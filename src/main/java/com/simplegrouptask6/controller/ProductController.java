package com.simplegrouptask6.controller;

import com.simplegrouptask6.entity.Consumer;
import com.simplegrouptask6.entity.Order;
import com.simplegrouptask6.entity.Product;
import com.simplegrouptask6.service.ProductService;
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
    public String saveOrUpdateProduct(@ModelAttribute("product") Product product) {
        if (product != null) {
            if(product.getTitle() == null || product.getPrice() == null) {
                throw new EntityNotFoundException("the product title or price is null");
            }
            else
            if(!product.getTitle().trim().isEmpty() && product.getPrice() > 0) {
                Boolean exist = productService.checkProductByTitleAndPrice(product.getTitle(), product.getPrice());
                if (!exist) {
                    productService.saveOrUpdateProduct(product);
                }
                else throw new EntityExistsException("there is such a product in database");
            }
            else {
                throw new EntityNotFoundException("the product title is empty or price is negative");
            }
        }
        else {
            throw new EntityNotFoundException("the product is null");
        }

        return "redirect:/";
    }

    //todo Я понимаю, что сейчас всё привязано к view, и теоретически не получится попасть в этот метод
    //      с id отсутствующего в БД продукта, если его использует 1 человек.
    //      Но если сервисом одновременно пользуются 2 человека, то один может уже удалить какой-то продукт из базы,
    //      тогда для второго пользователя с таким же id вернётся продукт null. И что тогда метод будет менять?
    //      Не существующий в БД продукт?
    //      Второй вариант. Меняем продукт, но задаём существующие название и стоимость.
    //      Продукт ведь не изменится, но мы об этом даже не узнаем. Может стоит эту ситуацию как-то обрабатывать?
    @RequestMapping("/updateProduct")
    public String updateProduct(@RequestParam("productId") Long id, Model model) {
        Product product = productService.findByIdProduct(id);
        if(product == null) {
            throw new EntityNotFoundException("There is no product with id = " + id);
        }
        model.addAttribute("product", product);
        return "saveOrUpdateProduct";
    }

    @RequestMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") Long id) {
        Product product = productService.findByIdProduct(id);
        if(product == null) {
            throw new EntityNotFoundException("There is no product with id = " + id);
        }
        productService.deleteByIdProduct(id);
        return "redirect:/";
    }

    //todo Я думал, что ты отношение корзины (consumer_product) расширишь столбцом, с количеством выбранных продуктов.
    //      И сделаешь сущность для корзины для связи между потребителями и продуктами.
    //      А прямую связь исключишь, раз мы заговорили про количество продуктов.
    //      Но, наверное, это выходит за рамки текущего задания и будет реализовываться в дальнейших тасках.
    //      Так что давай количество оставим на твоё усмотрение, если интересно, то конечно можешь довести до ума.
    //      При текущей реализации получается, что в таблице корзины может быть куча одинаковых записей,
    //      если добавить много одинаковых продуктов.
    //      Это не соответствует требованиям даже первой нормальной формы таблиц реляционной базы данных.
    //      Обычно приводят таблицы РБД к 3-й нормальной форме. И лучше сделать отдельную сущность корзины.
    @RequestMapping("/showProductByConsumer")
    public String showProductByConsumer(@RequestParam("consumerId") Long id, Model model) {
        Consumer consumer = productService.findByIdConsumer(id);
        if(consumer != null) {
            List<Order> orders = consumer.getOrders();
            model.addAttribute("orders", orders);
            model.addAttribute("nameConsumer", consumer.getName());
        }
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
