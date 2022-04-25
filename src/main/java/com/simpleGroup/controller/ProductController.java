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

//todo Файл pom так и не привёл в порядок. Комментарии были ещё в 4 таске.
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

    //todo Проверь для себя, будет ли корректно работать с данными для продукта, который уже существует в базе.
    //      (одинаковые title и price)
    @RequestMapping("/addProduct")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        return "saveOrUpdateProduct";
    }

    @RequestMapping("/saveOrUpdateProduct")
    public String saveOrUpdateProduct(@ModelAttribute("product") Product product) {
        productService.saveOrUpdateProduct(product);
        return "redirect:/";
    }

    //todo Аналогично добавлению нового продукта. Проверь для себя, если изменённые данные соответствуют данными
    //      другого продукта, уже существующего в базе. Корректно ли работает?
    //      Скриптов нет на БД. Не понятно, какое поведение ожидается в каких-то граничных случаях.
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

    //todo Это метод для ProductController
    //      См. комментарии к аналогичному методу для ProductController
    @RequestMapping("/showProductByConsumer")
    public String showProductByConsumer(@RequestParam("consumerId") Long id, Model model) {
        Consumer consumer = productService.findByIdConsumer(id);
        model.addAttribute("products", consumer.getProducts());
        model.addAttribute("nameConsumer", consumer.getName());
        return "viewProductConsumer";
    }
}
