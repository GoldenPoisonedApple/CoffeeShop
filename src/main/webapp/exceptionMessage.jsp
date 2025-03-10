<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.io.PrintWriter"%>
<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>例外ページ</title>
  <link rel="stylesheet" href="css/coffeeShop.css">
</head>
<body>
  <!-- 本ページは、実装アセスメント(JSPサーブレット)用のページです -->
  <!-- 本来であれば、 printStackTrace の内容を画面に表示することは -->
  <!-- セキュリティの観点から危険な行為のため行いません。 -->
  <h1>例外のprintStackTrace</h1>
  <!-- 
    <pre> 要素
    Preformatted Text（事前整形されたテキスト）を表すための要素です。
    改行やホワイトスペースをそのままの形で表示します。
   -->
  <pre>
    <%
    Exception exception = (Exception) request.getAttribute("exception");
    exception.printStackTrace(new PrintWriter(out));
    %>
  </pre>
</body>
</html>