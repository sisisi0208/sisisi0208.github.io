<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>デッキリスト</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>

<body class="bg-light">
<div class="container py-4">

<h1 class="mb-4">デッキリスト</h1>
<div class="row g-3">

<%-- 新規作成のカード --%>
<div class="col-md-3">
<div class="card h-100 bg-warning-subtle">
<div class="card-body">

<h5 class="card-title">新規デッキ作成</h5>
<p class="card-text">新しいデッキを作成します</p>

<%-- カード全体のリンク化 --%>
<a href="/deckApp/deckRegister" class="stretched-link"></a>

</div>
</div>
</div>

<%-- 既存デッキのカード --%>
<c:forEach var="deck" items="${deckList}">
<div class="col-md-3">
<div class="card h-100 bg-success-subtle">
<div class="card-body">

<%-- デッキ名 --%>
<h5 class="card-title">
<c:out value="${deck.name}"/>
</h5>
<%-- デッキ説明 --%>
<p class="card-text">
<c:out value="${deck.description}"/>
</p>

<%-- カード全体を編集リンク化 --%>
<a href="${pageContext.request.contextPath}/deckEdit?deckId=${deck.id}" class="stretched-link"></a>
<%-- 削除 --%>
<a href="${pageContext.request.contextPath}/deckDelete?deckId=${deck.id}" class="btn btn-danger btn-sm position-relative z-2 float-end">削除</a>

</div>
</div>
</div>
</c:forEach>
</div>

<%-- 既存デッキが登録されていない場合 --%>
<c:if test="${empty deckList}">
<p class="mt-3">登録されているデッキはありません</p>
</c:if>
<p class="mt-4">
<a href="/deckApp/home">ホームに戻る</a>
</p>

</div>
</body>

</html>