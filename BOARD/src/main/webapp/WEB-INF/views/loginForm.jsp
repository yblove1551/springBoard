<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="loginId"     value="${sessionScope.id == null ? '': sessionScope.id}"/>
<c:set var="loginLink"   value="${loginId=='' ? '/login/login' : '/login/logout'}"/>
<c:set var="loginLabel"  value="${loginId=='' ? 'Login' : 'Logout'}"/>
<c:set var="regLabel"    value="${loginId=='' ? 'Sign in' : 'Account'}"/>
<c:set var="regLink"     value="${loginId=='' ? '/user/add' : '/user/modify'}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>로그인</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
   <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"/>
  <style>
    * { box-sizing:border-box; }
    a { text-decoration: none; }
    form {
      width:400px;
      height:500px;
      display : flex;
      flex-direction: column;
      align-items:center;
      position : absolute;
      top:50%;
      left:50%;
      transform: translate(-50%, -50%) ;
      border: 1px solid rgb(89,117,196);
      border-radius: 10px;
    }
    input[type='text'], input[type='password'] {
      width: 300px;
      height: 40px;
      border : 1px solid rgb(89,117,196);
      border-radius:5px;
      padding: 0 10px;
      margin-bottom: 10px;
    }
    input[type='button'] {
      background-color: rgb(89,117,196);
      color : white;
      width:300px;
      height:50px;
      font-size: 17px;
      border : none;
      border-radius: 5px;
      margin : 20px 0 30px 0;
    }
    #title {
      font-size : 50px;
      margin: 40px 0 30px 0;
    }
    #msg {
      height: 30px;
      text-align:center;
      font-size:16px;
      color:red;
      margin-bottom: 20px;
    }
  </style>
</head>
<body>
	<div id="menu">
	    <ul>
	        <li id="logo"></li>
	        <li><a href="<c:url value='/'/>">Home</a></li>
	        <li><a href="<c:url value='/board/list'/>">Board</a></li>
	        <li><a href="<c:url value='${loginLink}'/>">${loginLabel}</a></li>
	        <li><a href="<c:url value='${regLink}'/>">${regLabel}</a></li>
	    </ul>
	</div>
	<form>
		<h3 id="title">Login</h3>
		<div id="msg">

		</div>
		<input type="text" 		id="id"     name="id" 	value="${cookie.id.value}" placeholder="이메일 입력"  required ${empty cookie.id.value ? "autofocus" : ""}>
		<input type="password" 	id="pwd" 	name="pwd" 	placeholder="비밀번호" required ${not empty cookie.id.value ? "autofocus" : ""}>
		<input type="hidden" 	id="toURL"	name="toURL" value="${param.toURL}">
		<input type="button" 	id="btnLogin" value="로그인">
		<div>
	    	<label><input type="checkbox" id="rememberId" name="rememberId" value="true" ${empty cookie.id.value ? "":"checked"}> 아이디 기억</label> |
	    	<a href="<c:url value='/user/find'/>">비밀번호 찾기</a> |
	    	<a href="<c:url value='/user/add'/>">회원가입</a>
	  	</div>
	</form>
</body>
<script>
		$("#btnLogin").on("click", function(){
			let jd = {
					id:document.getElementById("id").value,
					pwd:document.getElementById("pwd").value,
					toURL:document.getElementById("toURL").value,
					rememberId: document.getElementById("rememberId").checked ? document.getElementById("rememberId").value : ""
			}

			$.ajax({
				type:"post",
				url:"/board/login/login",
				headers: {"content-type":"application/json; charset=UTF-8"},
				data: JSON.stringify(jd),
   				success: function(result){
   					location.href = "/board" + (jd.toURL == null ? "" : jd.toURL);
   				},
   				error:function(result){
   					let obj = JSON.parse(result.responseText);
   					document.getElementById("msg").innerHTML = "<i class='fa fa-exclamation-circle'>" + obj.result.errMsg + "</i>";
   				}
				
			});
		});

</script>

</html>