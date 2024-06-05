package com.create.shop.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final MemberService memberService;
    public MyUserDetailsService (MemberService memberService){
        this.memberService = memberService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberService.findByUsername(username);
        if(result.isEmpty()){
            throw new UsernameNotFoundException("그런아이디 없음");
        }
        Member user = result.get();
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority("일반유저")) ;
        CustomUser data = new CustomUser(user.getUsername(), user.getPassword(), role);
        data.setDisplayName(user.getDisplayName() );
        return data;

    }

}
