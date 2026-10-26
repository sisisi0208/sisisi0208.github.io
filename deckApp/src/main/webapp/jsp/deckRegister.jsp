<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>新規デッキ作成</title>
<link rel="stylesheet" type="text/css" href="/deckApp/css/stylesheet.css"/>
</head>

<body>
<h1>新規デッキ作成</h1>

<c:if test="${errorMsgList.size() > 0 }">
<ul>
<c:forEach var="msg" items="${errorMsgList}">
<li style="color:red;">${msg}</li>
</c:forEach>
</ul>
</c:if>
<c:if test="${registerError != null}">
<div style="color:red;">
<p>${registerError}</p>
</div>
</c:if>
<h2>デッキ作成フォーム</h2>
<p>下記のフォームより、デッキ作成してください</p>
<form action="/deckApp/deckRegister" method="post">
<table border="1">
<tr>
<th>デッキ名</th>
<td><input type="text" name="name" value="<c:out value='${name}'/>" placeholder="1～255文字"></td>
</tr>
<tr>
<th>デッキ説明</th>
<td><input type="text" name="description"></td>
</tr>
</table>
<input type="submit" value="作成">
</form>
<p><a href="/deckApp/deckList">デッキリストに戻る</a></p>
</body>

</html>