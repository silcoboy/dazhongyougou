package com.pine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pine.entity.AdminInfo;
import com.pine.entity.UserDetail;
import com.pine.mapper.AdminInfoMapper;

@Configuration
public class JwtAdminDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
   private AdminInfoMapper adminInfoMapper;
   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            AdminInfo admin = adminInfoMapper.findByUserName(username);
            if (admin == null) {
                throw new UsernameNotFoundException(String.format("No userDetail found with username '%s'.", username));
            }
            

        return new UserDetail(admin.getUserName(),admin.getRoleId(),passwordEncoder.encode(admin.getPwd()));

}


}
