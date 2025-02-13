package com.example.editor_0213;

import bbs.util.Paging;
import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import spring.input.ImgVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BbsControl {

  @Autowired
  private BbsDAO bbsDAO;

  @Autowired
  private HttpServletRequest request;

  @Autowired
  private ServletContext application;

  @Autowired
  private HttpSession session;

  int numPerPage = 7;
  int pagePerBlock = 5;

  private String uploadPath = "/resources/upload/";

  @RequestMapping("/list")
  public ModelAndView list(String bname, String cPage) {

    // 페이징 처리----------------------------
    if (bname == null || bname.equals(""))
      bname = "BBS";

    Paging page = new Paging(numPerPage, pagePerBlock);
    int totalCount = bbsDAO.getTotalCount(bname);
    page.setTotalRecord(totalCount);
    if (cPage == null)
      cPage = "1";
    page.setNowPage(Integer.parseInt(cPage));

    ModelAndView mv = new ModelAndView();

    // 뷰페이지에서 표현할 자원
    BbsVO[] ar = bbsDAO.getList(bname, page.getBegin(), page.getEnd());
    mv.addObject("ar", ar);
    mv.addObject("page", page);
    mv.setViewName("list");
    return mv;
  }

  @RequestMapping("view")
  public ModelAndView view(String b_idx, String bname, String cPage) {

    ModelAndView mv = new ModelAndView();

    List<BbsVO> list = null;
    // 세션으로부터 이름이 r_list라는 이름으로 저장된 객체를 얻어낸다.
    Object obj = session.getAttribute("r_list");

    if (obj != null) {
      list = (List<BbsVO>) obj;
    } else {
      list = new ArrayList<BbsVO>();
      session.setAttribute("r_list", list);
    }

    // 이제 list에서 인자로 받은 b_idx갑과 같은 값을 가진 BbsVO를 list에서 검색
    boolean chk = false;
    for (BbsVO bvo : list) {
      if (bvo.getB_idx().equals(b_idx)) {
        chk = true;
        break;
      }
    } // for의 끝
    if (!chk) { // chk가 false를 유지하고 있을 때
      // 한번도 읽지 않은 게시물인 경우 hit 증가
      bbsDAO.hit(b_idx);
    }

    BbsVO vo = bbsDAO.getBbs(b_idx);

    if (vo != null) {
      list.add(vo);
    }

    mv.addObject("vo", vo);
    mv.addObject("cPage", cPage);
    mv.setViewName("view");
    return mv;
  }

  @RequestMapping("/write")
  public String write() {
    return "write";
  }

  @RequestMapping(value = "saveImg", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> saveImg(ImgVO ivo) {
    // 반환객체인 Map구조를 생성
    Map<String, Object> map = new HashMap<String, Object>();

    // 전달되어 오는 이미지는 ivo에 저장되어 있다.
    MultipartFile f = ivo.getUpload();
    if (f.getSize() > 0) {
      // 파일이 있는 경우
      // 파일을 저장할 위치를 절대경로로 만들자
      String realPath = application.getRealPath("/resources/upload_img");

      // 저장될 위치를 알아냈으니 파일을 저장하자.
      try {
        // 파일올리기
        f.transferTo(new File(realPath, f.getOriginalFilename()));
        map.put("fname", f.getOriginalFilename());
      } catch (Exception e) {
        e.printStackTrace();
      }
      // 현재 파일이 저장된 서버경로(url)를 문자열로 만들자.
      // 예) localhost:8080/resources/upload_img
      String c_path = request.getContextPath(); // localhost:8080
      map.put("url", c_path+"/resources/upload_img");

//      String serverUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
//      map.put("url", serverUrl + "/resources/upload_img");
    }

    return map; // 요청한 곳으로 보내진다. 이때
    // JSON으로 보내기 위해 현제 메서드 위에 @ResponseBody를 지정한다.
    // { "fname" : test.gif", "url" : "localhost:8080/resources/upload_img" }
  }

  @RequestMapping(value = "/write", method = RequestMethod.POST)
  public ModelAndView write(BbsVO vo) {
    ModelAndView mv = new ModelAndView();

    // 폼에 encType이 multipart로 시작하는지 확인하고 싶다면
    String c_type = request.getContentType();
    if (c_type.startsWith("multipart")){
      // 파일이 첨부되는 폼에서 호출된 경우
      MultipartFile f = vo.getFile();
      String fname = null;
      if (f != null && f.getSize() > 0) {
        // 파일이 첨부된 경우 - 파일이 저장될 위치를 절대경로화 시킨다.
        String realPath = application.getRealPath(uploadPath);
        fname = f.getOriginalFilename();

        try {
          // 파일 업로드(upload폴더에 지정)
          f.transferTo(new File(realPath, fname));
          vo.setFile_name(fname);

        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      vo.setIp(request.getRemoteAddr()); // ip저장

      // vo를 DAO에게 전달해서 DB에 저장하도록 한다.
      bbsDAO.add(vo);
    }
    // list화면으로 이동하기 위해 다시 list라는 RequestMapping을 호출
    // redirect로 호출해야함
    mv.setViewName("redirect:/list?bname=" + vo.getBname());
    return mv;
  }

//  @RequestMapping(value = "/write_ok", method = RequestMethod.POST)
//  public String writeOk(String title, String writer, String content,
//                        MultipartFile file, String pwd) {
//
//    String ip = request.getRemoteAddr(); // 클라이언트 IP 가져오기
//    String bname = "BBS"; // 기본 게시판 이름 설정
//
//    String fname = null;
//    String oname = null;
//
//    // 파일이 존재하면 저장
//    if (file != null && !file.isEmpty()) {
//      oname = file.getOriginalFilename();
//      String realPath = application.getRealPath("/resources/upload");
//
//      File saveFile = new File(realPath, oname);
//      try {
//        file.transferTo(saveFile);
//        fname = oname; // 파일 저장 후 이름 설정
//      } catch (Exception e) {
//        e.printStackTrace();
//      }
//    }
//
//    // DB에 저장
//    int res = bbsDAO.add(title, writer, content, fname, oname, pwd, ip, bname);
//
//    if (res > 0) {
//      return "redirect:/list";
//    } else {
//      return "error"; // 실패 시 에러 페이지
//    }
//  }
}