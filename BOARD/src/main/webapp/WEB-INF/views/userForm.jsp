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
         .checkBtn {
            background-color: rgb(89,117,196);
            color : white;
            width:50px;
            height:30px;
            font-size: 17px;
            border : none;
            border-radius: 5px;
            margin : 10px 0 15px 0;
        }   
        
        .checkBtn:hover{
  			cursor:pointer;
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
            cursor:pointer;
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
    <title>Register</title>
</head>
<body>

	<form  method="POST" onsubmit="return formCheck(this)">
		<div class="title">Register</div>
		<div id="msg" class="msg"></div>  
		
		<div style="width:300px; height:40px;">
			<label for="id" >아이디</label>
			<input type="button" name="idCheck" id="idCheck" class="checkBtn" value="확인">		
		</div>
	
		<input class="input-field" type="text" name="id" id="id" autocomplete="off" placeholder="5~30자리의 영대소문자와 숫자 조합" required value="${user.id}" ${sessionScope.id == null ? "":"readonly"}>
		<label for="pwd">비밀번호</label>
		<input class="input-field" type="text" name="pwd" id="pwd" autocomplete="off" placeholder="5~30자리의 영대소문자와 숫자 조합" required value="${user.pwd}">
		<label for="name">이름</label>
		<input class="input-field" type="text" name="name" id="name" autocomplete="off" placeholder="홍길동" required value="${user.name}">
		<label for="phone">휴대폰</label>
		<input class="input-field" type="text" name="phone" id="phone" autocomplete="off" placeholder="-없이" value="${user.phone}"> 
			
		<div style="width:300px; height:40px;">
			<label for="email">이메일</label>
			<input type="button" name="emailCheck" id="emailCheck" class="checkBtn" value="확인">
			<input type="button" name="emailModify" id="emailModify" class="checkBtn" value="수정" style="display:none;">
		</div>
		<input class="input-field" type="text" name="email" id="email" autocomplete="off" placeholder="example@naver.co.kr" value="${user.email}"> 
		<div id="email-Auth" style="display:none;">
			<input type="text" name="emailAuthNum" id="emailAuthNum">
			<input type="button" id="btnEmailAuth" class="checkBtn" name="btnEmailAuth" value="인증" >
		</div>
		
		<label for="birth">생일</label>
		<input class="input-field" type="text" name="birth" id="birth" autocomplete="off" placeholder="2020-12-31" value=<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd" type="date"/>>
		 	
		<button type="button" id="btnSave">저 장</button>
		<button type="button" id="btnBack" onclick="history.go(-1)">돌아가기</button>
		<button type="button" id="btnDelete" style="display:none">회원탈퇴</button>
	</form> 
   
   <script>
   		var isNew = ${sessionScope.id == null};
   		$(document).ready(function(){
   			if (!isNew){
   				document.querySelector(".title").innerText = "Modify";
   			    document.querySelector("title").innerText = "Modify";
   			    document.querySelector("#btnDelete").style.display = "block";
   			    document.getElementById("idCheck").style.display = "none";
   				document.getElementById("idCheck").disabled = true;
   			    document.getElementById("emailCheck").style.display = "none";
   				document.getElementById("emailCheck").disabled = true;
   				document.getElementById("emailModify").style.display = "inline";	
   				document.getElementById("email").readOnly = true;
   			}   
   		});
   		
	        $("#btnSave").on("click", function(){        	
   	        	let form = document.querySelector("form");
   	        	if (!formCheck(form)) return;
   	        	
   	   			let id = document.getElementById("id").value; 			
   	   			let pwd = document.getElementById("pwd").value;
   	   			let name = document.getElementById("name").value;
   	   			let phone = document.getElementById("phone").value;
   	   			let email = document.getElementById("email").value;
   	   			let birth = document.getElementById("birth").value;
   	   			
   	   			$.ajax({
   	   				type:"POST",
   	   				url: ${sessionScope.id == null ? "'/board/user/add'" : "'/board/user/modify'"},
   	   				headers: {"content-type" : "application/json; charset=UTF-8"},
   	   				data: JSON.stringify({id:id, pwd:pwd, name:name, phone:phone, email:email, birth:birth}),
   	   				success: function(result){
   	   					let msg = isNew ? "회원가입에 성공했습니다." : "수정되었습니다"; 	
   	   					alert(msg);
   	   					location.href = "/board/";
   	   				},
   	   				error:function(result){
   	   					document.getElementById("msg").innerHTML = "<i class='fa fa-exclamation-circle'>" + result.msg +"</i>";
   	   				}
   	   			});
   	        });     
        
        $("#btnDelete").on("click", function(){
   	   		let id = "${user.id}";
   	   		if (id != "" && document.querySelector("#id").value === id){
   		   		if (!confirm("정말 회원을 탈퇴하시겠습니까?")) return;
   		
   	   			$.ajax({
   	   				type:"POST",
   	   				url: "<c:url value='/user/remove?id="+id+"'/>",
   	   				headers: {"content-type" : "application/json; charset=UTF-8"},
   	   				success: function(result){
   	   					if (result.isSuccess == "Y"){
   	   						alert("회원탈퇴에 성공하셨습니다.");		
   	   						location.href = "/board/";
   	   					}
   	   					else
   	   						document.getElementById("msg").innerHTML = "<i class='fa fa-exclamation-circle'>" + result.msg +"</i>"; 					
   	   				},
   	   				error:function(result){
   	   					document.getElementById("msg").innerHTML = "<i class='fa fa-exclamation-circle'>" + result.msg +"</i>";
   	   				}
   	   			});
   	   		}	
        });
        
        
        $("#idCheck").on("click", function(){
            let id = document.getElementById("id").value;      
            if (id.length < 3 || id.length > 15){
                setMessage('아이디 길이는 4~15자여야 합니다.', id);
                return;
            }
            
        	let url = "<c:url value='/user/id?id="+id+"'/>";
        	$.ajax({
        		type: "GET",
        		header: {"content-type" : "application/json; charset=utf-8"},
        		url: url,
        		success: function(result){  
        			let obj = JSON.parse(result);
        			if (obj.isSuccess == "Y"){
        				alert("사용가능한 아이디입니다");
        				document.getElementById("idCheck").disabled = true;
        				document.getElementById("id").readOnly = true;
        			}else
        				alert("사용할 수 없는 아이디입니다.");
        		},
        		error: function(result){
	        		alert("에러가 발생했습니다");
        		}
        	}); 
        });
	

        $("#emailCheck").on("click", function(){
            let email = document.querySelector("#email");
            let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

            if (email.value.trim() == ""){
            	setMessage('이메일을 입력해주세요.', email);
            	return;
            }

            let emailVal = email.value;
          	if (!regEmail.test(emailVal)){
          		setMessage('이메일 형식이 맞지 않습니다.', email);
          		return;
          	}  
            
          	let url = "<c:url value='/user/email?email="+email.value+"'/>";
        	 
        	$.ajax({
        		type: "GET",
        		headers: {"content-type" : "application/json; charset=utf-8"},
        		url: url,
        		success: function(result){
        			let isSuccess = JSON.parse(result).isSuccess;
        			if (isSuccess === "Y"){
        				alert("사용 가능한 이메일입니다.");
    		            let email = document.getElementById("email").value;
			            let name = document.getElementById("name").value;
			            let kind = 1;
			            sendMail(email, name, kind);	
        			}
        			else
        				alert("이미 사용중인 이메일입니다.");
        		},
        		error: function(result){
        			alert("이메일 확인 중 에러가 발생했습니다.");
        		}
        	});  
        });
        

        function sendMail(email, name, kind){
        	let paramObj = {
        			email : email, 
        			name : name,
        			kind : kind
        	};
        	
        	$.ajax({
        		type:"POST",
        		headers:{"content-type":"application/json; charset=utf-8"},
        		url:  "<c:url value='/mail/mail'/>",
        		data: JSON.stringify(paramObj),
        		success: function(result){
        			if (result.isSuccess == "Y"){
						document.getElementById("email").readOnly = true;
						document.getElementById("emailCheck").disabled = true;
						document.getElementById("email-Auth").style.display = "block";						
						alert("인증메일이 발송되었습니다");      			
        			}else{
        				alert("인증메일 발송에 실패했습니다");		
        			}
        		},
        		error: function(result){
        			alert("메일인증 오류가 발생했습니다");	
        		}
        	});	     	
        }
       
        $("#btnEmailAuth").on("click", function(){
            let email = document.getElementById("email").value;
            let authNum = document.getElementById("emailAuthNum").value;
            if (authNum.length != 4){
                setMessage('인증번호는 4자리입니다.', authNum);
                return;
            }

            authMail(email, authNum);
        });
        
        function authMail(email, authNumber){       	
        	let url = "<c:url value='/mail/auth?authNumber="+authNumber+"&email="+email+"'/>";
        	
        	$.ajax({
        		type: "POST",
				headers: {"content-type" : "application/json; charset=utf-8"},
        		url: url,
        		success: function(result){
        			let obj = JSON.parse(result);
        			alert()
        			if (obj.isSuccess == "Y"){
        				alert("인증이 완료되었습니다");
        				document.getElementById("email-Auth").style.display = "none";
        			}else{
        				alert("유효하지 않은 번호입니다");
        			}
        		},
        		error: function(result){
        			alert("인증에러가 발생했습니다.");
        		}	
			});    	
        }
        
        $("#emailModify").on("click", function(){
        	if (!confirm("이메일을 수정하려면 인증이 필요합니다. 정말 수정하십니까?"))	 return;
        	
			document.getElementById("emailCheck").style.display = "inline";
   			document.getElementById("emailCheck").disabled = false;
			document.getElementById("emailModify").style.display = "none";
   			document.getElementById("emailModify").disabled = true;
   			
   			document.getElementById("email").readOnly = false;
        }); 

          		   		   
       function formCheck(frm) {
            var msg ='';

            let idVal = frm.id.value;
            if(idVal.length < 4 || idVal.length > 15) {
                setMessage('아이디 길이는 4~15자여야 합니다..', frm.id);
                return false;
            }
            
            
            let pwdVal = frm.pwd.value;	     
            if (pwdVal.length < 3 || pwdVal.length > 15){
                setMessage('비밀번호 길이는 4~15자여야 합니다.', frm.pwd);
                return;
            }
            
            let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
            let emailVal = frm.email.value;
          	if (!regEmail.test(emailVal)){
          		setMessage('이메일 형식이 맞지 않습니다.', frm.email);
          		return;
          	}  
          	
            let nameVal = frm.name.value;	     
            if (nameVal.length < 2 || nameVal.length > 30){
                setMessage('이름은 2~30자 사이입니다.', frm.name);
                return;
            }         	
                      
            if (!document.getElementById("idCheck").disabled){
                setMessage('아이디 확인이 필요합니다.', document.getElementById("idCheck"));
                return false;            	
            }
            
            if (!document.getElementById("emailCheck").disabled){
                setMessage('이메일 인증이 필요합니다.', document.getElementById("emailCheck"));
                return false;            	
            }
          
            let result = confirm("저장하시겠습니까?");
            
           return result;
       }

       function setMessage(msg, element){
            document.getElementById("msg").innerHTML = `<i class="fa fa-exclamation-circle"> ${'${msg}'}</i>`;

            if(element) {
                element.select();
            }
       }
   </script>
</body>
</html>