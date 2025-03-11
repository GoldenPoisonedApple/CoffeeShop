<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>全テーブル表示</title>
  <link rel="stylesheet" href="css/coffeeShop.css">
</head>
<body>
	<p><a href="./index.html">＜ 戻る</a></p>
	<br>
	<h1>商品テーブル</h1>
	<table class="itemList">
		<tr>
			<th>ID</th>
			<th>商品名</th>
			<th>原産地域</th>
			<th>原産地</th>
			<th>値段</th>
		</tr>
		<c:forEach var="item" items="${items}">
			<tr>
				<td>${item.id}</td>
				<td>${item.name}</td>
				<td>${item.area}</td>
				<td>${item.originalHome}</td>
				<td>${item.price}</td>
			</tr>
		</c:forEach>
	</table>

	<h1>注文テーブル</h1>
	<table class="itemList">
		<tr>
			<th>ID</th>
			<th>商品ID</th>
			<th>氏名</th>
			<th>住所</th>
			<th>電話番号</th>
		</tr>
		<c:forEach var="order" items="${orders}">
			<tr>
				<td>${order.id}</td>
				<td>${order.itemId}</td>
				<td>${order.name}</td>
				<td>${order.address}</td>
				<td>${order.telNumber}</td>
			</tr>
		</c:forEach>
</body>
</html>
