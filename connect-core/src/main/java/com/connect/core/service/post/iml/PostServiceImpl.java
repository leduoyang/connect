package com.connect.core.service.post.iml;

import com.connect.api.post.dto.CreatePostDto;
import com.connect.api.post.dto.DeletePostDto;
import com.connect.api.post.dto.QueryPostDto;
import com.connect.api.post.dto.UpdatePostDto;
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
    public QueryPostDto queryPostById(long id) {
        Post post = postRepository.queryPostById(id);
        postRepository.incrementViewCount(
                post.getId(),
                post.getVersion()
        );

        QueryPostDto postDto = new QueryPostDto()
                .setId(post.getId())
                .setStatus(post.getStatus())
                .setLikesCount(post.getLikesCount())
                .setViewsCount(post.getViewsCount())
                .setCreatedUser(post.getCreatedUser())
                .setUpdatedUser(post.getUpdatedUser())
                .setDbCreateTime(post.getDbCreateTime())
                .setDbModifyTime(post.getDbModifyTime());
        if (post.getContent() != null) {
            postDto.setContent(post.getContent());
        }
        if (post.getReferenceId() != null) {
            postDto.setReferencePost(checkReferencePost(post));
        }
        return postDto;
    }

    @Override
    public List<QueryPostDto> queryPost(QueryPostRequest request) {
        QueryPostParam param = new QueryPostParam()
                .setPostId(request.getPostId())
                .setKeyword(request.getKeyword())
                .setUserId(request.getUserId())
                .setTags(request.getTags());

        List<Post> postList = postRepository.queryPost(param);

        return postList
                .stream()
                .map(x -> new QueryPostDto()
                        .setId(x.getId())
                        .setStatus(x.getStatus())
                        .setContent(x.getContent())
                        .setLikesCount(x.getLikesCount())
                        .setViewsCount(x.getViewsCount())
                        .setCreatedUser(x.getCreatedUser())
                        .setUpdatedUser(x.getUpdatedUser())
                        .setDbCreateTime(x.getDbCreateTime())
                        .setDbModifyTime(x.getDbModifyTime()))
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(CreatePostDto request) {
        if (request.getStatus() < 0 || request.getStatus() > 2) {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (status should be between 0 and 2)"
            );
        }

        Post post = new Post()
                .setStatus(request.getStatus())
                .setCreatedUser(request.getCreatedUser())
                .setUpdatedUser(request.getCreatedUser());
        if (request.getContent() != null) {
            if (request.getContent().equals("")) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (post content can not be blank)"
                );
            }
            post.setContent(request.getContent());
        } else if (request.getReferenceId() != null) {
            post.setReferenceId(request.getReferenceId());
        } else {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (post content and referenceId can not both be absent)"
            );
        }

        postRepository.createPost(post);
    }

    @Override
    public void updatePost(UpdatePostDto request) {
        Post post = new Post()
                .setId(request.getId())
                .setUpdatedUser(request.getUpdatedUser());
        if (request.getStatus() != null) {
            if (request.getStatus() < 0 || request.getStatus() > 2) {
                throw new ConnectDataException(
                        ConnectErrorCode.PARAM_EXCEPTION,
                        "Invalid payload (status should be between 0 and 2)"
                );
            }
            post.setStatus(request.getStatus());
        }
        if (request.getReferenceId() != null) {
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

    private QueryPostDto checkReferencePost(Post post) {
        if (post.getReferenceId() == null) {
            return null;
        }

        Post referencePost = postRepository.queryPostById(post.getReferenceId());
        return new QueryPostDto()
                .setId(referencePost.getId())
                .setContent(referencePost.getContent())
                .setUpdatedUser(referencePost.getUpdatedUser())
                .setDbModifyTime(referencePost.getDbModifyTime());
    }
}
