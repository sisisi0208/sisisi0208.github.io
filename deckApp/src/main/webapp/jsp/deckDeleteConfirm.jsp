<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>デッキ削除確認</title>
<link rel="stylesheet" type="text/css" href="/deckApp/css/stylesheet.css"/>
</head>
<body>
<h1>デッキ削除確認画面</h1>
<p>下記の内容でよろしければ、削除ボタンを押してください。</p>
<c:if test="${not empty deleteError}">
<div style="color:red;">
<p>${deleteError}</p>
</div>
</c:if>

<h2>デッキ情報</h2>
<form action="/deckApp/deckDelete" method="post">
<input type="hidden" name="deckId" value="${deck.id}">
<table border="1">
<tr>
<th>デッキ名</th>
<td><c:out value="${deck.name}"/></td>
</tr>
<tr>
<th>デッキ説明</th>
<td><c:out value="${deck.description}"/></td>
</tr>
</table>
<input type="submit" value="削除">
</form>
</body>

</html>