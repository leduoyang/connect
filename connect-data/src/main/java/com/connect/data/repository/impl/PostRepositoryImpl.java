package com.connect.data.repository.impl;

import com.connect.data.dao.IPostDao;
import com.connect.data.dto.PostDto;
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

    public PostDto queryPostById(long id, long userId) {
        log.info(String.format("param: %s, userId %s", id, userId));

        return postDao.queryPostById(id, userId);
    }

    public Post internalQueryPostById(long id) {
        return postDao.internalQueryPostById(id);
    }

    public List<PostDto> queryPost(QueryPostParam param, long userId) {
        log.info(String.format("param: %s, userId %s", param, userId));

        return postDao.queryPost(
                param.getKeyword(),
                param.getTags(),
                userId
        );
    }

    public long createPost(Post post) {
        log.info(String.format("param: %s, userId %s", post, post.getCreatedUser()));

        if (post.getReferenceId() != null) {
            PostDto referencePost = postDao.queryPostById(
                    post.getReferenceId(),
                    post.getCreatedUser()
            );
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

        return post.getId();
    }

    public void updatePost(Post post) {
        log.info(String.format("param: %s, userId %s", post, post.getUpdatedUser()));

        long targetId = post.getId();
        long userId = post.getUpdatedUser();
        boolean existed = postDao.postExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.POST_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = postDao.updatePost(post);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_UPDATE_EXCEPTION);
        }
    }

    public void incrementViews(long id, int version) {
        int affected = postDao.incrementViews(id, version);

        if (affected <= 0) {
            log.error(ConnectErrorCode.OPTIMISTIC_LOCK_CONFLICT_EXCEPTION + "post incrementViews failed");
        }
    }

    public void refreshStars(long id, int version, int stars) {
        int affected = postDao.refreshStars(id, version, stars);

        if (affected <= 0) {
            log.error(ConnectErrorCode.OPTIMISTIC_LOCK_CONFLICT_EXCEPTION + "post refreshStars failed");
        }
    }

    public void deletePost(Post post) {
        log.info(String.format("param: %s, userId %s", post, post.getUpdatedUser()));

        long targetId = post.getId();
        long userId = post.getUpdatedUser();
        boolean existed = postDao.postExisting(targetId, userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.POST_NOT_EXISTED_EXCEPTION,
                    String.format("Post %s not exited or user %s is not the creator", targetId, userId)
            );
        }

        int affected = postDao.deletePost(targetId, userId);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.POST_DELETE_EXCEPTION);
        }
    }
}
