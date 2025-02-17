<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>추가정보 입력</title>
    <style>
        /* 전체 스타일 */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        /* 컨테이너 스타일 */
        .container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 350px;
            text-align: center;
        }

        header h2 {
            color: #333;
            margin-bottom: 20px;
        }

        img {
            border-radius: 50%;
            margin-bottom: 15px;
            border: 2px solid #ddd;
        }

        /* 입력 폼 스타일 */
        input {
            width: calc(100% - 20px);
            padding: 10px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 14px;
            box-sizing: border-box;
        }

        input[readonly] {
            background-color: #e9ecef;
            cursor: not-allowed;
        }

        button {
            width: 100%;
            padding: 12px;
            background-color: #4CAF50;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
            transition: background 0.3s ease;
        }

        button:hover {
            background-color: #45a049;
        }

        /* 반응형 스타일 */
        @media (max-width: 400px) {
            .container {
                width: 90%;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <header>
        <h2>추가정보 입력</h2>
    </header>
    <form action="/registry" method="post">
        <img src="${mvo.p_img}" width="100"/><br/>
        <input type="text" name="m_name" placeholder="이름 입력" /><br/>
        <input type="text" name="nickname" readonly value="${mvo.nickname}" /><br/>
        <input type="text" name="m_email" placeholder="이메일 입력" /><br/>
        <input type="text" name="m_phone" placeholder="연락처 입력" /><br/>
        <button type="submit">저장</button>
    </form>
</div>
</body>
</html>
