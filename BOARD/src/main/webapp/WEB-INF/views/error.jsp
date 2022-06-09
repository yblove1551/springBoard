<%@ page contentType="text/html;charset=utf-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>error.jsp</title>
</head>
<body>
<h1>죄송합니다 서버에러가 발생했습니다.</h1>
에러내용 : ${msg} 
<ol>

<!-- 
발생한 예외 : ${pageContext.exception}<br>
예외 메시지 : ${pageContext.exception.message}<br>
<c:forEach items="${pageContext.exception.stackTrace}" var="i">
	${i.toString()}
</c:forEach>
 -->

</ol>
</body>
</html>