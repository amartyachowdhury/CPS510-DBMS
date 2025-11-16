package com.cps510.controller;

import com.cps510.dao.CustomerDAO;
import com.cps510.dao.OrderDAO;
import com.cps510.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private CustomerDAO customerDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private OrderDAO orderDAO;

    @GetMapping("/")
    public String index(Model model) {
        // Get statistics for dashboard
        long customerCount = customerDAO.findAll().size();
        long productCount = productDAO.findAll().size();
        long orderCount = orderDAO.findAll().size();
        
        model.addAttribute("customerCount", customerCount);
        model.addAttribute("productCount", productCount);
        model.addAttribute("orderCount", orderCount);
        
        return "index";
    }
}

