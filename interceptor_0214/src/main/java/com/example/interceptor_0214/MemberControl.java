package com.example.interceptor_0214;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MemberControl {
  // DB에서 인증을 거칠려면 DAO들이 있어야 한다. (생략)

  @Autowired
  private HttpSession session;

  @RequestMapping("/login")
  public String login(){ // GET방식
    return "login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ModelAndView login(@RequestParam String m_id, @RequestParam String m_pw){

    ModelAndView mv = new ModelAndView();

    // DAO를 통해 DB인증 확인

    // 로그인 처리를 위한 session 작업
    session.setAttribute("mvo", m_id);
    mv.setViewName("redirect:/");
    return mv;
  }
}