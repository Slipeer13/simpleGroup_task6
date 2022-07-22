package com.simplegrouptask6.controller;

import com.simplegrouptask6.entity.Purchase;
import com.simplegrouptask6.entity.Product;
import com.simplegrouptask6.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Validated
@Controller
public class ProductController {
    private ProductService productService;
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/showProducts")
    public String showAllProducts(Model model) {
        List<Product> list = productService.findAllProducts();
        model.addAttribute("products", list);
        return "viewProduct";
    }

    @RequestMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "saveOrUpdateProduct";
    }

    @RequestMapping("/saveOrUpdateProduct")
    public String saveOrUpdateProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "saveOrUpdateProduct";
        }
        productService.saveOrUpdateProduct(product);

        return "redirect:/";
    }

    @RequestMapping("/updateProduct")
    public String updateProduct(@RequestParam("productId") Long id, Model model) {
        Product product = productService.findByIdProduct(id);
        model.addAttribute("product", product);
        return "saveOrUpdateProduct";
    }

    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") Long id) {
        productService.deleteByIdProduct(id);
        return "redirect:/";
    }

    @RequestMapping("/showProductByConsumer")
    public String showProductByConsumer(@RequestParam("consumerId") Long consumerId, Model model) {
        List<Purchase> purchases = productService.findAllProductsByConsumerId(consumerId);
        model.addAttribute("purchases", purchases);
        return "viewProductConsumer";
    }

    @ExceptionHandler
    public ModelAndView handleException(EntityNotFoundException exception) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("exception", exception.getMessage());
        return modelAndView;
    }
    @ExceptionHandler
    public ModelAndView handleException(EntityExistsException exception) {
        ModelAndView modelAndView = new ModelAndView("errorPage");
        modelAndView.addObject("exception", exception.getMessage());
        return modelAndView;
    }
}
