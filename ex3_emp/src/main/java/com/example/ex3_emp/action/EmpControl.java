package com.example.ex3_emp.action;

import com.example.ex3_emp.dao.EmpDAO;
import com.example.ex3_emp.vo.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class EmpControl {

  @Autowired
  private EmpDAO empDAO;

  @RequestMapping("/emp/list")
  public ModelAndView empList() {
    ModelAndView mv = new ModelAndView();

    EmpVO[] ar = empDAO.getList();

    System.out.println("데이터 개수: " + ar.length);
    System.out.println("데이터 확인: " + Arrays.toString(ar)); // 데이터 확인

    mv.addObject("ar", ar);
    mv.setViewName("emp/list");
    return mv;
  }
}
