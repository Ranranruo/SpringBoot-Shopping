package com.create.shop.comment;

import com.create.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;
    @PostMapping("/comment")
    public String postComment(@RequestParam String content, @RequestParam Long parent, Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal();
        Comment comment = new Comment();
        comment.setUsername(user.getUsername());
        comment.setContent(content);
        comment.setParentId(parent);
        commentRepository.save(comment);
        return "redirect:/list/1";
    }
}
