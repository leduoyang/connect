package com.connect.core.service.star.impl;

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

    private void updateLikesCountForTarget(long targetId, int targetType) {
        int counts = starRepository.countStars(targetId, targetType);

        StarTargetType target = StarTargetType.getType(targetType);
        switch (target) {
            case PROJECT:
                Project project = projectRepository.queryProjectById(targetId);
                project.setLikesCount(counts);
                projectRepository.refreshLikeCount(
                        project.getId(),
                        project.getVersion(),
                        project.getLikesCount()
                );
                break;
            case POST:
                Post post = postRepository.queryPostById(targetId);
                post.setLikesCount(counts);
                postRepository.refreshLikeCount(
                        post.getId(),
                        post.getVersion(),
                        post.getLikesCount()
                );
                break;
            case COMMENT:
                Comment comment = commentRepository.queryCommentById(targetId);
                comment.setLikesCount(counts);
                commentRepository.refreshLikeCount(
                        comment.getId(),
                        comment.getVersion(),
                        comment.getLikesCount()
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
