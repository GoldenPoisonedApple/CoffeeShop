<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>コーヒー豆検索結果</title>
  <link rel="stylesheet" href="css/coffeeShop.css">
</head>
<body>
	<table class="itemList">
		<tr>
			<th>商品名</th>
			<th>原産地域</th>
			<th>原産地</th>
			<th>値段</th>
		</tr>
		<c:forEach var="item" items="${itemList}">
			<tr>
				<td>${item.name}</td>
				<td>${item.area}</td>
				<td>${item.originalHome}</td>
				<td>${item.price}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
