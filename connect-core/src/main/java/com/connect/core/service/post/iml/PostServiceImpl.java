package com.connect.core.service.post.iml;

import com.connect.api.post.dto.PostDto;
import com.connect.api.post.request.CreatePostRequest;
import com.connect.api.post.request.QueryPostRequest;
import com.connect.api.post.request.UpdatePostRequest;
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
    public PostDto queryPostById(long id) {
        Post post = postRepository.queryPostById(id);

        PostDto postDto = new PostDto()
                .setId(post.getId())
                .setUpdatedUser(post.getUpdatedUser())
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
    public List<PostDto> queryPost(QueryPostRequest request) {
        QueryPostParam param = new QueryPostParam()
                .setPostId(request.getPostId())
                .setKeyword(request.getKeyword())
                .setUserId(request.getUserId());

        List<Post> postList = postRepository.queryPost(param);

        return postList
                .stream()
                .map(x -> new PostDto()
                        .setId(x.getId())
                        .setContent(x.getContent())
                        .setUpdatedUser(x.getUpdatedUser())
                        .setDbModifyTime(x.getDbModifyTime())
                        .setReferencePost(checkReferencePost(x)))
                .collect(Collectors.toList());
    }

    @Override
    public void createPost(CreatePostRequest request) {
        Post post = new Post()
                .setCreatedUser(request.getUserId())
                .setUpdatedUser(request.getUserId());
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        } else if (request.getReferenceId() != null) {
            post.setReferenceId(request.getReferenceId());
        } else {
            throw new ConnectDataException(
                    ConnectErrorCode.PARAM_EXCEPTION,
                    "Invalid payload (content and referenceId can not both be absent)"
            );
        }
        postRepository.createPost(post);
    }

    @Override
    public void updatePost(Long postId, UpdatePostRequest request) {
        Post post = new Post()
                .setId(postId)
                .setContent(request.getContent())
                .setUpdatedUser(request.getUpdatedUser());
        postRepository.updatePost(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deletePost(id);
    }

    private PostDto checkReferencePost(Post post) {
        if (post.getReferenceId() == null) {
            return null;
        }

        Post referencePost = postRepository.queryPostById(post.getReferenceId());
        return new PostDto()
                .setId(referencePost.getId())
                .setContent(referencePost.getContent())
                .setUpdatedUser(referencePost.getUpdatedUser())
                .setDbModifyTime(referencePost.getDbModifyTime());
    }
}
