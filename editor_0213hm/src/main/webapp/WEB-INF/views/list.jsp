<%@ page import="mybatis.vo.BbsVO" %>
<%@ page import="bbs.util.Paging" %>
<%@ page import="java.util.List" %>
<%@ page import="mybatis.vo.CommVO" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2025-01-02
  Time: 오후 4:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
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

    #bbs table th,#bbs table td {
      text-align:center;
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

    /* paging */

    table tfoot ol.paging {
      list-style:none;
    }

    table tfoot ol.paging li {
      float:left;
      margin-right:8px;
    }

    table tfoot ol.paging li a {
      display:block;
      padding:3px 7px;
      border:1px solid #00B3DC;
      color:#2f313e;
      font-weight:bold;
    }

    table tfoot ol.paging li a:hover {
      background:#00B3DC;
      color:white;
      font-weight:bold;
    }

    .disable {
      padding:3px 7px;
      border:1px solid silver;
      color:silver;
    }

    .now {
      padding:3px 7px;
      border:1px solid #ff4aa5;
      background:#ff4aa5;
      color:white;
      font-weight:bold;
    }

  </style>
</head>
<body>
<div id="bbs">
  <table summary="게시판 목록">
    <caption>게시판 목록</caption>
    <thead>
    <tr class="title">
      <th class="no">번호</th>
      <th class="subject">제목</th>
      <th class="writer">글쓴이</th>
      <th class="reg">날짜</th>
      <th class="hit">조회수</th>
    </tr>
    </thead>

    <tfoot>
    <tr>
      <td colspan="4">
        <ol class="paging">

          <c:if test="${requestScope.page ne null}">
            <c:set var="pvo" value="${requestScope.page}"/>
            <c:if test="${pvo.startPage < pvo.pagePerBlock}">
              <li class="disable">&lt;</li>
            </c:if>
            <c:if test="${pvo.startPage >= pvo.pagePerBlock}">
              <li><a href="list?cPage=${pvo.startPage-pvo.pagePerBlock}">&lt;</a></li> <%-- startPage가 6 이상일때 --%>
            </c:if>
            <c:forEach begin="${pvo.startPage}" end="${pvo.endPage}" varStatus="st">
              <c:if test="${st.index eq pvo.nowPage}">
                <li class="now">${st.index}</li>
              </c:if>
              <c:if test="${st.index ne pvo.nowPage}">
                <li>
                  <a href="list?cPage=${st.index}">${st.index}</a>
                </li>
              </c:if>
            </c:forEach>
            <c:if test="${pvo.endPage < pvo.totalPage}">
              <li><a href="list?cPage=<${pvo.startPage+pvo.pagePerBlock}>&gt;</a></li>
            </c:if>
            <c:if test="${pvo.endPage >= pvo.totalPage}">
              <li class="disable">&gt;</li>
            </c:if>
          </c:if>
        </ol>
      </td>
      <td>
        <input type="button" value="글쓰기"
               onclick="javascript:location.href='write'"/> <%-- get방식 --%>
      </td>
    </tr>
    </tfoot>
    <tbody>
    <%--
        Object obj = request.getAttribute("ar");
        if(obj != null){
            BbsVO[] ar = (BbsVO[]) obj;
            int idx = 0;
            for(BbsVO vo : ar){ //jstl에서는 위의 if로 obj를 확인하지 않고 바로 for문을 돌려도 된다.
    --%>
    <c:forEach var="vo" items="${ar}" varStatus="vs"> <%-- requestScope.ar = ar --%>
      <tr>
        <td>${pvo.totalRecord-((pov.nowPage-1)*pvo.numPerPage+vs.index)}</td>
        <td style="text-align: left">
          <a href="Controller?type=view&b_idx=${vo.b_idx}&cPage=${pvo.nowPage}"> <%-- b_idx는 조회수 증가 시 확인을 위해, cPage는 목록으로 돌아갈 때 사용하기 위해 보낸다. --%>
              ${vo.subject}
            <c:if test="${vo.c_list.size() > 0}">
              (${vo.c_list.size()})
            </c:if>
          </a>
        </td>
        <td>${vo.writer}</td>
        <td>${vo.write_date}</td>
        <td>${vo.hit}</td>
      </tr>
    </c:forEach>
    <c:if test="${ar eq null or fn:length(ar) eq 0}">
      <tr>
        <td colspan="5">현재 등록된 데이터가 없습니다.</td>
      </tr>
    </c:if>
    </tbody>
  </table>

</div>

<%--<script src="https://code.jquery.com/jquery-3.7.1.js"></script>--%>
<%--<script>--%>
<%--  0102--%>
<%--  $(function () {--%>
<%--    let param = "type=getlist";--%>

<%--    $.ajax({--%>
<%--      url: "Controller",--%>
<%--      type: "post",--%>
<%--      data: param,--%>
<%--    }).done(function (res) {--%>
<%--      $("table>tbody").html(res);--%>
<%--    })--%>
<%--  });--%>
<%--</script>--%>
</body>
</html>