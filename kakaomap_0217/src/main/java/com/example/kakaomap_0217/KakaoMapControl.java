package com.example.kakaomap_0217;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class KakaoMapControl {

  @RequestMapping("/map")
  public String map(){
    return "map";
  }
}