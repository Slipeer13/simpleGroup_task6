package com.simplegrouptask6.controller;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Product;
import com.simplegrouptask6.entity.Purchase;
import com.simplegrouptask6.service.ConsumerService;
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
import java.util.stream.Collectors;

@Validated
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

    @RequestMapping("/addConsumer")
    public String addConsumer(Model model) {
        model.addAttribute("consumer", new Consumer());
        return "saveOrUpdateConsumer";
    }

    @RequestMapping("/saveOrUpdateConsumer")
    public String saveOrUpdateConsumer(@Valid @ModelAttribute("consumer")Consumer consumer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "saveOrUpdateConsumer";
        }
        consumerService.saveOrUpdateConsumer(consumer);
        return "redirect:/";
    }

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
        List<Consumer> consumers = consumerService.findAllConsumersByProductId(id);
        model.addAttribute("consumers", consumers);
        return "viewConsumerByProduct";
    }

    //todo Название методов я бы поменял. В приложении уже нет корзины. Вводит в заблуждение.
    // На работоспособность конечно не влияет.
    @RequestMapping("/addProductToCart")
    public String addProductToCart(@RequestParam("consumerId") Long id, Model model) {
        List<Product> list = consumerService.findAllProducts();
        model.addAttribute("products", list);
        model.addAttribute("consumerId", id);
        return "addProductToCart";
    }

    @RequestMapping("/saveProductToCart")
    public String saveProductToCart(@RequestParam("consumerId") Long consumerId, @RequestParam("productId") Long productId) {
        consumerService.saveProductToCart(consumerId, productId);
        return "redirect:/";
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
