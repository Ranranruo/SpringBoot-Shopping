package com.create.shop.member;

import com.create.shop.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    public ServiceMessage addMember(String username, String password, String displayName){
        Optional<Member> isAlready = memberRepository.findByUsername(username);
        ServiceMessage serviceMessage = new ServiceMessage();
        if(!isAlready.isEmpty()){
            serviceMessage.setResult(false);
            serviceMessage.setMessage("이미 있는 아이디 입니다.");
        }
        Member member = new Member();
        member.setUsername(username);
        var hash = new BCryptPasswordEncoder().encode(password);
        member.setPassword(hash);
        member.setDisplayName(displayName);
        memberRepository.save(member);
        serviceMessage.setResult(true);
        serviceMessage.setMessage("성공");
        return serviceMessage;
    }
    public Optional<Member> findById(Long id){
        return memberRepository.findById(id);
    }
    public Optional<Member> findByUsername(String username){
        Optional<Member> result = memberRepository.findByUsername(username);
        return result;
    }
}
