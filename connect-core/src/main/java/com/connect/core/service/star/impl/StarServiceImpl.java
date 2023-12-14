package com.connect.core.service.star.impl;

import com.connect.api.star.dto.StarDto;
import com.connect.api.star.dto.UnStarDto;
import com.connect.common.enums.StarTargetType;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.star.IStarService;
import com.connect.data.entity.Star;
import com.connect.data.repository.IStarRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class StarServiceImpl implements IStarService {
    private IStarRepository starRepository;

    public StarServiceImpl(IStarRepository starRepository) {
        this.starRepository = starRepository;
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
}
