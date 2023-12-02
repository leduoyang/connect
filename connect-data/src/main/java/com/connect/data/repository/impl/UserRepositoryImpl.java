package com.connect.data.repository.impl;

import com.connect.common.exception.ConnectDataException;
import com.connect.common.exception.ConnectErrorCode;
import com.connect.data.dao.IUserDao;
import com.connect.data.entity.User;
import com.connect.data.param.QueryUserParam;
import com.connect.data.repository.IUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class UserRepositoryImpl implements IUserRepository {

    @Autowired
    IUserDao userDao;

    public UserRepositoryImpl(IUserDao userDao) {
        this.userDao = userDao;
    }

    public boolean authenticateRootUser(String userId, String password) {
        return userDao.authenticateRootUser(userId, password);
    }

    public boolean checkUserExisting(String userId) {
        return userDao.userExisting(userId);
    }

    public User queryUserByUserId(String userId) {
        User targetUser = userDao.queryUserByUserId(userId);
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_NOT_EXISTED_EXCEPTION,
                    String.format("User %s not exited.", userId)
            );
        }
        return targetUser;
    }

    public List<User> queryUser(QueryUserParam param) {
        log.info(param.toString());
        return userDao.queryUser(param.getKeyword());
    }

    public void createUser(User user) {
        boolean existed = userDao.userExisting(user.getUserId());
        if (existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_EXISTED_EXCEPTION,
                    String.format("UserId %s has exited.", user.getUserId())
            );
        }

        log.info("payload for creating user - " + user);
        int affected = userDao.createUser(user);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.USER_CREATE_EXCEPTION);
        }
    }

    public void updateUser(User user) {
        User targetUser = userDao.queryUserByUserId(user.getUserId());
        log.info("targetUser for updating - " + targetUser);
        if (targetUser == null) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_NOT_EXISTED_EXCEPTION,
                    String.format("User %s not exited.", user.getId())
            );
        }
        user.setId(targetUser.getId());
        log.info("payload for updating user - " + user);

        int affected = userDao.updateUser(user);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.USER_UPDATE_EXCEPTION);
        }
    }

    public void deleteUser(String userId) {
        boolean existed = userDao.userExisting(userId);
        if (!existed) {
            throw new ConnectDataException(
                    ConnectErrorCode.USER_NOT_EXISTED_EXCEPTION,
                    String.format("User %s not exited.", userId)
            );
        }
        int affected = userDao.deleteUser(userId);
        if (affected <= 0) {
            throw new ConnectDataException(ConnectErrorCode.USER_DELETE_EXCEPTION);
        }
    }
}
