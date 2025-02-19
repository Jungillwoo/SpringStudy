package com.example.openapi_0218;

import data.vo.DataVO;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Controller
public class IndexControl {

  @RequestMapping("/") // 시작하자마자 실행
  public String index(Model model) {
    // 공공데이터의 자원을 받기 위해 URL을 생성
    StringBuffer sb = new StringBuffer("http://apis.data.go.kr/B551011/KorService1/searchFestival1?");
    // 내 key값
    sb.append("serviceKey=DX7Ku0Wanoi%2B3C0RoK%2FSlFaXAtlmK0lsHDcdm81X6fd3%2F6K7NCUhOZfW2tY1ldPu1CHFq%2BHQYqekPPtersVubQ%3D%3D");
    sb.append("&numOfRows=10");
    sb.append("&pageNo=1");
    sb.append("&MobileOS=ETC");
    sb.append("&MobileApp=AppTest");
    sb.append("&arrange=A");
    sb.append("&listYN=Y");
    sb.append("&eventStartDate=20170901");
    sb.append("&areaCode=1");

    try {
      // 자바에서 외부의 서버호출 시 URL 객체 생성
      URL url = new URL(sb.toString());

      // 위에서 만든 웹상 경로(URL)를 호출하기 위한 연결
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      // 응답 받을 데이터의 형식을 지정
      conn.setRequestProperty("Content-Type", "application/xml");

      // 요청-(연결)
      conn.connect();

      // JDOM 라이브러리를 통해 xml문자열을 DOM객체로 변환한다.
      SAXBuilder builder = new SAXBuilder();

      // 응답된 내용을 하나의 xml 문서(Doucument)로 인식(변환)한다.
      Document doc = builder.build(conn.getInputStream());

      // 루트 엘리먼트를 얻어내자
      Element root = doc.getRootElement();

      //System.out.println(root.getName());     // response 나와야함

      // 루트 안에 있는 body만 얻어내자
      Element body = root.getChild("body");

      // body 안에 있는 items라는 요소를 얻는다.
      Element items = body.getChild("items");

      // items 안에 있는 item요소들을 얻어낸다.
      List<Element> itemList = items.getChildren("item");

      //System.out.println(itemList.size());

      // item들은 요소다. 이것을 JSP에서 표현할 수 있는 의미가 부여된 vo로 변환하자
      // 배열을 만들자(List의 크기와 같아야 한다.)
      DataVO[] ar = new DataVO[itemList.size()];

      int i = 0;
      for (Element item : itemList) {
        // 하나의 item요소에서 원하는 값들을 얻어낸다.(title, addr1, addr2, ...)
        String title, mapx, mapy, addr1, addr2, firstimage, firstimage2, tel, eventstartdate, eventenddate;
        title = item.getChildText("title");
        mapx = item.getChildText("mapx");
        mapy = item.getChildText("mapy");
        addr1 = item.getChildText("addr1");
        addr2 = item.getChildText("addr2");
        firstimage = item.getChildText("firstimage");
        firstimage2 = item.getChildText("firstimage2");
        tel = item.getChildText("tel");
        eventstartdate = item.getChildText("eventstartdate");
        eventenddate = item.getChildText("eventenddate");

        // vo객체로 생성
        DataVO vo = new DataVO(title, mapx, mapy, addr1, addr2, firstimage,firstimage2, tel, eventstartdate, eventenddate);

        // 생성된 vo객체를 배열에 추가
        ar[i++] = vo;
        //++i;
      } // for끝

      // 배열을 jsp에서 표현할 수 있도록 모델에 저장한다.
      model.addAttribute("ar", ar);

    } catch (Exception e) {
      e.printStackTrace();
    }

    return "index";
  }
}
