package com.example.ex2;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;

public class DateAction implements Controller {

  public DateAction() {
    System.out.println("DateAction생성");
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    LocalDate today = LocalDate.now();

    ModelAndView mv = new ModelAndView();

    mv.addObject("today", today.toString());
    mv.setViewName("ex2");

    return mv;
  }
}