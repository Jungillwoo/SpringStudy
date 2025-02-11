<%@ page import="org.springframework.beans.factory.BeanFactory" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext" %>
<%@ page import="com.example.demo.vo.TestVO" %>
<%@ page import="com.example.demo.vo.Test2VO" %>
<%@ page import="com.example.demo.vo.Test3VO" %>
<%@ page import="com.example.demo.vo.Test4VO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    // 준비된 환경설정 파일(config.xml)을 로드한다.
    BeanFactory bf = new ClassPathXmlApplicationContext("config.xml");
    // 이때 config.xml에 정의된 모든 bean들이 생성된다.

    // 사용자가 원하는 bean객체를 얻어내자(id가 t1인 객체)
    TestVO tt = (TestVO) bf.getBean("t1");

    Test2VO tt2 = (Test2VO) bf.getBean("t2");

    Test3VO tt3 = (Test3VO) bf.getBean("t3");

    // 클래스의 유형(자료형)을 가지고 빈객체를 검색한다.
    Test4VO tt4 = bf.getBean(Test4VO.class);
%>

    <h1><%=tt.getMsg()%></h1>
    <h1><%=tt2.getStr()%>/<%=tt2.getValue()%></h1>
    <h1><%=tt3.getName()%>/<%=tt3.getAge()%>/<%=tt3.isLive()%></h1>
    <h1><%=tt4.getTest().getStr()%>/<%=tt4.getTest().getValue()%></h1>
</body>
</html>