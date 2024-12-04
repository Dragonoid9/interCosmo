package com.interntest.cosmotechintl.helper;


import com.interntest.cosmotechintl.entity.UserInfo;
import com.interntest.cosmotechintl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        UserInfo user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("could not found user..!!");
        }

        return new CustomUserDetails(user);
    }
}
