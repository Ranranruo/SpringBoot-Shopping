package com.create.shop.member;

import com.create.shop.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Controller
public class MemberController {
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
    @GetMapping("/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/register")
    public String memberAddView(Authentication auth){
        if(auth != null)
            return "redirect:/list";
        return "register.html";
    }
    @PostMapping("/register")
    public String memberAdd(@RequestParam String username, @RequestParam String password, @RequestParam String displayName){
        ServiceMessage result = memberService.addMember(username, password, displayName);
        return "redirect:/";
    }
    @GetMapping("/my-page")
    public String myPage(Authentication auth){
//        System.out.println(auth.getAuthorities().contains(new SimpleGrantedAuthority("일반유저")));
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.getDisplayName());
        return "mypage.html";
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public MemberDto getUser(@PathVariable Long id){
        Optional<Member> user = memberService.findById(id);
        var result = user.get();
        MemberDto data = new MemberDto(result.getUsername(), result.getDisplayName());
        return data;
    }
}
class MemberDto{
    public String username;
    public String displayName;
    public MemberDto(String username, String displayName){
        this.username = username;
        this.displayName = displayName;
    }
}
