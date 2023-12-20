package com.connect.core.service.post.iml;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.dto.*;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.post.IPostService;
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
    public long createPost(CreatePostDto request, RequestMetaInfo requestMetaInfo) {
        Post post = new Post()
                .setStatus(request.getStatus())
                .setCreatedUser(requestMetaInfo.getUserId())
                .setUpdatedUser(requestMetaInfo.getUserId());

        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getReferenceId() != null){
            if(checkReferencePost(request.getReferenceId(), requestMetaInfo.getUserId()) == null) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (targetReferenceId not existed or unauthorized to retrieve)"
                );
            }
            post.setReferenceId(request.getReferenceId());
        }

        return postRepository.createPost(post);
    }

    @Override
    public void updatePost(UpdatePostDto request, RequestMetaInfo requestMetaInfo) {
        Post post = new Post()
                .setId(request.getId())
                .setStatus(request.getStatus())
                .setUpdatedUser(requestMetaInfo.getUserId());

        if (request.getReferenceId() != null
                && checkReferencePost(request.getReferenceId(), requestMetaInfo.getUserId()) != null
        ) {
            post.setReferenceId(request.getReferenceId());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }

        postRepository.updatePost(post);
    }

    @Override
    public void deletePost(DeletePostDto request, RequestMetaInfo requestMetaInfo) {
        Post post = new Post()
                .setId(request.getId())
                .setUpdatedUser(requestMetaInfo.getUserId());

        postRepository.deletePost(post);
    }

    /**
     * Retrieve target post by referenceId and check authorization by userId
     *
     * @param referenceId id of the reference post
     * @param userId      id of the user making the request
     * @return target post
     */
    private QueryPostResponseDto checkReferencePost(Long referenceId, String userId) {
        if (referenceId == null) {
            log.info("referenceId not exist");
            return null;
        }

        Post referencePost = postRepository.queryPostById(referenceId, userId);

        if (referencePost == null) {
            log.warn("reference post not found or not authorized to retrieve");
            return null;
        }

        return new QueryPostResponseDto()
                .setId(referencePost.getId())
                .setContent(referencePost.getContent())
                .setUpdatedUser(referencePost.getUpdatedUser())
                .setDbModifyTime(referencePost.getDbModifyTime());
    }
}
