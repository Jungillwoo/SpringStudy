package com.example.service_0218;

import mybatis.service.EmpMapper;
import mybatis.vo.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmpControl {

  @Autowired
  private EmpMapper empMapper;

  @RequestMapping("empList")
  public String empList(Model model){ // model 은 스프링이 자동으로 만든다. model 은 request 에 존재한다.

    try {
      // 사원들 모두의 정보를 가져온다.
      EmpVO[] ar = empMapper.allEmp();
      model.addAttribute("ar",ar); // request 에 저장하는 의미
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "empList";
  }

  @RequestMapping(value = "empSearch", method = RequestMethod.POST)
  public ModelAndView empSearch(@RequestParam("type") int type, @RequestParam("value") String value){

    ModelAndView mv = new ModelAndView();

    try {
      EmpVO[] ar = empMapper.searchEmp(type, value);
      mv.addObject("ar",ar);
    } catch (Exception e) {
      e.printStackTrace();
    }
    mv.setViewName("empList");
    return mv;
  }
}