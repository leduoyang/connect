package com.connect.core.service.user.impl;

import com.connect.data.repository.IUserSecurityRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserSecurityServiceImpl implements UserDetailsService {
    private IUserSecurityRepository userSecurityRepository;

    public UserSecurityServiceImpl(IUserSecurityRepository userSecurityRepository) {
        this.userSecurityRepository = userSecurityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        return userSecurityRepository.findByUsername(Long.parseLong(userId));
    }
}
