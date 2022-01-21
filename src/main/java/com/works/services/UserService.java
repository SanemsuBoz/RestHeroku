package com.works.services;

import com.works.entities.Role;
import com.works.entities.User;
import com.works.entities.UserInfo;
import com.works.repositories.UserInfoRepository;
import com.works.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {

    final UserRepository uRepo;
    final UserInfoRepository iRepo;
    public UserService(UserRepository uRepo, UserInfoRepository iRepo) {
        this.uRepo = uRepo;
        this.iRepo = iRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = null;
        Optional<User> oUser = uRepo.findByEmailEqualsIgnoreCase(username);
        if ( oUser.isPresent() ) {
            User us = oUser.get();
            userDetails = new org.springframework.security.core.userdetails.User(
                    us.getEmail(),
                    us.getPassword(),
                    us.isEnabled(),
                    us.isTokenExpired(),
                    true,
                    true,
                    getAuthorities( us.getRoles() ));
        }else {
            throw new UsernameNotFoundException("Kullanıcı adı yada şifre hatalı");
        }
        return userDetails;
    }

    private List<GrantedAuthority> getAuthorities (List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority( role.getName() ));
        }
        return authorities;
    }

    public User register( User us ) throws AuthenticationException {
        Optional<User> uOpt = uRepo.findByEmailEqualsIgnoreCase(us.getEmail());
        if ( uOpt.isPresent() ) {
            //throw new AuthenticationException("Bu kullanıcı daha önce kayıtlı!");
        }
        us.setPassword( encoder().encode( us.getPassword() ) );
        return uRepo.save(us);
    }


    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    // user actions info
    public void info(String url) {
        Authentication aut = SecurityContextHolder.getContext().getAuthentication();

        // roles
        StringBuilder roles = new StringBuilder();
        Collection<? extends GrantedAuthority> cls = aut.getAuthorities();
        roles.append("[");
        cls.forEach( item -> {
            item.getAuthority();
            roles.append(item.getAuthority());
            roles.append(",");
        } );
        roles.append("]");

        String name = aut.getName();
        String detail = String.valueOf(aut.getDetails());
        String[] detailArr = split(detail);
        Date date = new Date();

        UserInfo i = new UserInfo();
        i.setDate(date);
        i.setRoles(roles.toString());
        i.setUsername(name);
        i.setUrl(url);
        i.setIp(detailArr[0]);
        i.setSessionid(detailArr[1]);
        iRepo.save(i);

    }

    public String[] split( String data ) {
        data = data.replace("WebAuthenticationDetails [", "");
        data = data.replace("]", "");
        String[] arr = data.split(",");
        String ip = arr[0].replace("RemoteIpAddress=", "");
        String session = arr[1].replace("SessionId=", "");
        String[] dizi = { ip, session };
        return dizi;
    }


}
