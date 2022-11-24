package com.demoappfeky.services.productServices;

import com.demoappfeky.model.Book;
import com.demoappfeky.repository.productRepo.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book save(Book book){
        return bookRepository.save(book);
    }

    public List<Book> findAll(){
        return bookRepository.findAll();
    }

    public Optional<Book> findOne(Long id){
        return bookRepository.findById(Math.toIntExact(id));
    }

    public void removeOne(Long id) {
        bookRepository.deleteById(Math.toIntExact(id));
    }
}
