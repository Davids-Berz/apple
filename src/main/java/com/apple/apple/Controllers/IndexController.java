package com.apple.apple.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apple.apple.models.IService;

@Controller
@RequestMapping("")
public class IndexController {
    
    @Autowired
    private IService iService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("servicio", iService.operacion());
        return "index";
    }
}
