package com.create.shop.item;

import com.create.shop.comment.Comment;
import com.create.shop.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final CommentRepository commentRepository;
    @GetMapping("/list")
    String list(Model model, Authentication auth){
        String username = "";
        if(auth != null)
            username = auth.getName();
        model.addAttribute("username", username);
        ArrayList<Item> result = itemService.findAll();
        model.addAttribute("items", result);
        return "list.html";
    }
    @GetMapping("/list/{id}")
    String getListPage(Model model, @PathVariable Integer id) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(id - 1, 5));
        int totalPages = result.getTotalPages();
        model.addAttribute("currentPages", id);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("items", result);
        return "list.html";
    }
    @GetMapping("/write")
    String write(){
        return "write.html";
    }
    @PostMapping("/add")
    String addPost(@RequestParam String title, @RequestParam Integer price, Authentication auth) {
        if(auth == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "로그인이 안되어있는데 어캐해요");
        itemService.saveItem(title, price, auth.getName());
        return "redirect:/list/1";
    }
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model) {
        try{
            Optional<Item> result = itemService.findById(id);
            if(result.isPresent()){
                List<Comment> comments = commentRepository.findAllByParentId(id);
                model.addAttribute("item", result.get());
                model.addAttribute("comments", comments);
                return "detail.html";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/list";
        }
        return "detail.html";
    }
    @GetMapping("/edit/{id}")
    String edit(@PathVariable Long id, Model model){
        Optional<Item> result = itemService.findById(id);
        if(result.isPresent()){
            model.addAttribute("item", result.get());
            return "edit.html";
        }
        return "redirect:/list";
    }
    @PostMapping("/edit/{id}")
    String editItem(@PathVariable Long id, @RequestParam String title, @RequestParam Integer price){
        itemService.updateItem(id, title, price);
        return "redirect:/list";
    }
    @DeleteMapping("/item/{id}")
    ResponseEntity<Boolean> deleteItem(@PathVariable Long id){
        itemService.deleteItem(id);
        return ResponseEntity.status(200).body(true);
    }
    @GetMapping("/error")
    String error() throws Exception{
        throw new Exception();
    }
    @PostMapping("/test")
    String test(@RequestBody Map<String, Object> body){
        System.out.println(body.get("name"));
        return "redirect:/list";
    }
    @GetMapping("/search")
    String postSearch(@RequestParam String searchText, Model model){
        var result = itemRepository.findAllByTitleContains(searchText);
        model.addAttribute("items", result);
        return "search.html";
    }
//    @ExceptionHandler(Exception.class)
//    public void handler(){
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("니잘못");
//    }

}
