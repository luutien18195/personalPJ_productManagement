package com.codegym.productmanagement.controller;

import com.codegym.productmanagement.model.Customer;
import com.codegym.productmanagement.model.Item;
import com.codegym.productmanagement.model.Product;
import com.codegym.productmanagement.service.CustomerService;
import com.codegym.productmanagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/mycart")
public class CartController {
    @Autowired
    ProductService productService;

    @Autowired
    CustomerService customerService;

    private static List<Item> cart = new ArrayList<Item>();
    @GetMapping
    public String showCartPage(Model model, HttpSession session){
        Customer customer = (Customer) session.getAttribute("current_customer");
        session.setAttribute("cart",cart);
        session.setAttribute("current_customer",customer);
        return "mycart";
    }

    @GetMapping("/add/{id}")
    public String showCartPage(Model model, @PathVariable int id, HttpSession session){
        Customer customer = (Customer) session.getAttribute("current_customer");
        if(session.getAttribute("cart")==null){
            cart.add(new Item(this.productService.findById(id),1));
        }else {
            if(isExited(id)){
                cart.get(findByProductId(id)).quantityIncrease();
            }
            else{
                cart.add(new Item(this.productService.findById(id),1));
            }
        }
        session.setAttribute("cart",cart);
        session.setAttribute("current_customer",customer);
        return "mycart";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(Model model, @PathVariable int id, HttpSession session){

        return "mycart";
    }

    private boolean isExited(int id){
        for(int i = 0; i < cart.size(); i++){
            if(cart.get(i).getProduct().getId()==id){
                return true;
            }
        }
        return false;
    }

    private int findByProductId(int id){
        for(int i = 0; i < cart.size(); i++){
            if(cart.get(i).getProduct().getId()==id){
                return i;
            }
        }
        return -1;
    }
}
