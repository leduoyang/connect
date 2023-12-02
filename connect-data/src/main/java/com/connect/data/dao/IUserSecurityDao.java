package com.connect.data.dao;

import com.connect.data.entity.UserSecurity;

public interface IUserSecurityDao {
    UserSecurity findByUsername(String userId);
}
