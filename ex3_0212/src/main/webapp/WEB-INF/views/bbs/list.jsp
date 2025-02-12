<%--
  Created by IntelliJ IDEA.
  User: jeong-il-u
  Date: 2025. 2. 12.
  Time: 오후 2:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Title</title>
</head>
<body>
    <h2>게시물 목록</h2>
    <hr/>
    <ul>
        <c:forEach var="vo" items="${bbs_ar}" varStatus="vs">
            <li>${vo.subject}/${vo.write_date}</li>
        </c:forEach>
    </ul>
</body>
</html>