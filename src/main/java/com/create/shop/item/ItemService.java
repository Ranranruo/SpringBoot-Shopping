package com.create.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    public void saveItem(String title, Integer price, String username){
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setUsername(username);
        itemRepository.save(item);
    }
    public void updateItem(Long id, String title, Integer price){
        Optional<Item> isAlready = itemRepository.findById(id);
        if(isAlready.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"수정할 item이 없음");
        if(title.length() > 30)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "제목이 너무 긴거 아닙니까...");
        if(price < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "가격이 "+price+"원이 뭡니까...");
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }
    public void deleteItem(Long id){
        Optional<Item> isAlready = itemRepository.findById(id);
        if(isAlready.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"제거할 item이 없음");
        itemRepository.deleteById(id);
    }
    public Optional<Item> findById (Long id) {
        return itemRepository.findById(id);
    }
    public ArrayList<Item> findAll (){
        return (ArrayList<Item>) itemRepository.findAll();
    }
}
