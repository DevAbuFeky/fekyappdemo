package com.demoappfeky.controller;

import com.demoappfeky.model.Category;
import com.demoappfeky.repository.productRepo.CategoryRepository;
import com.demoappfeky.services.productServices.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    public CategoryController(CategoryRepository categoryRepository, CategoryService categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/categoriesList" ,method = RequestMethod.GET)
    public String listCategories(Model model){
        List<Category> categoriesList = categoryRepository.findAll();
        model.addAttribute("categoriesList",categoriesList);
        return "categoriesList";
    }

    @RequestMapping(value = "/new" ,method = RequestMethod.GET)
    public String showCategoryNewForm(Model model){
        model.addAttribute("category", new Category());

        return "addCategory";
    }

    @RequestMapping(value = "/save" ,method = RequestMethod.POST)
    public String saveCategory(Category category, RedirectAttributes redirectAttributes){
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("message", "Category has been saved successfully.");
        return "redirect:categoriesList";
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@ModelAttribute("id") Integer id, Model model){
        categoryService.removeOne(id);
        List<Category> categoriesList = categoryRepository.findAll();
        model.addAttribute("categoriesList", categoriesList);
        return "redirect:/categories/categoriesList";
    }
}
