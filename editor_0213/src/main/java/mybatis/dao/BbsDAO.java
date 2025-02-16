package mybatis.dao;

import mybatis.vo.BbsVO;
import mybatis.vo.CommVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BbsDAO {

  @Autowired
  private SqlSessionTemplate ss;

  // 총 게시물 수를 반환
  public int getTotalCount(String bname) {

    int cnt = ss.selectOne("bbs.totalCount", bname);

    return cnt;
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

  // 글쓰기
  public int add(BbsVO vo) {

    int cnt = ss.insert("bbs.add", vo);

    return cnt;
  }

  // 특정 게시물 검색
  public BbsVO getBbs(String b_idx) {

    BbsVO vo = ss.selectOne("bbs.get_bbs", b_idx);

    return vo;
  }

  // 조회수 증가
  public int hit(String b_idx) {

    int cnt = ss.update("bbs.hit", b_idx);

    return cnt;
  }

  // 게시물 수정
  public int update(String b_idx, String title, String content, String pwd, String fname, String oname, String ip) {

    Map<String, String> map = new HashMap<String, String>();
    map.put("b_idx", b_idx);
    map.put("title", title);
    map.put("content", content);
    map.put("pwd", pwd);
    map.put("fname", fname);
    map.put("oname", oname);
    map.put("ip", ip);

    int cnt = ss.update("bbs.update", map);

    return cnt;
  }

  // 댓글 저장
  public int addComm(String writer, String content, String pwd, String ip, String b_idx) {

    CommVO cvo = new CommVO();
    cvo.setWriter(writer);
    cvo.setContent(content);
    cvo.setPwd(pwd);
    cvo.setIp(ip);
    cvo.setB_idx(b_idx);

    int cnt = ss.insert("bbs.addcomm", cvo);

    return cnt;
  }

  // 게시글 삭제
  public int del(String b_idx, String pwd) {

    Map<String, String> map = new HashMap<String, String>();
    map.put("b_idx", b_idx);
    map.put("pwd", pwd);

    int cnt = ss.update("bbs.del", map);

    return cnt;
  }
}
