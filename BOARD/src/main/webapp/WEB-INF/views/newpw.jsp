<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page import="java.net.URLDecoder"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-1.11.3.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" />
    <style>
        * { box-sizing:border-box; }

        form {
            width:400px;
            height:700px;
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

        .input-field {
            width: 300px;
            height: 40px;
            border : 1px solid rgb(89,117,196);
            border-radius:5px;
            padding: 0 10px;
            margin-bottom: 10px;
        }
        .input-field:read-only{
    	    color: gray;
        }
        label {
            width:300px;
            height:30px;
            margin-top :4px;
        }
        
        .labelAndBtn{
     	  width:100px; 
        }

        button {
            background-color: rgb(89,117,196);
            color : white;
            width:300px;
            height:50px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 10px 0 15px 0;
        }

        .title {
            font-size : 50px;
            margin: 20px 0;
        }

        .msg {
            height: 30px;
            text-align:center;
            font-size:16px;
            color:red;
            margin-bottom: 20px;
        }
    </style>
    <title>??????????????????</title>
</head>
<body>
	<form>
		<label for="pw">????????????</label>
		<input class="input-field" type="password" name="pw" id="pw" placeholder="??????????????? ????????? ????????????."> 		
		<label for="pwCheck">??????</label>
		<input class="input-field" type="password" name="pwCheck" id="pwCheck" placeholder="????????? ??????????????? ??????????????????.">	
		<input type="hidden" name="id" id="id" value="${vid}">	 	
		<button type="button" id="btnSave">??? ???</button>
		<button type="button" id="btnBack" onclick="<c:url value='/'/>">????????????</button>
	</form> 
   
   <script>
        $("#btnSave").on("click", function(){
        	//??????
   			let pw = document.getElementById("pw").value;
   			let pwCheck = document.getElementById("pwCheck").value;
   			
   			if (pw.trim().length == 0 || pwCheck.trim().length == 0){
   				alert('????????? ?????? ????????? ?????????????????????.');
   				return;
   			} 
   			
   			if (pw.trim() !== pwCheck.trim()){
   				alert('??????????????? ???????????? ????????? ????????? ????????????.');
   				return; 				
   			}
   			let id = document.getElementById("id").value;
			//???????????? ?????????
   			setUserPw(id, pw);
   				
        });
         
        //????????????
        function setUserPw(id, pw){
   			let request = new XMLHttpRequest(); 
	      	let url =  "<c:url value='/user/newpw'/>";	      	
	      	url += "?id=" + encodeURI(id); 
	      	url += "&pw=" + encodeURI(pw);
	      	
			request.open("POST", url, true); //??????
			request.onreadystatechange = function(){
				if (request.readyState == 4 && request.status == 200){		
					let obj = JSON.parse(request.responseText);
					if (obj.isSuccess === "Y"){
						alert("???????????? ????????? ??????????????????. ?????? ????????? ????????????");	
						location.href = "<c:url value='/login/login'/>";
					}else{
						alert("????????? ??????????????????");	
					}
					
				}			
			};
			request.send(null);	     	
        }
   </script>
</body>
</html>