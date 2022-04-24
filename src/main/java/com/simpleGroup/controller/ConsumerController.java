package com.simpleGroup.controller;

import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;
import com.simpleGroup.service.ConsumerService;
import com.simpleGroup.service.ProductService;
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
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

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

    //todo Это метод для ProductController
    //      См. комментарии к аналогичному методу для ProductController
    @RequestMapping("/showProductByConsumer")
    public String showProductByConsumer(@RequestParam("consumerId") Long id, Model model) {
        Consumer consumer = consumerService.findByIdConsumer(id);
        List<Product> productList = consumerService.findAllProductsByConsumer(id);
        model.addAttribute("products", productList);
        model.addAttribute("nameConsumer", consumer.getName());
        return "viewProductConsumer";
    }

    //todo Зачем контроллеру потребителей зависеть от сервиса продуктов?
    @RequestMapping("/addProductToCart")
    public String addProductToCart(@RequestParam("consumerId") Long id, Model model) {
        List<Product> list = productService.findAllProducts();
        model.addAttribute("products", list);
        model.addAttribute("consumerId", id);
        return "addProductToCart";
    }

    //todo Убери всю эту логику в consumerService.
    //      Лучше сделай так. Контроллер принял запрос с параметрами. Запросил результат у своего сервиса с этими параметрами.
    //      Сервис параметры обработал, результат вернул в контроллер. Как сервис обрабатывает параметры,
    //      контроллеру без разницы. Ему нужен только результат, чтобы ответ сформировать.
    @RequestMapping("/saveProductToCart")
    public String saveProductToCart(@RequestParam("consumerId") Long consumerId, @RequestParam("productId") Long productId) {
        Product product = productService.findByIdProduct(productId);
        consumerService.saveProductToCart(consumerId, product);
        return "redirect:/";
    }

}
