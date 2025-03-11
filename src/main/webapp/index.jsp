<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%-- 言語設定取得 --%>
<fmt:setLocale value="${sessionScope.language}" />
<fmt:setBundle basename="messages" />

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title><fmt:message key="index.title" /></title>
  <link rel="stylesheet" href="css/coffeeShop.css">
</head>
<body>
  <p>
    <fmt:message key="index.description" />
  </p>
  <ul class="no-list-style">
    <li><fmt:message key="index.task1" />&nbsp;<a href="./findItem.html"><fmt:message key="index.task1.name" /></a></li>
    <li><fmt:message key="index.task2" />&nbsp;<a href="./order.html"><fmt:message key="index.task2.name" /></a></li>

		<li><fmt:message key="index.task3" />&nbsp;<a href="./showAllTable"><fmt:message key="index.task3.name" /></a></li>
  </ul>
	<p><fmt:message key="index.language.change" />:	${sessionScope.language}</p>
	<p><fmt:message key="index.language.reason" /></p>
	<ul>
		<li><a href="change-language?lang=en"><fmt:message key="index.language.english" /></a></li>
		<li><a href="change-language?lang=ja"><fmt:message key="index.language.japanese" /></a></li>
		<li><a href="change-language?lang=zh"><fmt:message key="index.language.chinese" /></a></li>
		<li><a href="change-language?lang=ar"><fmt:message key="index.language.arabic" /></a></li>

	</ul>
</body>
</html>
