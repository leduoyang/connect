package com.connect.core.service.star.impl;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.star.dto.UnStarDto;
import com.connect.api.star.request.StarRequest;
import com.connect.api.star.request.UnStarRequest;
import com.connect.common.enums.StarTargetType;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.star.IStarService;
import com.connect.core.service.user.IUserService;
import com.connect.core.service.user.dto.UserDto;
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

    private IUserService userService;

    public StarServiceImpl(
            IStarRepository starRepository,
            IProjectRepository projectRepository,
            IPostRepository postRepository,
            ICommentRepository commentRepository,
            IUserService userService
    ) {
        this.starRepository = starRepository;
        this.projectRepository = projectRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    @Override
    public void star(StarRequest request, RequestMetaInfo requestMetaInfo) {
        if (StarTargetType.getType(request.getTargetType()) == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (type should be between 0 and 3)"
            );
        }

        Star star = new Star()
                .setUserId(requestMetaInfo.getUserId())
                .setTargetId(request.getTargetId())
                .setTargetType(request.getTargetType())
                .setIsActive(Boolean.TRUE);

        if (starRepository.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            starRepository.updateStar(star);
        } else {
            starRepository.createStar(star);
        }
        updateStarsForTarget(star.getTargetId(), star.getTargetType());
    }

    @Override
    public void unStar(UnStarRequest request, RequestMetaInfo requestMetaInfo) {
        if (StarTargetType.getType(request.getTargetType()) == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (type should be between 0 and 3)"
            );
        }

        Star star = new Star()
                .setUserId(requestMetaInfo.getUserId())
                .setTargetId(request.getTargetId())
                .setTargetType(request.getTargetType())
                .setIsActive(Boolean.FALSE);

        if (starRepository.starExisting(star.getUserId(), star.getTargetId(), star.getTargetType())) {
            starRepository.updateStar(star);
        } else {
            starRepository.createStar(star);
        }
        updateStarsForTarget(star.getTargetId(), star.getTargetType());
    }

    @Override
    public boolean starExisting(long targetId, int targetType, RequestMetaInfo requestMetaInfo) {
        UserDto userDto = userService.internalQueryUserByUserId(requestMetaInfo.getUserId());

        return starRepository.starExisting(
                userDto.getUserId(),
                targetId,
                targetType,
                true
        );
    }

    private void updateStarsForTarget(long targetId, int targetType) {
        int stars = starRepository.countStars(targetId, targetType);

        StarTargetType target = StarTargetType.getType(targetType);
        switch (target) {
            case PROJECT:
                Project project = projectRepository.internalQueryProjectById(targetId);
                project.setStars(stars);
                projectRepository.refreshStars(
                        project.getId(),
                        project.getVersion(),
                        project.getStars()
                );
                break;
            case POST:
                Post post = postRepository.internalQueryPostById(targetId);
                post.setStars(stars);
                postRepository.refreshStars(
                        post.getId(),
                        post.getVersion(),
                        post.getStars()
                );
                break;
            case COMMENT:
                Comment comment = commentRepository.internalQueryCommentById(targetId);
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
