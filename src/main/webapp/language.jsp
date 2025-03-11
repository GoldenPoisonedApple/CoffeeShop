<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
	<title>JSTL 国際化サンプル</title>
</head>

<body>

	<%-- 言語設定（パラメータ "lang" を取得して適用） --%>
		<fmt:setLocale value="${sessionScope.language}" />
		<fmt:setBundle basename="messages" />

		<h1>
			<fmt:message key="welcome.message" />
		</h1>
		<p>${sessionScope.language}</p>

		<hr>
		<p>言語を変更:</p>
		<ul>
			<li><a href="change-language?lang=en">英語 (English)</a></li>
			<li><a href="change-language?lang=ja">日本語 (Japanese)</a></li>
		</ul>

</body>

</html>