package com.create.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class NoticeController {
    NoticeRepository noticeRepository;
    @Autowired
    public NoticeController(NoticeRepository noticeRepository){
        this.noticeRepository = noticeRepository;
    }
    @GetMapping("/notice")
    public String notice(Model model){
        ArrayList<Notice> result = (ArrayList<Notice>) noticeRepository.findAll();
        model.addAttribute("notices", result);
        return "notice.html";
    }
//    @ResponseBody
//    @GetMapping("/test")
//    public String test(){
//        Optional<Notice> result = noticeRepository.findByTitle("입금자명 제발 쓸까요");
//        return result.get().toString();
//    }
}
