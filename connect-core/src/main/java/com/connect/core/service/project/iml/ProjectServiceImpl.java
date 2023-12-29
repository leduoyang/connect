package com.connect.core.service.project.iml;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.project.request.CreateProjectRequest;
import com.connect.api.project.request.QueryProjectRequest;
import com.connect.api.project.request.UpdateProjectRequest;
import com.connect.api.project.vo.QueryProjectVo;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.project.IProjectService;
import com.connect.data.dto.ProjectDto;
import com.connect.data.entity.Project;
import com.connect.data.param.QueryProjectParam;
import com.connect.data.repository.IProjectRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
    public QueryProjectVo queryProjectById(long id, RequestMetaInfo requestMetaInfo) {
        ProjectDto project = projectRepository.queryProjectById(id, requestMetaInfo.getUserId());
        if (project == null) {
            log.error("query project not found or not authorized to retrieve");
            return null;
        }

        projectRepository.incrementViews(
                project.getId(),
                project.getVersion()
        );

        QueryProjectVo projectDto = new QueryProjectVo()
                .setId(project.getId())
                .setUsername(project.getUsername())
                .setTitle(project.getTitle())
                .setDescription(project.getDescription())
                .setStatus(project.getStatus())
                .setTags(project.getTags())
                .setBoosted(project.getBoosted())
                .setStars(project.getStars())
                .setViews(project.getViews())
                .setDbModifyTime(project.getDbModifyTime());
        return projectDto;
    }

    @Override
    public List<QueryProjectVo> queryProject(QueryProjectRequest request, RequestMetaInfo requestMetaInfo) {
        QueryProjectParam param = new QueryProjectParam()
                .setUsername(request.getUsername())
                .setKeyword(request.getKeyword())
                .setTags(request.getTags());

        List<ProjectDto> projectList = projectRepository.queryProject(param, requestMetaInfo.getUserId());

        return projectList
                .stream()
                .map(x -> new QueryProjectVo()
                        .setId(x.getId())
                        .setUsername(x.getUsername())
                        .setTitle(x.getTitle())
                        .setDescription(x.getDescription())
                        .setStatus(x.getStatus())
                        .setTags(x.getTags())
                        .setBoosted(x.getBoosted())
                        .setStars(x.getStars())
                        .setViews(x.getViews())
                        .setDbModifyTime(x.getDbModifyTime())
                )
                .collect(Collectors.toList());
    }

    @Override
    public long createProject(CreateProjectRequest request, RequestMetaInfo requestMetaInfo) {
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
                .setCreatedUser(requestMetaInfo.getUserId())
                .setUpdatedUser(requestMetaInfo.getUserId());

        return projectRepository.createProject(project);
    }

    @Override
    public void updateProject(long id, UpdateProjectRequest request, RequestMetaInfo requestMetaInfo) {
        Project project = new Project()
                .setId(id)
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setTags(request.getTags())
                .setUpdatedUser(requestMetaInfo.getUserId());
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
    public void deleteProject(long id, RequestMetaInfo requestMetaInfo) {
        Project project = new Project()
                .setId(id)
                .setUpdatedUser(requestMetaInfo.getUserId());

        projectRepository.deleteProject(project);
    }
}
