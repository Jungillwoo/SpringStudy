<%--
  Created by IntelliJ IDEA.
  User: jeong-il-u
  Date: 2025. 2. 12.
  Time: 오후 4:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Title</title>
    <style>
        /* 전체 페이지 스타일 */
        body {
            font-family: Arial, sans-serif;
            text-align: center; /* h2 및 테이블 가운데 정렬 */
        }

        /* 제목 중앙 정렬 */
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }

        /* 테이블 스타일 */
        table {
            width: 80%;
            margin: 0 auto; /* 가운데 정렬 */
            border-collapse: collapse; /* 테두리 겹침 */
        }

        /* 테이블 헤더 스타일 */
        thead {
            background-color: #4CAF50;
            color: white;
        }

        /* 테이블 테두리 및 셀 스타일 */
        th, td {
            border: 1px solid black;
            padding: 10px;
            text-align: center;
        }

        /* 짝수 행 배경색 추가 */
        tbody tr:nth-child(even) {
            background-color: #f2f2f2;
        }

        /* 테이블 호버 효과 */
        tbody tr:hover {
            background-color: #ddd;
        }
    </style>
</head>
<body>
<h2>emp table</h2>
<hr/>
<table id="empTable" style="border: 1px solid black">
    <thead>
    <tr>
            <th>empno</th>
            <th>ename</th>
            <th>job</th>
            <th>mgr</th>
            <th>hiredate</th>
            <th>sal</th>
            <th>comm</th>
            <th>deptno</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="vo" items="${ar}">
        <tr>
            <td>${vo.empno}</td>
            <td>${vo.ename}</td>
            <td>${vo.job}</td>
            <td>${vo.mgr}</td>
            <td>${vo.hiredate}</td>
            <td>${vo.sal}</td>
            <td>${vo.comm}</td>
            <td>${vo.deptno}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
