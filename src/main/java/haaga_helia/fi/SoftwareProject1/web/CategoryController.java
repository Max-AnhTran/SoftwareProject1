package haaga_helia.fi.SoftwareProject1.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import haaga_helia.fi.SoftwareProject1.domain.Category;
import haaga_helia.fi.SoftwareProject1.domain.CategoryRepository;

@Controller
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;

    // Category CRUD methods
    @GetMapping("/addcategory")
    public String addCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "addcategory";
    }

    @GetMapping("/editcategory/{id}")
    public String editCategoryForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).orElse(null));
        return "editcategory";
    }

    @PostMapping("/savecategory")
    public String saveCategory(@ModelAttribute Category category) {
        categoryRepository.save(category);
        return "redirect:/";
    }

    @GetMapping("/deletecategory/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryRepository.deleteById(id);
        return "redirect:/";
    }
}