package com.connect.core.service.project.iml;

import com.connect.api.project.dto.CreateProjectDto;
import com.connect.api.project.dto.DeleteProjectDto;
import com.connect.api.project.dto.QueryProjectDto;
import com.connect.api.project.dto.UpdateProjectDto;
import com.connect.api.project.request.QueryProjectRequest;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.project.IProjectService;
import com.connect.data.entity.Post;
import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;
import com.connect.data.repository.IProjectRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ProjectServiceImpl implements IProjectService {
    private IProjectRepository projectRepository;

    public ProjectServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public QueryProjectDto queryProjectById(long id) {
        Project project = projectRepository.queryProjectById(id);
        this.incrementViewCount(project);

        QueryProjectDto projectDto = new QueryProjectDto()
                .setId(project.getId())
                .setTitle(project.getTitle())
                .setDescription(project.getDescription())
                .setStatus(project.getStatus())
                .setUpdatedUser(project.getUpdatedUser())
                .setDbModifyTime(project.getDbModifyTime());
        return projectDto;
    }

    @Override
    public List<QueryProjectDto> queryProject(QueryProjectRequest request) {
        QueryProjectParam param = new QueryProjectParam()
                .setProjectId(request.getProjectId())
                .setKeyword(request.getKeyword())
                .setUserId(request.getUserId())
                .setTags(request.getTags());

        List<Project> projectList = projectRepository.queryProject(param);

        return projectList
                .stream()
                .map(x -> new QueryProjectDto()
                        .setId(x.getId())
                        .setTitle(x.getTitle())
                        .setDescription(x.getDescription())
                        .setStatus(x.getStatus())
                        .setBoosted(x.getBoosted())
                        .setUpdatedUser(x.getUpdatedUser())
                        .setDbModifyTime(x.getDbModifyTime())
                )
                .collect(Collectors.toList());
    }

    @Override
    public void createProject(CreateProjectDto request) {
        if (request.getStatus() < 0 || request.getStatus() > 2) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (status should be between 0 and 2)"
            );
        }
        if (request.getBoosted() < 0 || request.getBoosted() > 1) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (boosted should be between 0 and 1)"
            );
        }

        Project project = new Project()
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setTags(request.getTags())
                .setStatus(request.getStatus())
                .setBoosted(request.getBoosted())
                .setCreatedUser(request.getCreatedUser())
                .setUpdatedUser(request.getCreatedUser());

        projectRepository.createProject(project);
    }

    @Override
    public void updateProject(UpdateProjectDto request) {
        Project project = new Project()
                .setId(request.getId())
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setTags(request.getTags())
                .setUpdatedUser(request.getUpdatedUser());
        if (request.getStatus() != null) {
            if (request.getStatus() < 0 || request.getStatus() > 2) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (status should be between 0 and 2)"
                );
            }
            project.setStatus(request.getStatus());
        }
        if (request.getBoosted() != null) {
            if (request.getBoosted() < 0 || request.getBoosted() > 1) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (boosted should be between 0 and 1)"
                );
            }
            project.setBoosted(request.getBoosted());
        }

        projectRepository.updateProject(project);
    }

    @Override
    public void deleteProject(DeleteProjectDto request) {
        Project project = new Project()
                .setId(request.getId())
                .setUpdatedUser(request.getUpdatedUser());

        projectRepository.deleteProject(project);
    }

    @Transactional
    private void incrementViewCount(Project project) {
        project.setViewsCount(project.getViewsCount() + 1);
        projectRepository.updateProject(project);
    }
}
