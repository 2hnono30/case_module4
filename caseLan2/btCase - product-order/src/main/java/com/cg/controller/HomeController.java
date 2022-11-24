package com.cg.controller;


import com.cg.model.Role;
import com.cg.model.User;
import com.cg.model.dto.UserDTO;
import com.cg.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("")
public class HomeController {
    @Autowired
    private UserService userService;


    private String getPrincipal() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = "";
        }

        return email;
    }

    //get role

    private UserDTO getUserDTO(String email) {
        User user = userService.findByEmail(email).get();
        Role role = user.getRole();
        UserDTO userDTO = user.toUserDTO();
        userDTO.setRoleDTO(role.toRoleDTO());
        return userDTO;
    }

    @GetMapping("home")
    public ModelAndView showProduct(){
        ModelAndView modelAndView = new ModelAndView();
        if (!getPrincipal().equals("")){
            UserDTO userDTO = getUserDTO(getPrincipal());
            modelAndView.addObject("user",userDTO);
            modelAndView.addObject("role",userDTO.getRoleDTO());
//            if (userDTO.getRoleDTO().getCode().equalsIgnoreCase("admin")){
//                modelAndView.setViewName("/product/list");
//                return modelAndView;
//            }
        }else {
            modelAndView.addObject("user", null);
            modelAndView.addObject("role", null);
        }
        modelAndView.setViewName("/home");
        return modelAndView;
    }


    @GetMapping("login")
    public ModelAndView showformLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/login");
        return modelAndView;
    }

    @GetMapping("register")
    public ModelAndView showRegisterPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/register");
        return modelAndView;
    }
    @GetMapping("cart")
    public ModelAndView showCartPage() {
        ModelAndView modelAndView = new ModelAndView();

        if (!getPrincipal().equals("")){
            UserDTO userDTO = getUserDTO(getPrincipal());
            modelAndView.addObject("user",userDTO);
            modelAndView.addObject("role",userDTO.getRoleDTO());
        }else {
            modelAndView.addObject("user", null);
            modelAndView.addObject("role", null);
        }
        modelAndView.setViewName("/cart");
        return modelAndView;
    }
//    @GetMapping("/home")
//    public ModelAndView showHomePage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("index");
//
//        return modelAndView;
//    }

//    @GetMapping("/login")
//    public ModelAndView showLoginPage() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("login");
//
//        return modelAndView;
//    }
}
