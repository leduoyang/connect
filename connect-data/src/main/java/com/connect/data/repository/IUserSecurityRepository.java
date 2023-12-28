package com.connect.data.repository;

import com.connect.data.entity.UserSecurity;

public interface IUserSecurityRepository {
    UserSecurity findByUsername(long userId);
}
