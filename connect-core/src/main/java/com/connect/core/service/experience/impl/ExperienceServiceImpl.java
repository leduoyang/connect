package com.connect.core.service.experience.impl;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.experience.request.CreateExperienceRequest;
import com.connect.api.experience.request.UpdateExperienceRequest;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.experience.IExperienceService;
import com.connect.data.entity.Experience;
import com.connect.data.repository.IExperienceRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Log4j2
@Service
public class ExperienceServiceImpl implements IExperienceService {
    private IExperienceRepository experienceRepository;

    public ExperienceServiceImpl(IExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }

    @Override
    public long createExperience(CreateExperienceRequest request, RequestMetaInfo requestMetaInfo) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Experience experience = new Experience()
                .setUserId(requestMetaInfo.getUserId())
                .setTitle(request.getTitle())
                .setCompany(request.getCompany());

        try {
            experience.setStart(LocalDate.parse(request.getStart(), dateFormatter));
            if (request.getUntil() == null) {
                experience.setUntil(LocalDate.now());
            } else {
                experience.setUntil(LocalDate.parse(request.getUntil(), dateFormatter));
            }
            if (!experience.getUntil().isAfter(experience.getStart())) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid date range"
                );
            }
        } catch (DateTimeParseException e) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid date format"
            );
        }

        return experienceRepository.createExperience(experience);
    }

    @Override
    public void updateExperience(long id, UpdateExperienceRequest request, RequestMetaInfo requestMetaInfo) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Experience experience = new Experience()
                .setId(id)
                .setUserId(requestMetaInfo.getUserId())
                .setTitle(request.getTitle())
                .setCompany(request.getCompany());

        if (request.getStart() != null) {
            try {
                experience.setStart(LocalDate.parse(request.getStart(), dateFormatter));
            } catch (DateTimeParseException e) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid format for start date"
                );
            }
        }

        if (request.getUntil() != null) {
            try {
                experience.setUntil(LocalDate.parse(request.getUntil(), dateFormatter));
            } catch (DateTimeParseException e) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid format for until date"
                );
            }
        }

        if (!experience.getUntil().isAfter(experience.getStart())) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid date range"
            );
        }

        experienceRepository.updateExperience(experience);
    }

    @Override
    public void deleteExperience(long id, RequestMetaInfo requestMetaInfo) {
        Experience experience = new Experience()
                .setId(id)
                .setUserId(requestMetaInfo.getUserId());

        experienceRepository.deleteExperience(experience);
    }
}
