package am.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Test1Action {
  @RequestMapping("/member/login")
  public ModelAndView test1() {
    ModelAndView mv = new ModelAndView();
    mv.setViewName("ex1"); // WEB-INF/views/ex1.jsp를 의미한다.
    return mv;
  }

  @RequestMapping("/member/test")
  public String test2() {
    return "member/ex2";
  }
}