package mybatis.service;

import mybatis.dao.EmpDAO;
import mybatis.vo.EmpVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmpService implements EmpMapper {

  @Autowired
  private EmpDAO empDAO;

  @Override
  public EmpVO[] allEmp() throws Exception {
    return empDAO.getList();
  }

  @Override
  public EmpVO selectEmpById(String empno) throws Exception {
    return empDAO.getEmp(empno);
  }

  @Override
  public EmpVO[] searchEmp(int type, String value) throws Exception {

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("type", type);
    map.put("value", value);

    return empDAO.searchEmp(map);
  }
}