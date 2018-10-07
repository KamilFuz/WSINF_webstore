package com.webstore.controller;

import com.webstore.domain.Product;
import com.webstore.domain.User;
import com.webstore.service.ProductService;
import com.webstore.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @RequestMapping("/searchByCategory")
    public String searchByCategory(
            @RequestParam("category") String category,
            Model model, Principal principal
    ){
        if(principal!=null) {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
        classActiveCategory = classActiveCategory.replaceAll("&", "");
        model.addAttribute(classActiveCategory, true);

        List<Product> productList = productService.findByCategory(category);

        if (productList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "storage";
        }

        model.addAttribute("productList", productList);

        return "storage";
    }
}

