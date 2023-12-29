package com.connect.core.service.post.iml;

import com.connect.api.common.RequestMetaInfo;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
import com.connect.api.post.vo.QueryPostVo;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.core.service.post.IPostService;
import com.connect.data.dto.PostDto;
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
    public QueryPostVo queryPostById(long id, RequestMetaInfo requestMetaInfo) {
        PostDto post = postRepository.queryPostById(id, requestMetaInfo.getUserId());
        if (post == null) {
            log.error("query post not found or not authorized to retrieve");
            return null;
        }

        postRepository.incrementViews(
                post.getId(),
                post.getVersion()
        );

        QueryPostVo postDto = new QueryPostVo()
                .setId(post.getId())
                .setUsername(post.getUsername())
                .setStatus(post.getStatus())
                .setStars(post.getStars())
                .setViews(post.getViews())
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
    public List<QueryPostVo> queryPost(QueryPostRequest request, RequestMetaInfo requestMetaInfo) {
        QueryPostParam param = new QueryPostParam()
                .setKeyword(request.getUsername())
                .setKeyword(request.getKeyword())
                .setTags(request.getTags());

        List<PostDto> postList = postRepository.queryPost(param, requestMetaInfo.getUserId());

        return postList
                .stream()
                .map(x -> new QueryPostVo()
                        .setId(x.getId())
                        .setUsername(x.getUsername())
                        .setStatus(x.getStatus())
                        .setContent(x.getContent())
                        .setStars(x.getStars())
                        .setViews(x.getViews())
                        .setReferencePost(checkReferencePost(x.getReferenceId(), requestMetaInfo.getUserId()))
                        .setDbModifyTime(x.getDbModifyTime()))
                .collect(Collectors.toList());
    }

    @Override
    public long createPost(CreatePostRequest request, RequestMetaInfo requestMetaInfo) {
        if (request.getStatus() < 0 || request.getStatus() > 2) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (status should be between 0 and 2)"
            );
        }

        Post post = new Post()
                .setStatus(request.getStatus())
                .setCreatedUser(requestMetaInfo.getUserId())
                .setUpdatedUser(requestMetaInfo.getUserId());

        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        if (request.getReferenceId() != null) {
            if (checkReferencePost(request.getReferenceId(), requestMetaInfo.getUserId()) == null) {
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
    public void updatePost(long id, UpdatePostRequest request, RequestMetaInfo requestMetaInfo) {
        Post post = new Post()
                .setId(id)
                .setUpdatedUser(requestMetaInfo.getUserId());

        if (request.getStatus() != null) {
            if (request.getStatus() < 0 || request.getStatus() > 2) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (status should be between 0 and 2)"
                );
            }
            post.setStatus(request.getStatus());
        }
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
    public void deletePost(long id, RequestMetaInfo requestMetaInfo) {
        Post post = new Post()
                .setId(id)
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
    private QueryPostVo checkReferencePost(Long referenceId, Long userId) {
        if (referenceId == null) {
            log.info("referenceId not exist");
            return null;
        }

        PostDto referencePost = postRepository.queryPostById(referenceId, userId);

        if (referencePost == null) {
            log.warn("reference post not found or not authorized to retrieve");
            return null;
        }

        return new QueryPostVo()
                .setId(referencePost.getId())
                .setContent(referencePost.getContent())
                .setUsername(referencePost.getUsername())
                .setDbModifyTime(referencePost.getDbModifyTime());
    }
}
