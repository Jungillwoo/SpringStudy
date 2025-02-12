package mybatis.dao;

import mybatis.vo.BbsVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class BbsDAO {

  @Autowired
  private SqlSessionTemplate ss; // 리스너에 의해 움직이는
  // root-context.xml에서 ss를 생성하여 자동으로 멤버변수 ss에 저장되도록 한다.

  public BbsDAO() {
    System.out.println("BbsDAO생성");
  }

  // 목록
  public BbsVO[] getList(String bname, int begin, int end) {

    BbsVO[] ar = null;

    HashMap<String, String> map = new HashMap<String, String>();
    map.put("bname", bname);
    map.put("begin", String.valueOf(begin)); // String.valueof(begin)
    map.put("end", String.valueOf(end));

    List<BbsVO> list = ss.selectList("bbs.list", map);

    if (list != null && !list.isEmpty()) {
      ar = new BbsVO[list.size()];
      list.toArray(ar);
    }

    return ar;
  }
}
