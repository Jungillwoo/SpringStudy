package pm.action;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BbsControl {

  @Autowired
  private BbsDAO bbsDAO;

  @RequestMapping("/bbs/list")
  public ModelAndView bbsList(){
    ModelAndView mv = new ModelAndView();

    // mybatis로부터 DB에 있는 자원을 가져온다.
    BbsVO[] ar = bbsDAO.getList("BBS", 1, 10);

    mv.addObject("bbs_ar", ar);
    mv.setViewName("bbs/list");
    return mv;
  }
}