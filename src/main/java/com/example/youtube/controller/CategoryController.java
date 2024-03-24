package com.example.youtube.controller;

import com.example.youtube.model.Category;
import com.example.youtube.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("/categorys")
    public ModelAndView ShowListBlog() {
        Iterable<Category> categories = categoryService.findAll();

        ModelAndView modelAndView = new ModelAndView("/list");
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }
//    @PostMapping("/create-category")
//    public ModelAndView createCategory(@ModelAttribute("category") Category category) {
//        try{
//        categoryService.save(category);
//
//        ModelAndView modelAndView = new ModelAndView("/list");
//        modelAndView.addObject("category", category);
//        return modelAndView;
//    }catch (Exception ex){
//            ex.getMessage();
//        }
//return null;
//
//}
@GetMapping("/edit-category/{id}")
public ModelAndView showUpdateForm(@PathVariable Long id) {
    Optional<Category> category = categoryService.findById(id);


    ModelAndView modelAndView = new ModelAndView("/edit");
    modelAndView.addObject("category", category.get());
    return modelAndView;
}

    @PostMapping("/edit-category")
    public ModelAndView updateCategory(@ModelAttribute("category") Category category) {
        categoryService.save(category);

        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("category", category);
        modelAndView.addObject("message", "Category updated thành công");
        return modelAndView;
    }

    @GetMapping("/delete-category/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Category> category = categoryService.findById(id);



        ModelAndView modelAndView = new ModelAndView("/delete");
        modelAndView.addObject("category", category.get());
        return modelAndView;
    }

    @PostMapping("/delete-category")
    public String deleteCategory(@ModelAttribute("category") Category category) {
        categoryService.remove(category.getCategory_id());

        return "redirect:categorys";
    }


}