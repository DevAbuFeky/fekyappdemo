package com.demoappfeky.controller;

import com.demoappfeky.configuration.FileUploadUtil;
import com.demoappfeky.model.Book;
import com.demoappfeky.model.Category;
import com.demoappfeky.repository.productRepo.CategoryRepository;
import com.demoappfeky.services.productServices.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class BookController {

    private final BookService bookService;
    private final CategoryRepository categoryRepository;

    public BookController(BookService bookService, CategoryRepository categoryRepository) {
        this.bookService = bookService;
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.GET)
    public String addProduct(Model model){
        List<Category> listCategories = categoryRepository.findAll();
        Book book = new Book();
        model.addAttribute("book", book);
        model.addAttribute("listCategories", listCategories);

        return "addProduct";
    }

    @RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProductPost(@ModelAttribute(name = "book") Book book,
                                 RedirectAttributes redirectAttributes, @RequestParam("bookImage") MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            book.setLogo(fileName);
            Book savedBook = bookService.save(book);

            String uploadDir = "image/" + savedBook.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
         if (book.getLogo().isEmpty()){
             book.setLogo(null);
         }
         bookService.save(book);
        }

        redirectAttributes.addFlashAttribute("message", "The Product has been saved successfully.");

        return "redirect:productsList";
    }

    @RequestMapping("/productDetails")
    public String productDetails(@RequestParam("id") Long id, Model model) throws Exception {

        Optional<Book> book = bookService.findOne(id);
        if(book.isPresent()) {
            System.out.println(book.get().getId());
            model.addAttribute("book", book.get());

            return "productDetails";
        } else {
            throw new Exception("book not found");
        }
    }

    @RequestMapping("/updateProduct")
    public String updateProduct(Model model,@RequestParam("id") Long id) throws Exception {

        Optional<Book> book = bookService.findOne(id);
        if(book.isPresent()) {
            List<Category> listCategories = categoryRepository.findAll();
            model.addAttribute("listCategories", listCategories);
            System.out.println(book.get().getId());
            model.addAttribute("book", book.get());
            return "updateProduct";
        } else {
            throw new Exception("Product not found");
        }
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public String updateProductPost(@ModelAttribute("book") Book book, RedirectAttributes redirectAttributes, @RequestParam("bookImage") MultipartFile multipartFile) throws IOException{


        bookService.save(book);

        if (!multipartFile.isEmpty()){
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            book.setLogo(fileName);
            book.setLogo(fileName);
            Book savedBook = bookService.save(book);

            String uploadDir = "image/" + savedBook.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (book.getLogo().isEmpty()){
                book.setLogo(null);
            }
            bookService.save(book);
        }
        redirectAttributes.addFlashAttribute("message", "The Product has been updated successfully.");

        return "redirect:/product/productDetails?id=" + book.getId();
    }

    @RequestMapping("/productsList")
    public String products(Model model){
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "productsList";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute("id") String id, Model model){
        bookService.removeOne(Long.parseLong(id.substring(8)));
        List<Book> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);

        return "redirect:/product/productsList";
    }
}
