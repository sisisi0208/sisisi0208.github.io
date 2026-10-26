<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>デッキ編集画面</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>

<body class="bg-light">
<div class="container py-4">

<h1 class="mb-4">デッキ名：<c:out value="${deck.name}"></c:out></h1>
<div class="row g-4 align-items-start">

<div class="col-md-4">
<div class="card bg-warning-subtle">
<div class="card-body">

<c:if test="${not empty errorMsg}">
<ul>
<c:forEach var="msg" items="${errorMsg }">
<li style="color:red;">${msg}</li>
</c:forEach>
</ul>
</c:if>
<h2>カード登録</h2>
<p>下記のフォームより、カードの登録をしてください</p>
<form action="/deckApp/deckEdit" method="post">
<input type="hidden" name="operation" value="register">
<input type="hidden" name="deckId" value="${deckId}">
<table class="table table-bordered align-middle mb-3">
<tr>
<th class="table-warning">カード名</th>
<td><input type="text" name="name" class="form-control"></td>
</tr>
<tr>
<th class="table-warning">色</th>
<td><input type="text" name="color" class="form-control"></td>
</tr>
<tr>
<th class="table-warning">コスト</th>
<td><input type="text" name="cost" class="form-control"></td>
</tr>
<tr>
<th class="table-warning">インク</th>
<td><input type="text" name="inc" class="form-control"></td>
</tr>
<tr>
<th class="table-warning">種類</th>
<td><input type="text" name="type" class="form-control"></td>
</tr>
<tr>
<th class="table-warning">枚数</th>
<td><input type="text" name="quantity" class="form-control"></td>
</tr>
</table>
<button type="submit" class="btn btn-warning w-100">登録</button>
</form>
</div>
</div>
</div>

<div class="col-md-8">
<div class="card bg-success-subtle">
<div class="card-body">

<h2>カード一覧</h2>
<p><strong>カード合計枚数:${totalQuantity}枚</strong></p>
<c:choose>
<%-- カードが登録されていない場合 --%>
<c:when test="${empty cardList}">
<p>登録されているカードはありません</p>
</c:when>

<%-- カードが登録されている場合 --%>
<c:otherwise>

<div class="table-responsive">
<table class="table table-bordered table-striped table-hover align-middle">
<thead class="table-success">
<tr>
<th>カード名</th>
<th>色</th>
<th>コスト</th>
<th>インク</th>
<th>種類</th>
<th>枚数</th>
<th>削除</th>
</tr>
</thead>

<tbody>
<c:forEach var="card" items="${cardList}">
<tr>
<%-- カード名 --%>
<td>
<c:out value="${card.name}"/>
</td>
<%-- 色 --%>
<td>
<c:out value="${card.color}"/>
</td>
<%-- コスト --%>
<td>
<c:out value="${card.cost}"/>
</td>
<%-- インク --%>
<td>
<c:out value="${card.inc}"/>
</td>
<%-- 種類 --%>
<td>
<c:out value="${card.type}"/>
</td>
<%-- 枚数 --%>
<td>
<c:out value="${card.quantity}"/>
</td>

<%-- 削除 --%>
<td>
<form action="${pageContext.request.contextPath}/deckEdit" method="post">
<input type="hidden" name="operation" value="delete">
<input type="hidden" name="cardId" value="${card.id}">
<input type="hidden" name="deckId" value="${deckId}">
<button type="submit" class="btn btn-danger btn-sm">削除</button>
</form>
</td>

</tr>
</c:forEach>
</tbody>
</table>
</div>
</c:otherwise>
</c:choose>
</div>
</div>
</div>
</div>

<p><a href="/deckApp/deckList">デッキリストに戻る</a></p>

</div>
</body>

</html>