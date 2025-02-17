<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f5f5f5;
        }

        .login-container {
            background: #fff;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
            width: 300px;
        }

        .login-container h1 {
            margin-bottom: 20px;
            font-size: 24px;
        }

        .login-container input {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .login-container button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
        }

        .login-container button:hover {
            background-color: #0056b3;
        }

        .login-container img {
            width: 100%;
            margin-top: 15px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<%-- 세션에 "mvo" 값이 없을 때만 로그인 화면을 표시 --%>
<c:if test="${sessionScope.mvo eq null}">
    <div class="login-container">
        <h1>Login</h1>
        <form action="" method="post">
            <input type="text" name="m_id" placeholder="아이디 입력" />
            <input type="password" name="m_pw" placeholder="비밀번호 입력" />
            <button type="button">LOGIN</button>
        </form>
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=5e0747d589cb2925c8fe1e4cef2f3722&redirect_uri=http://localhost:8080/login/kakao&response_type=code">
            <img src="resources/images/kakao_login_large_narrow.png" alt="카카오 로그인">
        </a>
    </div>
</c:if>
</body>
</html>
