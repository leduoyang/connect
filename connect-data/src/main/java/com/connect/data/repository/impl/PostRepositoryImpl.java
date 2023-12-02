package com.connect.data.repository.impl;

import com.connect.data.dao.IPostDao;
import com.connect.data.entity.Post;
import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.param.QueryPostParam;
import com.connect.data.repository.IPostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class PostRepositoryImpl implements IPostRepository {

    @Autowired
    IPostDao postDao;

    public PostRepositoryImpl(IPostDao postDao) {
        this.postDao = postDao;
    }

    public Post queryPostById(long id) {
        return postDao.queryPostById(id);
    }

    public List<Post> queryPost(QueryPostParam param) {
        return postDao.queryPost(
                param.getPostId(),
                param.getUserId(),
                param.getKeyword()
        );
    }

    public void createPost(Post post) {
        if (post.getReferenceId() != null) {
            Post referencePost = postDao.queryPostById(post.getReferenceId());
            if (referencePost == null) {
                throw new ConnectDataException(
                        ConnectErrorCode.POST_NOT_EXISTED_EXCEPTION,
                        String.format("Reference post %s not exited.", post.getReferenceId())
                );
            }
            if (referencePost.getReferenceId() != null) {
                post.setReferenceId(referencePost.getReferenceId());
            }
        }

        int affected = postDao.createPost(post);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_CREATE_EXCEPTION);
        }
    }

    public void updatePost(Post post) {
        boolean existed = postDao.postExisting(post.getId());
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.POST_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited.", post.getId())
            );
        }
        int affected = postDao.updatePost(post);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_UPDATE_EXCEPTION);
        }
    }

    public void deletePost(Long id) {
        boolean existed = postDao.postExisting(id);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.POST_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited.", id)
            );
        }
        int affected = postDao.deletePost(id);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_UPDATE_EXCEPTION);
        }
    }
}
