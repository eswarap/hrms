package org.woven.hrms.employee.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.woven.hrms.employee.entity.User;
import org.woven.hrms.employee.model.UserInfo;
import org.woven.hrms.employee.repository.UserRepository;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        log.debug("Entering in loadUserByUsername Method...");
        User user = userRepository.findByUsername(username);
        if(user == null) {
            log.error("Username not found: " + username);
            throw new UsernameNotFoundException("could not found user..!!");
        }

        log.info("User Authenticated Successfully..!!!");

        return new UserInfo(user);
    }
}
