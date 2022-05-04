package com.simplegrouptask6.controller;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Order;
import com.simplegrouptask6.entity.Product;
import com.simplegrouptask6.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
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
        if (consumer != null) {
            if(consumer.getName() == null) {
                throw new EntityNotFoundException("the consumer name is null");
            }
            else
            if(!consumer.getName().trim().isEmpty()) {
                Boolean exist = consumerService.checkConsumerToDB(consumer);
                if (!exist) {
                    consumerService.saveOrUpdateConsumer(consumer);
                }
                else throw new EntityExistsException("there is such a consumer in database");
            }
            else {
                throw new EntityNotFoundException("The consumer name is empty");
            }
        }
        else {
            throw new EntityNotFoundException("the consumer is null");
        }

        return "redirect:/";
    }

    //todo См. updateProduct
    @RequestMapping("/updateConsumer")
    public String updateConsumer(@RequestParam("consumerId") Long id, Model model) {
        Consumer consumer = consumerService.findByIdConsumer(id);
        if(consumer == null) {
            throw new EntityNotFoundException("There is no consumer with id = " + id);
        }
        model.addAttribute("consumer", consumer);

        return "saveOrUpdateConsumer";
    }

    @RequestMapping("/deleteConsumer")
    public String deleteConsumer(@RequestParam("consumerId") Long id) {
        Consumer consumer = consumerService.findByIdConsumer(id);
        if(consumer == null) {
            throw new EntityNotFoundException("There is no consumer with id = " + id);
        }
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
        if(product != null) {
            model.addAttribute("orders", product.getOrders());
            model.addAttribute("titleProduct", product.getTitle());
        }
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
