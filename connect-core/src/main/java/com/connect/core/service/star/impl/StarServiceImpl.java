package com.connect.core.service.star.impl;

import com.connect.api.comment.dto.QueryCommentDto;
import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.project.dto.QueryProjectDto;
import com.connect.api.star.dto.StarDto;
import com.connect.api.star.dto.UnStarDto;
import com.connect.common.enums.StarTargetType;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.star.IStarService;
import com.connect.data.entity.*;
import com.connect.data.repository.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@Service
public class StarServiceImpl implements IStarService {
    private IStarRepository starRepository;

    private IProjectRepository projectRepository;

    private IPostRepository postRepository;

    private ICommentRepository commentRepository;

    public StarServiceImpl(
            IStarRepository starRepository,
            IProjectRepository projectRepository,
            IPostRepository postRepository,
            ICommentRepository commentRepository
    ) {
        this.starRepository = starRepository;
        this.projectRepository = projectRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void star(StarDto request) {
        if (StarTargetType.getType(request.getTargetType()) == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (type should be between 0 and 3)"
            );
        }

        Star star = new Star()
                .setUserId(request.getUserId())
                .setTargetId(request.getTargetId())
                .setTargetType(request.getTargetType())
                .setIsActive(request.isActive());

        if (starRepository.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            starRepository.updateStar(star);
        } else {
            starRepository.createStar(star);
        }
        updateLikesCountForTarget(star.getTargetId(), star.getTargetType());
    }

    @Override
    public void unStar(UnStarDto request) {
        if (StarTargetType.getType(request.getTargetType()) == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (type should be between 0 and 3)"
            );
        }

        Star star = new Star()
                .setUserId(request.getUserId())
                .setTargetId(request.getTargetId())
                .setTargetType(request.getTargetType())
                .setIsActive(request.isActive());

        if (starRepository.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            starRepository.updateStar(star);
        } else {
            starRepository.createStar(star);
        }
        updateLikesCountForTarget(star.getTargetId(), star.getTargetType());
    }

    @Override
    public boolean starExisting(String userId, long targetId, int targetType, Boolean isActive) {
        return starRepository.starExisting(
                userId,
                targetId,
                targetType,
                isActive
        );
    }

    @Override
    public <T> List<T> queryTargetIdList(StarTargetType targetType, String userId, Class<T> returnClass) {
        List<Integer> idList = starRepository.queryTargetIdList(targetType.getCode(), userId);
        switch (Objects.requireNonNull(targetType)) {
            case PROJECT:
                List<QueryProjectDto> projectDtoList = idList.stream().map(x -> {
                    Project project = projectRepository.queryProjectById(x);
                    return new QueryProjectDto()
                            .setId(project.getId())
                            .setTitle(project.getTitle())
                            .setDescription(project.getDescription())
                            .setStatus(project.getStatus())
                            .setTags(project.getTags())
                            .setBoosted(project.getBoosted())
                            .setStars(project.getStars())
                            .setViews(project.getViews())
                            .setCreatedUser(project.getCreatedUser())
                            .setUpdatedUser(project.getUpdatedUser())
                            .setDbCreateTime(project.getDbCreateTime())
                            .setDbModifyTime(project.getDbModifyTime());
                }).collect(Collectors.toList());
                return (List<T>) projectDtoList;
            case POST:
                List<QueryPostDto> postDtoList = idList.stream().map(x -> {
                    Post post = postRepository.queryPostById(x);
                    return new QueryPostDto()
                            .setId(post.getId())
                            .setStatus(post.getStatus())
                            .setStars(post.getStars())
                            .setViews(post.getViews())
                            .setCreatedUser(post.getCreatedUser())
                            .setUpdatedUser(post.getUpdatedUser())
                            .setDbCreateTime(post.getDbCreateTime())
                            .setDbModifyTime(post.getDbModifyTime());
                }).collect(Collectors.toList());
                return (List<T>) postDtoList;
            case COMMENT:
                List<QueryCommentDto> commentDtoList = idList.stream().map(x -> {
                    Comment comment = commentRepository.queryCommentById(x);
                    return new QueryCommentDto()
                            .setId(comment.getId())
                            .setPostId(comment.getPostId())
                            .setStatus(comment.getStatus())
                            .setContent(comment.getContent())
                            .setStars(comment.getStars())
                            .setViews(comment.getViews())
                            .setCreatedUser(comment.getCreatedUser())
                            .setUpdatedUser(comment.getUpdatedUser())
                            .setDbCreateTime(comment.getDbCreateTime())
                            .setDbModifyTime(comment.getDbModifyTime());
                }).collect(Collectors.toList());
                return (List<T>) commentDtoList;
            default:
                throw new ConnectDataException(
                        ConnectErrorCode.INTERNAL_SERVER_ERROR,
                        "Invalid payload updating like counts for target entity"
                );
        }
    }

    private void updateLikesCountForTarget(long targetId, int targetType) {
        int stars = starRepository.countStars(targetId, targetType);

        StarTargetType target = StarTargetType.getType(targetType);
        switch (target) {
            case PROJECT:
                Project project = projectRepository.queryProjectById(targetId);
                project.setStars(stars);
                projectRepository.refreshStars(
                        project.getId(),
                        project.getVersion(),
                        project.getStars()
                );
                break;
            case POST:
                Post post = postRepository.queryPostById(targetId);
                post.setStars(stars);
                postRepository.refreshStars(
                        post.getId(),
                        post.getVersion(),
                        post.getStars()
                );
                break;
            case COMMENT:
                Comment comment = commentRepository.queryCommentById(targetId);
                comment.setStars(stars);
                commentRepository.refreshStars(
                        comment.getId(),
                        comment.getVersion(),
                        comment.getStars()
                );
                break;
            default:
                throw new ConnectDataException(
                        ConnectErrorCode.INTERNAL_SERVER_ERROR,
                        "Invalid payload updating like counts for target entity"
                );
        }
    }
}
