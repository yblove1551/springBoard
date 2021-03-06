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
			<label for="id" >?????????</label>
			<input type="button" name="idCheck" id="idCheck" class="checkBtn" value="??????">		
		</div>
	
		<input class="input-field" type="text" name="id" id="id" autocomplete="off" placeholder="5~30????????? ?????????????????? ?????? ??????" required value="${user.id}" ${sessionScope.id == null ? "":"readonly"}>
		<label for="pwd">????????????</label>
		<input class="input-field" type="text" name="pwd" id="pwd" autocomplete="off" placeholder="5~30????????? ?????????????????? ?????? ??????" required value="${user.pwd}">
		<label for="name">??????</label>
		<input class="input-field" type="text" name="name" id="name" autocomplete="off" placeholder="?????????" required value="${user.name}">
		<label for="phone">?????????</label>
		<input class="input-field" type="text" name="phone" id="phone" autocomplete="off" placeholder="-??????" value="${user.phone}"> 
			
		<div style="width:300px; height:40px;">
			<label for="email">?????????</label>
			<input type="button" name="emailCheck" id="emailCheck" class="checkBtn" value="??????">
			<input type="button" name="emailModify" id="emailModify" class="checkBtn" value="??????" style="display:none;">
		</div>
		<input class="input-field" type="text" name="email" id="email" autocomplete="off" placeholder="example@naver.co.kr" value="${user.email}"> 
		<div id="email-Auth" style="display:none;">
			<input type="text" name="emailAuthNum" id="emailAuthNum">
			<input type="button" id="btnEmailAuth" class="checkBtn" name="btnEmailAuth" value="??????" >
		</div>
		
		<label for="birth">??????</label>
		<input class="input-field" type="text" name="birth" id="birth" autocomplete="off" placeholder="2020-12-31" value=<fmt:formatDate value="${user.birth}" pattern="yyyy-MM-dd" type="date"/>>
		 	
		<button type="button" id="btnSave">??? ???</button>
		<button type="button" id="btnBack" onclick="history.go(-1)">????????????</button>
		<button type="button" id="btnDelete" style="display:none">????????????</button>
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
   	   					let msg = isNew ? "??????????????? ??????????????????." : "?????????????????????"; 	
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
   		   		if (!confirm("?????? ????????? ?????????????????????????")) return;
   		
   	   			$.ajax({
   	   				type:"POST",
   	   				url: "<c:url value='/user/remove?id="+id+"'/>",
   	   				headers: {"content-type" : "application/json; charset=UTF-8"},
   	   				success: function(result){
   	   					if (result.isSuccess == "Y"){
   	   						alert("??????????????? ?????????????????????.");		
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
                setMessage('????????? ????????? 4~15????????? ?????????.', id);
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
        				alert("??????????????? ??????????????????");
        				document.getElementById("idCheck").disabled = true;
        				document.getElementById("id").readOnly = true;
        			}else
        				alert("????????? ??? ?????? ??????????????????.");
        		},
        		error: function(result){
	        		alert("????????? ??????????????????");
        		}
        	}); 
        });
	

        $("#emailCheck").on("click", function(){
            let email = document.querySelector("#email");
            let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;

            if (email.value.trim() == ""){
            	setMessage('???????????? ??????????????????.', email);
            	return;
            }

            let emailVal = email.value;
          	if (!regEmail.test(emailVal)){
          		setMessage('????????? ????????? ?????? ????????????.', email);
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
        				alert("?????? ????????? ??????????????????.");
    		            let email = document.getElementById("email").value;
			            let name = document.getElementById("name").value;
			            let kind = 1;
			            sendMail(email, name, kind);	
        			}
        			else
        				alert("?????? ???????????? ??????????????????.");
        		},
        		error: function(result){
        			alert("????????? ?????? ??? ????????? ??????????????????.");
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
						alert("??????????????? ?????????????????????");      			
        			}else{
        				alert("???????????? ????????? ??????????????????");		
        			}
        		},
        		error: function(result){
        			alert("???????????? ????????? ??????????????????");	
        		}
        	});	     	
        }
       
        $("#btnEmailAuth").on("click", function(){
            let email = document.getElementById("email").value;
            let authNum = document.getElementById("emailAuthNum").value;
            if (authNum.length != 4){
                setMessage('??????????????? 4???????????????.', authNum);
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
        				alert("????????? ?????????????????????");
        				document.getElementById("email-Auth").style.display = "none";
        			}else{
        				alert("???????????? ?????? ???????????????");
        			}
        		},
        		error: function(result){
        			alert("??????????????? ??????????????????.");
        		}	
			});    	
        }
        
        $("#emailModify").on("click", function(){
        	if (!confirm("???????????? ??????????????? ????????? ???????????????. ?????? ???????????????????"))	 return;
        	
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
                setMessage('????????? ????????? 4~15????????? ?????????..', frm.id);
                return false;
            }
            
            
            let pwdVal = frm.pwd.value;	     
            if (pwdVal.length < 3 || pwdVal.length > 15){
                setMessage('???????????? ????????? 4~15????????? ?????????.', frm.pwd);
                return;
            }
            
            let regEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
            let emailVal = frm.email.value;
          	if (!regEmail.test(emailVal)){
          		setMessage('????????? ????????? ?????? ????????????.', frm.email);
          		return;
          	}  
          	
            let nameVal = frm.name.value;	     
            if (nameVal.length < 2 || nameVal.length > 30){
                setMessage('????????? 2~30??? ???????????????.', frm.name);
                return;
            }         	
                      
            if (!document.getElementById("idCheck").disabled){
                setMessage('????????? ????????? ???????????????.', document.getElementById("idCheck"));
                return false;            	
            }
            
            if (!document.getElementById("emailCheck").disabled){
                setMessage('????????? ????????? ???????????????.', document.getElementById("emailCheck"));
                return false;            	
            }
          
            let result = confirm("?????????????????????????");
            
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