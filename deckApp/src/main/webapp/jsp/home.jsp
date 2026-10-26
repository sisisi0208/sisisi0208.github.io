<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>デッキ管理アプリ</title>
<link rel="stylesheet" type="text/css" href="/deckApp/css/stylesheet.css"/>
</head>

<body>
<h1>デッキ管理アプリ</h1>
<p style="text-align: right">${user.name} 様ログイン中</p>
<p style="text-align: right"><a href="/deckApp/logout">ログアウト</a></p>
<h2></h2>
<p><a href="/deckApp/deckList">・デッキリスト</a><p>
<p style="text-align: right"><a href="/deckApp/edit">ユーザー情報の編集</a></p>
<p style="text-align: right"><a href="/deckApp/delete">退会する方はこちら</a></p>
</body>

</html>