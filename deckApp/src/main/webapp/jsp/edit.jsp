<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>会員情報編集画面</title>
<link rel="stylesheet" type="text/css" href="/deckApp/css/stylesheet.css"/>
</head>

<body>
<h1>ユーザー情報編集</h1>
<p style="text-align:right">${user.name} 様ログイン中</p>
<p style="text-align:right"><a href="/deckApp/logout">ログアウト</a></p>

<c:if test="${errorMsg.size() > 0 }">
<ul>
<c:forEach var="msg" items="${errorMsg}">
<li style="color:red;">${msg}</li>
</c:forEach>
</ul>
</c:if>
<c:if test="${editError != null}">
<div style="color:red;">
<p>${editError}</p>
</div>
</c:if>
<h2>変更内容入力</h2>
<form action="/deckApp/edit" method="post">
<table border="1">
<tr>
<th>お名前</th>
<td><input type="text" name="editName" placeholder="1～10文字"></td>
</tr>
<tr>
<th>パスワード</th>
<td><input type="password" name="editPassword" placeholder="2～10文字"></td>
</tr>
</table>
<input type="submit" value="変更する（確認画面へ）">
</form>
<p><a href="/deckApp/jsp/home.jsp">ホーム画面へ戻る</a></p>
</body>

</html>