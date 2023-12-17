package com.connect.core.service.post.iml;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.dto.*;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.core.service.post.IPostService;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.entity.Post;
import com.connect.data.param.QueryPostParam;
import com.connect.data.repository.IPostRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class PostServiceImpl implements IPostService {
    private IPostRepository postRepository;

    public PostServiceImpl(IPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public QueryPostResponseDto queryPostById(long id, RequestMetaInfo requestMetaInfo) {
        Post post = postRepository.queryPostById(id, requestMetaInfo.getUserId());
        if (post == null) {
            log.error("query post not found or not authorized to retrieve");
            return null;
        }

        postRepository.incrementViews(
                post.getId(),
                post.getVersion()
        );

        QueryPostResponseDto postDto = new QueryPostResponseDto()
                .setId(post.getId())
                .setStatus(post.getStatus())
                .setStars(post.getStars())
                .setViews(post.getViews())
                .setCreatedUser(post.getCreatedUser())
                .setUpdatedUser(post.getUpdatedUser())
                .setDbCreateTime(post.getDbCreateTime())
                .setDbModifyTime(post.getDbModifyTime());
        if (post.getContent() != null) {
            postDto.setContent(post.getContent());
        }
        if (post.getReferenceId() != null) {
            postDto.setReferencePost(checkReferencePost(post.getReferenceId(), requestMetaInfo.getUserId()));
        }
        return postDto;
    }

    @Override
    public List<QueryPostResponseDto> queryPost(QueryPostDto request, RequestMetaInfo requestMetaInfo) {
        QueryPostParam param = new QueryPostParam()
                .setPostId(request.getPostId())
                .setKeyword(request.getKeyword())
                .setUserId(request.getUserId())
                .setTags(request.getTags());

        List<Post> postList = postRepository.queryPost(param, requestMetaInfo.getUserId());

        return postList
                .stream()
                .map(x -> new QueryPostResponseDto()
                        .setId(x.getId())
                        .setStatus(x.getStatus())
                        .setContent(x.getContent())
                        .setStars(x.getStars())
                        .setViews(x.getViews())
                        .setReferencePost(checkReferencePost(x.getReferenceId(), requestMetaInfo.getUserId()))
                        .setCreatedUser(x.getCreatedUser())
                        .setUpdatedUser(x.getUpdatedUser())
                        .setDbCreateTime(x.getDbCreateTime())
                        .setDbModifyTime(x.getDbModifyTime()))
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(CreatePostDto request) {
        Post post = new Post()
                .setStatus(request.getStatus())
                .setCreatedUser(request.getCreatedUser())
                .setUpdatedUser(request.getCreatedUser());

        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getReferenceId() != null
                && checkReferencePost(request.getReferenceId(), request.getCreatedUser()) != null) {
            post.setReferenceId(request.getReferenceId());
        }

        postRepository.createPost(post);
    }

    @Override
    public void updatePost(UpdatePostDto request) {
        Post post = new Post()
                .setId(request.getId())
                .setStatus(request.getStatus())
                .setUpdatedUser(request.getUpdatedUser());

        if (request.getReferenceId() != null
                && checkReferencePost(request.getReferenceId(), request.getUpdatedUser()) != null
        ) {
            post.setReferenceId(request.getReferenceId());
        }
        if (request.getContent() != null) {
            if (request.getContent().equals("")) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (post content can not be blank)"
                );
            }
            post.setContent(request.getContent());
        }

        postRepository.updatePost(post);
    }

    @Override
    public void deletePost(DeletePostDto request) {
        Post post = new Post()
                .setId(request.getId())
                .setUpdatedUser(request.getUpdatedUser());

        postRepository.deletePost(post);
    }

    /**
     * Retrieve target post by referenceId and check authorization by userId
     *
     * @param referenceId id of the reference post
     * @param userId      id of the user making the request
     * @return target post
     */
    private QueryPostResponseDto checkReferencePost(long referenceId, String userId) {
        Post referencePost = postRepository.queryPostById(referenceId, userId);

        if (referencePost == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.INTERNAL_SERVER_ERROR,
                    "reference post not found or not authorized to retrieve"
            );
        }

        return new QueryPostResponseDto()
                .setId(referencePost.getId())
                .setContent(referencePost.getContent())
                .setUpdatedUser(referencePost.getUpdatedUser())
                .setDbModifyTime(referencePost.getDbModifyTime());
    }
}
