package com.example.kakaologin_0217;

import mybatis.vo.MemberVO;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class LoginControl {

  @Autowired
  private HttpSession session;

  @RequestMapping("/login")
  public String login() {
    return "login";
  }

  @RequestMapping("/login/kakao")
  public ModelAndView kakaoLogin(String code) {
    ModelAndView mv = new ModelAndView();

    // 카카오서버가 인자로 전달해 준 "인증코드"가 code라는 변수로 넣어준다.
    //System.out.println("Code: " + code);

    // 받은 인증코드를 가지고 2번째 카카오서버와 통신을 하기 위해
    // 토큰을 요청하여 받아야 한다.

    String access_token = "";
    String refresh_token = "";

    String req_url = "https://kauth.kakao.com/oauth/token";

    try {
      // 웹 상의 경로를 객체화 시킨다.
      URL url = new URL(req_url);

      // 웹 상의 경로와 연결하는 객체
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();

      // 카카오서버쪽에서 POST방식의 요청을 원하므로 method를 Post로 지정한다.
      conn.setRequestMethod("POST");
      conn.setDoOutput(true);

      // 연결된 카카오서버로 파라미터들을 전달하기 위해 스트림 생성
      BufferedWriter bw = new BufferedWriter(
          new OutputStreamWriter(conn.getOutputStream())
      );

      // 카카오 API 문서에 있는 4개의 파라미터들을 정의하기 위해
      // 문자열 편집이 필요하다.
      // 예) grant_type = authorization_code&client_id=...
      StringBuffer sb = new StringBuffer();
      sb.append("grant_type=authorization_code");
      sb.append("&client_id=5e0747d589cb2925c8fe1e4cef2f3722");
      sb.append("&redirect_uri=http://localhost:8080/login/kakao");
      sb.append("&code=").append(code);

      bw.write(sb.toString()); // 준비된 파라미터들을 카카오서버에 보낸다.
      bw.flush();

      // 카카오서버에 요청을 보낸 수 응답결과가 성공적인 경우(200)에만
      // 토큰들을 받아내야 한다.
      int res_code = conn.getResponseCode();
      //System.out.println("RES_CODE:" + res_code);

      if (res_code == 200) {
        // 요청을 통해 얻은 JSON타입의 결과메세지를 읽어온다.
        // 그 결과를 받기 위해 스트림 준비
        BufferedReader br = new BufferedReader(
            new InputStreamReader(conn.getInputStream())
        );
        StringBuffer result = new StringBuffer();
        String line = null;
        // 한줄 단위로 읽어서 result라는 StringBuffer에 적재한다.
        while ((line = br.readLine()) != null) {
          result.append(line);
        } // while 문의 끝
        //System.out.println("RESULT:" + result.toString());

        // JSON 파싱 처리 : "access_token과
        // "refresh_token" 을 찾아내어 값을 얻어내야 한다.

        JSONParser parser = new JSONParser();
        // 위 객체는 json-simple 라이브러리가 가지고 있으며
        // 문자열이지만 JSON표현식으로 된 것을 JSON객체화 시키는 라이브러리다.

        //Object obj = parser.parse(result.toString());
        //JSONObject json = (JSONObject) obj;
        JSONObject json = (JSONObject) parser.parse(result.toString());

        access_token = (String) json.get("access_token");
        refresh_token = (String) json.get("refresh_token");
        //System.out.println("access_token = " + access_token);
        //System.out.println("refresh_token = " + refresh_token);

        // 이제 받은 토큰을 가지고 마지막 3번째 호출인
        // 사용자 정보를 요청해야 한다.
        String apiURL = "https://kapi.kakao.com/v2/user/me";
        String header = "Bearer " + access_token;

        // 자바에서 특정 웹상의 경로(URL)를 호출하기 위해서는
        // 먼저 URL객체를 생성한다.
        URL url2 = new URL(apiURL);
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestMethod("POST");
        conn2.setDoOutput(true);

        // 카카오 API 의 문서상의 조건이 access토큰을 header에 담아 보내라고
        // 명시되어 있으니 헤더설정을 하자.
        conn2.setRequestProperty("Authorization", header);
        res_code = conn2.getResponseCode();
        //System.out.println("RES_CODE: " + res_code);
        //System.out.println("RES_CODE: " + HttpURLConnection.HTTP_OK);

        if (res_code == HttpURLConnection.HTTP_OK) {
          // 요청에 성공했다면 ...
          BufferedReader brdm = new BufferedReader(
              new InputStreamReader(conn2.getInputStream())
          );
          StringBuffer res = new StringBuffer();
          String line2 = null;

          while ((line2 = brdm.readLine()) != null) {
            res.append(line2);
          }

          System.out.println("RES:" + res.toString());

          // 받은 JSON표현의 문자열을 JSON객체로 변환 후
          // 원하는 정보(nickname, profile_image)를 얻어낸다.
          // 앞서 이미 파서객체(JSONParser)가 생성된 상태다.
          json = (JSONObject) parser.parse(res.toString());

          // 원하는 정보 nickname과 profile_image가 있는 "properties" 라는
          // 키의 값을 얻어낸다.
          // ----- 사용자 정보 -----
          JSONObject props = (JSONObject) json.get("properties");
          String nickName = (String) props.get("nickname");
          String p_img = (String) props.get("profile_image");
          // --------------------

          MemberVO mvo = new MemberVO();
          mvo.setNickname(nickName);
          mvo.setP_img(p_img);

          // 이렇게 카카오에서 받은 정보를 mvo에 저장한 후
          // ModelAndView에 저장하여 추가 정보를 받을 수 있는
          // registry.jsp에서 표현할 수 있도록 한다.
          mv.addObject("mvo", mvo);
          mv.setViewName("registry");
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    return mv;
  }
}