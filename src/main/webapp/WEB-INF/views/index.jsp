<%@ page import="kr.java.jdbc.entity.Board" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>게시판</title>
</head>
<body>
    <h1>게시판에 오신걸 환영해요 :)</h1>
<%--    <%= request.getAttribute("boards") %>--%>
    <% List<Board> boards = (List<Board>) request.getAttribute("boards");
        for (Board b : boards) {
    %>
        <p>ID : <%= b.boardId() %> / CONTENT : <%= b.content() %> / CREATED_AT : <%= b.createdAt() %></p>
    <% } %>

    <form method="post">
        <input name="content" placeholder="내용 입력">
        <button>등록</button>
    </form>

    <form method="post" action="login">
        <input name="username" placeholder="이름 입력">
        <button>로그인</button>
    </form>
</body>
</html>