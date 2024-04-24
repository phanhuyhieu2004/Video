package com.example.youtube.controller;

import com.example.youtube.model.Category;

import com.example.youtube.model.Video;
import com.example.youtube.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categorys")
    public ModelAndView ShowList(@RequestParam(name = "s", required = false) String searchTerm) {
        Iterable<Category> categories ;
        if (searchTerm != null && !searchTerm.isEmpty()) {
            categories = categoryService.findAllByNameContaining(searchTerm);
        } else {
            categories = categoryService.findAll();
        }

        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }
    @GetMapping("/create-category")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("add1");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }
    @PostMapping("/create-category")
    public String createCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes, Model model) {

        categoryService.save(category);
        model.addAttribute("category", category);
//        modelAndView.addObject("message","thành công rồi");
        redirectAttributes.addFlashAttribute("msg", "Thêm thành công");

        return "redirect:/categorys";


}

    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable Long id,RedirectAttributes redirectAttributes) {
        Optional<Category> category = categoryService.findById(id);
        categoryService.remove(category.get().getCategory_id());
        redirectAttributes.addFlashAttribute("msg", "Xóa thành công");

        return "redirect:/categorys";
    }


}