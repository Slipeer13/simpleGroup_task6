package com.simpleGroup.controller;

import com.simpleGroup.entity.Consumer;
import com.simpleGroup.entity.Product;
import com.simpleGroup.service.ConsumerService;
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


    //todo Как-то странно получать потребителей через контроллер товаров
    //      Перемудрил с алгоритмом. Сейчас получается:
    //      1. берём из базы продукт с заданным id
    //      2. ещё раз берём из базы продукт с заданным id. Зачем, он ведь у нас уже получен?
    //      3. Из продукта, полученного в п.2 получаем список потребителей.
    //      Зачем за одним и тем же продуктом ходить в базу 2 раза?
    //      Зачем логику получения потребителей выносить в dao?
    @RequestMapping("/showConsumersByProduct")
    public String showConsumersByProduct(@RequestParam("productId") Long id, Model model) {
        Product product = consumerService.findByIdProduct(id);
        model.addAttribute("consumers", product.getConsumers());
        model.addAttribute("titleProduct", product.getTitle());
        return "viewConsumerByProduct";
    }

    //todo Зачем контроллеру потребителей зависеть от сервиса продуктов?
    @RequestMapping("/addProductToCart")
    public String addProductToCart(@RequestParam("consumerId") Long id, Model model) {
        List<Product> list = consumerService.findAllProducts();
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
        Product product = consumerService.findByIdProduct(productId);
        consumerService.saveProductToCart(consumerId, product);
        return "redirect:/";
    }

}
