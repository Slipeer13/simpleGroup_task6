package com.simplegrouptask6.controller;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Product;
import com.simplegrouptask6.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ConsumerController {
    private ConsumerService consumerService;

    @Autowired
    public void setConsumerService(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    @RequestMapping("/")
    public String showAllConsumers(Model model) {
        List<Consumer> list = consumerService.findAllConsumers();
        model.addAttribute("consumers", list);
        return "viewConsumer";
    }

    //todo См. addProduct
    @RequestMapping("/addConsumer")
    public String addConsumer(Model model) {
        model.addAttribute("consumer", new Consumer());
        return "saveOrUpdateConsumer";
    }

    @RequestMapping("/saveOrUpdateConsumer")
    public String saveOrUpdateConsumer(@ModelAttribute("consumer")Consumer consumer) {
        consumerService.saveOrUpdateConsumer(consumer);
        return "redirect:/";
    }

    //todo См. updateProduct
    @RequestMapping("/updateConsumer")
    public String updateConsumer(@RequestParam("consumerId") Long id, Model model) {
        Consumer consumer = consumerService.findByIdConsumer(id);
        model.addAttribute("consumer", consumer);

        return "saveOrUpdateConsumer";
    }

    @RequestMapping("/deleteConsumer")
    public String deleteConsumer(@RequestParam("consumerId") Long id) {
        consumerService.deleteByIdConsumer(id);
        return "redirect:/";
    }

    @RequestMapping("/showConsumersByProduct")
    public String showConsumersByProduct(@RequestParam("productId") Long id, Model model) {
        Product product = consumerService.findByIdProduct(id);
        model.addAttribute("consumers", product.getConsumers());
        model.addAttribute("titleProduct", product.getTitle());
        return "viewConsumerByProduct";
    }

    @RequestMapping("/addProductToCart")
    public String addProductToCart(@RequestParam("consumerId") Long id, Model model) {
        List<Product> list = consumerService.findAllProducts();
        model.addAttribute("products", list);
        model.addAttribute("consumerId", id);
        return "addProductToCart";
    }

    @RequestMapping("/saveProductToCart")
    public String saveProductToCart(@RequestParam("consumerId") Long consumerId, @RequestParam("productId") Long productId) {
        Product product = consumerService.findByIdProduct(productId);
        consumerService.saveProductToCart(consumerId, product);
        return "redirect:/";
    }

}
