package com.example.interceptor_0214;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexControl {

  @RequestMapping("/")
  public String index(){
    return "index";
  }

  @RequestMapping("/sub/bravo")
  public ModelAndView sub(){
    ModelAndView mv = new ModelAndView("bravo");

    mv.addObject("msg", "Hello World");
    return mv;
  }
}