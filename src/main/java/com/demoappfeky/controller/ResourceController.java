package com.demoappfeky.controller;

import com.demoappfeky.repository.productRepo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class ResourceController {
    private final BookRepository bookRepository;

    public ResourceController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(value = "/product/removeList", method = RequestMethod.POST)
    public String removeList(@RequestBody ArrayList<String> bookIdList, Model model){
        for (String id : bookIdList){
            String bookId = id.substring(8);
//            bookRepository.deleteById(bookId);
        }

        return "delete success";
    }
}
