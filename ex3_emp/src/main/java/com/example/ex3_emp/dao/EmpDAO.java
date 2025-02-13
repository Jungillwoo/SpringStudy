package com.example.ex3_emp.dao;

import com.example.ex3_emp.vo.EmpVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmpDAO {

  @Autowired
  private SqlSessionTemplate ss;

  public EmpVO[] getList() {

    EmpVO[] ar = null;

    List<EmpVO> list = ss.selectList("emp.list");
    System.out.println(list.size());

    if (list != null && !list.isEmpty()) {
      ar = new EmpVO[list.size()];
      list.toArray(ar);
    }
    return ar;
  }
}
