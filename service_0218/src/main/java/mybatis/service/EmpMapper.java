package mybatis.service;

import mybatis.vo.EmpVO;

public interface EmpMapper {

  EmpVO[] allEmp() throws Exception;
  EmpVO selectEmpById(String empno) throws Exception;
  EmpVO[] searchEmp(int type, String value) throws Exception;
}