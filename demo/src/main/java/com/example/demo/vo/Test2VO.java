package com.example.demo.vo;

public class Test2VO {

  private String str;
  private int value;

  public Test2VO() { // 생성자
    System.out.println("Test2VO생성자");
  }

  public String getStr() {
    return str;
  }

  public void setStr(String str) {
    this.str = str;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}
