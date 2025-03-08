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

    //총 게시물 수를 반환
    public int getTotalCount(String bname){

        int cnt = ss.selectOne("bbs.totalCount", bname);

        return cnt;
    }

    //목록
    public BbsVO[] getList(String bname, int begin, int end){
        BbsVO[] ar = null;
        HashMap<String, Object> map = new HashMap<>();
        map.put("bname", bname);
        map.put("begin", begin); //String.valueOf(begin)
        map.put("end", end);

        List<BbsVO> list = ss.selectList("bbs.list", map);
        if(list != null && !list.isEmpty()){ //list != null && list.size() > 0
            ar = new BbsVO[list.size()];
            list.toArray(ar);
        }

        return ar;
    }

    //저장
    public int add(String title, String writer, String content, String fname,
                   String oname, String pwd, String ip, String bname){
        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("writer", writer);
        map.put("content", content);
        map.put("fname", fname);
        map.put("oname", oname);
        map.put("pwd", pwd);
        map.put("ip", ip);
        map.put("bname", bname);

        int cnt = ss.insert("bbs.add", map);

        if(cnt > 0)
            ss.commit();
        else
            ss.rollback();

        return cnt;
    }

    public BbsVO getBbs(String b_idx){

        BbsVO vo = ss.selectOne("bbs.get_bbs", b_idx);


        return vo;
    }

    //조회수 증가
    public int hit(String b_idx){

        int cnt = ss.update("bbs.hit", b_idx);

        if(cnt > 0)
            ss.commit();
        else
            ss.rollback();

        return cnt;
    }

    //원글 수정
    //게시물 수정
    public int edit(String b_idx, String title, String content, String fname, String oname, String ip, String pwd){
        HashMap<String, String> map = new HashMap<>();
        map.put("b_idx", b_idx);
        map.put("title", title);
        map.put("content", content);
        if(fname != null){
            map.put("fname", fname);
            map.put("oname", oname);
        }
        map.put("ip", ip);
        map.put("pwd", pwd);


        int cnt = ss.update("bbs.edit", map);

        if(cnt > 0)
            ss.commit();
        else
            ss.rollback();

        return cnt;
    }

    //댓글 저장
    public int addComm(String writer, String comm, String pwd, String b_idx, String ip){
        CommVO cvo = new CommVO();
        cvo.setWriter(writer);
        cvo.setContent(comm);
        cvo.setPwd(pwd);
        cvo.setB_idx(b_idx);
        cvo.setIp(ip);


        int cnt = ss.insert("bbs.addComm", cvo);

        if(cnt > 0)
            ss.commit();
        else
            ss.rollback();

        return cnt;
    }

    //원글 삭제
    public int del(String b_idx, String pwd){
        Map<String, String> map = new HashMap<>();
        map.put("b_idx", b_idx);
        map.put("pwd", pwd);


        int cnt = ss.update("bbs.del", map);

        if (cnt > 0)
            ss.commit();
        else
            ss.rollback();

        return cnt;
    }
}