package com.example.jdom_0214;

import data.vo.MemberVO;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberControl {
  /*
    오픈 API서비스의 URL같은 경로가 멤버변수로 선언되어야 하지만
    내부에 있는 XML문서를 접근하여 마치 오픈API에서 결과를 받은것 처럼 가정한다.
   */
  @Autowired
  private ServletContext application;

  @RequestMapping("test")
  public ModelAndView test() throws Exception {
    ModelAndView mv = new ModelAndView();

    // 준비된 문서의 절대경로를 지정한다.
    String realPath = application.getRealPath("/resources/pm/data/member.xml");

    // XML문서를 읽기하기 위해 필요한 객체
    SAXBuilder builder = new SAXBuilder();

    // 준비된 builder를 통해 결과인 xml자원을 문서화(Document)시킨다.
    Document doc = builder.build(realPath);

    // 메모리상에 존재하는 xml문서의 루트를 얻어낸다.
    Element root = doc.getRootElement(); // members
    System.out.println("Root:"+root.getName());

    // 루트의 자식들 중 태그명이 member인 자식들 모두 가져온다.
    List<Element> list = root.getChildren("member");

    // 위에서 얻은 list를 배열로 만들고자 한다.
    System.out.println("list.size():"+list.size());

    // 각각의 Element를 MemberVO로 만든다.
    MemberVO[] ar = null;
    if (list != null && list.size() > 0) {
      ar = new MemberVO[list.size()];
      for (int i = 0; i < list.size(); i++) {
        Element element = list.get(i); // 리스트에 저장된 Element를 하나씩 가져온다.
        String name = element.getChildText("name");
        String phone = element.getChildText("phone");
        String email = element.getChildText("email");

        // 배열에 저장할 MemberVO를 생성
        MemberVO memberVO = new MemberVO();
        memberVO.setName(name);
        memberVO.setPhone(phone);
        memberVO.setEmail(email);
        ar[i] = memberVO;
      }
    }
    mv.addObject("ar", ar);
    mv.setViewName("member");

    return mv;
  }

  @RequestMapping(value = "search", method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> search(String searchType, String searchValue) throws Exception {
    Map<String, Object> map = new HashMap<>();

    // XML 파일 경로 설정
    String realPath = application.getRealPath("/resources/pm/data/member.xml");

    // XML 문서 읽기
    SAXBuilder builder = new SAXBuilder();
    Document doc = builder.build(realPath);

    // 루트 요소 가져온다.
    Element root = doc.getRootElement();
    List<Element> list = root.getChildren("member");

    // 각각의 Element를 MemberVO로 만든다.
    MemberVO[] ar = null;
    if (list != null && list.size() > 0) {
      ar = new MemberVO[list.size()];
      for (int i = 0; i < list.size(); i++) {
        Element element = list.get(i); // 리스트에 저장된 Element를 하나씩 가져온다.
        String name = element.getChildText("name");
        String phone = element.getChildText("phone");
        String email = element.getChildText("email");

        // 배열에 저장할 MemberVO를 생성
        MemberVO memberVO = new MemberVO();
        memberVO.setName(name);
        memberVO.setPhone(phone);
        memberVO.setEmail(email);

        boolean chk = false;

        if ("0".equals(searchType) && name.contains(searchValue)) {
          chk = true;
        } else if ("2".equals(searchType) && email.contains(searchValue)) {
          chk = true;
        } else if ("1".equals(searchType) && phone.contains(searchValue)) {
          chk = true;
        }

        if (chk) {
          ar[i] = memberVO;
        }
      }
    }
    map.put("ar", ar);
    map.put("count", ar.length);
    return map;
  }
}