<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- 言語設定取得 --%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title><fmt:message key="showAllTable.title" /></title>
  <link rel="stylesheet" href="css/coffeeShop.css">
</head>
<body>
	<p><a href="./index.jsp">＜ <fmt:message key="showAllTable.back" /></a></p>
	<br>
	<h1><fmt:message key="showAllTable.itemTable" /></h1>
	<table class="itemList">
		<tr>
			<th><fmt:message key="showAllTable.itemTable.id" /></th>
			<th><fmt:message key="showAllTable.itemTable.name" /></th>
			<th><fmt:message key="showAllTable.itemTable.area" /></th>
			<th><fmt:message key="showAllTable.itemTable.originalHome" /></th>
			<th><fmt:message key="showAllTable.itemTable.price" /></th>
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

	<h1><fmt:message key="showAllTable.orderTable" /></h1>
	<table class="itemList">
		<tr>
			<th><fmt:message key="showAllTable.orderTable.id" /></th>
			<th><fmt:message key="showAllTable.orderTable.itemId" /></th>
			<th><fmt:message key="showAllTable.orderTable.name" /></th>
			<th><fmt:message key="showAllTable.orderTable.address" /></th>
			<th><fmt:message key="showAllTable.orderTable.telNumber" /></th>
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
	</table>
</body>
</html>
