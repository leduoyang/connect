package com.connect.data.repository.impl;

import com.connect.data.dao.IUserSecurityDao;
import com.connect.data.entity.UserSecurity;
import com.connect.data.repository.IUserSecurityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserSecurityRepositoryImpl implements IUserSecurityRepository {
    @Autowired
    IUserSecurityDao userSecurityDao;

    public UserSecurityRepositoryImpl(IUserSecurityDao userSecurityDao) {
        this.userSecurityDao = userSecurityDao;
    }

    @Override
    public UserSecurity findByUsername(long userId) {
        return userSecurityDao.findByUsername(userId);
    }
}
