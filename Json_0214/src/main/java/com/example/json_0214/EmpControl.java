package com.example.json_0214;

import mybatis.dao.EmpDAO;
import mybatis.vo.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class EmpControl {

  @Autowired
  private EmpDAO empDAO;

  @RequestMapping(value = "/emp/total", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> total() {
    Map<String, Object> map = new HashMap<>();

    EmpVO[] ar = empDAO.getTotal();
    map.put("list", ar);
    map.put("count", ar.length);

    return map;
  }
}