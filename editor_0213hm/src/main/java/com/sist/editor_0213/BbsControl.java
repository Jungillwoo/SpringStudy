package com.sist.editor_0213;

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
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
public class BbsControl {
    // 내장객체이미로 Autowired 사용 가능

    @Autowired
    private BbsDAO bbsDAO;

    // servletcontext가 있어야 절대 경로를 얻을 수 있다
    @Autowired
    private HttpServletRequest request;

    // 절대경로를 얻기 위함
    @Autowired
    private ServletContext application;

    int numPerPage = 7; // 한 페이지에 보여질 게시물 수
    int pagePerBlock = 5;   // 한 블럭에 보여질 페이지 수
    @RequestMapping("/list")
    public ModelAndView list(String bname, String cPage) {
        // 페이징 처리 -----------------
        if(bname==null||bname.equals(""))
            bname="BBS";
        Paging page = new Paging(numPerPage, pagePerBlock);
        int totalCount = bbsDAO.getTotalCount(bname);
        page.setTotalRecord(totalCount);
        if(cPage==null)
            cPage="1";
        page.setNowPage(Integer.parseInt(cPage));

        ModelAndView mv = new ModelAndView();

        // 뷰페이지에서 표현할 자원
        BbsVO[] ar = bbsDAO.getList("BBS", page.getBegin(), page.getEnd());
        mv.addObject("ar", ar);
        mv.addObject("page", page);
        mv.setViewName("list");
        return mv;
    }

    @RequestMapping("/write")
    public String write() {
        return "write";
    }

    @RequestMapping(value = "saveImg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> saveImg(ImgVO iVO) {
        // 반환객체인 Map 구조를 생성
        Map<String, Object> map = new HashMap<String, Object>();

        // 전달되어 오는 이미지는 ivo에 저장되어 있다.
        MultipartFile f = iVO.getUpload();
        if(f.getSize() > 0){
            // 파일이 있는 경우
            // 파일을 저장할 위치를 절대경로로 만들자!
            String realPath = application.getRealPath(
                    "/resources/upload_img");

            // 저장될 위치를 알아냈으니 파일을 저장하자!
            try {
                // 파일 올리기
                f.transferTo(new File(realPath, f.getOriginalFilename()));
                map.put("fname", f.getOriginalFilename());
            } catch(Exception e) {
                e.printStackTrace();
            }

            // 현재 파일이 저장된 서버경로(url)를 문자열로 만들자!
            // 예) localhost:8080/upload_img
            String c_path = request.getContextPath(); //localhost:8080/
            map.put("url", c_path + "/resources/upload_img");
        }

        return map; // 요청한 곳으로 보내진다. 이때
        // JSON으로 보내기 위해 현재 메서드 위에 @ResponseBody를 지정했다.
        // { "fname": "test.gif", "url": "localhost:8080/resources/upload_img"}
    }
}
