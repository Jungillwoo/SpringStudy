<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-01-03
  Time: 오후 2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
  <link rel="stylesheet" href="resources/css/summernote-lite.css"/>
  <style type="text/css">
    #bbs table {
      width:580px;
      margin-left:10px;
      border:1px solid black;
      border-collapse:collapse;
      font-size:14px;

    }

    #bbs table caption {
      font-size:20px;
      font-weight:bold;
      margin-bottom:10px;
    }

    #bbs table th {
      text-align:center;
      border:1px solid black;
      padding:4px 10px;
    }

    #bbs table td {
      text-align:left;
      border:1px solid black;
      padding:4px 10px;
    }

    .no {width:15%}
    .subject {width:30%}
    .writer {width:20%}
    .reg {width:20%}
    .hit {width:15%}
    .title{background:lightsteelblue}

    .odd {background:silver}


  </style>
  <script type="text/javascript">

  </script>
</head>
<body>
<div id="bbs">
  <form action="write" method="post"
        encType="multipart/form-data">
    <table summary="게시판 글쓰기">
      <caption>게시판 글쓰기</caption>
      <tbody>
      <tr>
        <th>제목:</th>
        <td><input type="text" id="title" name="title" size="45"/></td>
      </tr>
      <tr>
        <th>이름:</th>
        <td><input type="text" id="writer" name="writer" size="12"/></td>
      </tr>
      <tr>
        <th>내용:</th>
        <td><textarea id="content" name="content" cols="50"
                      rows="8"></textarea></td>
      </tr>
      <tr>
        <th>첨부파일:</th>
        <td><input type="file" name="file"/></td>
      </tr>
      <tr>
        <th>비밀번호:</th>
        <td><input type="password" id="pwd" name="pwd" size="12"/></td>
      </tr>

      <tr>
        <td colspan="2">
          <input type="button" value="보내기"
                 onclick="sendData()"/>
          <input type="button" value="다시"/>
          <input type="button" value="목록"/>
        </td>
      </tr>
      </tbody>
    </table>
  </form>
</div>

<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="resources/js/summernote-lite.js"></script>
<script src="resources/js/lang/summernote-ko-KR.js"></script>
<script>
  $(function () {
    $("#content").summernote({
      lang: "ko-KR",
      callbacks:{
        onImageUpload: function (files, editor){
          //이미지가 에디터에 추가될 때마다 수행하는 곳
          //이미지를 첨부하면 배열로 인식된다.
          for(let i=0; i<files.length; i++)
            saveImg(files[i], editor);
        }
      }
    });
  });

  function saveImg(file, editor){
    //서버로 이미지를 보내기 위해 폼객체 준비
    let frm = new FormData();

    //서버로 파일을 보내기 위해 파라미터를 지정
    frm.append("upload", file) //frm.append("파라미터명", )

    //비동기식 통신
    $.ajax({
      url: "saveImg",
      data: frm,
      type: "post",
      contentType: false,
      processData: false, //2개를 false로 설정한 이유 : 첨부파일을 보내는 것이고, 일반적인 데이터 전송이 아니라는 것을 지정한 것이다.
      dataType: "json" //서버로부터 받는 자원의 형태
    }).done(function (res) {
      //서버에서 보내는 json데이터는 res가 되고, 그 res안에 img_url을 가지고
      //img요소를 에디터에 추가한다.
      $("#content").summernote("editor.insertImage", res.url+"/"+res.fname);
    });
  }

  function sendData(){

    let title = $("#title").val();
    let writer = $("#writer").val();
    let content = $("#content").val();
    let pwd = $("#pwd").val();

    if(title.trim().length < 1){
      alert("제목을 입력하세요");
      $("#title").val("");
      $("#title").focus();
      return;
    }
    if(writer.trim().length < 1){
      alert("이름을 입력하세요");
      $("#writer").val("");
      $("#writer").focus();
      return;
    }
    if(content.trim().length < 1){
      alert("내용을 입력하세요");
      $("#content").val("");
      $("#content").focus();
      return;
    }
    if(pwd.trim().length < 1){
      alert("비밀번호를 입력하세요");
      $("#pwd").val("");
      $("#pwd").focus();
      return;
    }

//     for(var i=0 ; i<document.forms[0].elements.length ; i++){
//       if(document.forms[0].elements[i].value == ""){
//         alert(document.forms[0].elements[i].name+
//                 "를 입력하세요");
//         document.forms[0].elements[i].focus();
//         return;//수행 중단
//       }
//     }
//
// //		document.forms[0].action = "test.jsp";
    document.forms[0].submit();
  }
</script>
</body>
</html>
