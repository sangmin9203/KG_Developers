<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta  name="viewport" content="width-device-width" initial-scale="1">
<link rel="stylesheet" href="/KGDevelopers/css/bootstrap.css">
<link rel="stylesheet" href="./css/bootstrap-theme.min.css">
<script src="./js/jquery-3.1.1.min.js"></script>
<script src="./js/bootstrap.min.js"></script>
<title>Insert title here</title>
</head>
<body>
<jsp:include page="include/header.jsp" />
<br><br>

<div calss="container" style="margin : auto; width: 700px; align : center">
   <div class="row">
         <h2>회원가입</h2><br>
      <h4>아래 항목에 모두 기입해주세요</h4><br>
      <form action="insertUser.do" method="post"><!--회원가입을 클릭하면 넘어갈 페이지  -->
         <!-- <div class="form-group">
               <label for="profile1">프로필 사진 첨부</label>
                <input type="file" class="form-control" id="profile1" name="profile_pic">
                 아이디 중복 체크하러가요 
              </div> -->
         <div class="form-group">
               <label for="id1">아이디:</label>
                 <input type="text" class="form-control" id="id1" name="userid">
                 <!-- <a href="#"><input style="margin-top: 10px;" type="button" value="아이디 중복체크"></a> -->
                 <div class="check_font" align="left" id="idCheck"></div>
                 <!--아이디 중복 체크하러가요  -->
              </div>
        <div class="form-group">
               <label for="nick1">별명:</label>
                <input type="text" class="form-control" id="nick1" name="nickname">
         		<div class="check_font" align="left" id="nickCheck"></div>
         </div>   
         <div class="form-group">
               <label for="name1">이름:</label>
                 <input type="text" class="form-control" id="name1" name="name">
              </div>
              
         <div class="form-group">
         		<!-- request로 받을 변수 name 주의 -->
               <label for="password1">비밀번호:</label>
                <input type="password" class="form-control" id="password1" name="password" onchange="check_pw()">
         </div>
         <div class="form-group">
               <label for="password2">비밀번호 확인:</label>
                <input type="password" class="form-control" id="password2" name="password2" onchange="check_pw()">
                &nbsp;<span id="check"></span>
         </div>

            <div class="form-group">
               <label for="email1">이메일:</label>
                <input type="email" class="form-control" id="email1" name="email">   
            </div>   
            <div style="margin-right:50px;">
                <input class="btn btn-primary" type="submit" id="rbtn" value="회원가입"> 
                <input class="btn btn-warning" type="button" value="취소"  onclick="goBack()">   
             </div>
      </form>
   </div>
</div>

<br><br>
<jsp:include page="include/footer.jsp" />

   <script>
	var flag = 0;
	var uflag = 0;
	var nflag = 0;
	$("#rbtn").attr("disabled", true);
	$("#id1").blur(function() {
		var url = "idCheck.do";
		var userid = $("#id1").val();
		var idJ = /^[a-z0-9]{4,12}$/;
		$.ajax({
			data : {
				id : userid
			},
			dataType :"text",
			url : url,
			type : "get",
			contentType: "application/x-json; charset=UTF-8",
			success : function(data){

				console.log(data);
				if(data === "NO"){
					$("#idCheck").text("사용중인 아이디입니다!");
					$("#idCheck").css("color", "red");
					uflag = 0;
					$("#rbtn").attr("disabled", true);
				}else{
					if(idJ.test(userid)){
						$("#idCheck").text("사용 가능한 아이디입니다!");
						$("#idCheck").css("color", "green");
						uflag = 1;
						if(uflag + nflag >= 2){
							$("#rbtn").removeAttr("disabled");
						}
						console.log("uflag : " + uflag);
						console.log("flag : " + flag);
					} else if(userid == ""){
						$("#idCheck").text("아이디를 입력해주세요!");
						$("#idCheck").css("color", "red");
						uflag = 0;
						$("#rbtn").attr("disabled", true);
					} else if(!idJ.test(userid)){
						$('#idCheck').text("소문자나 숫자 4~12자리 입력!");
						$('#idCheck').css('color', 'red');
						uflag = 0;
						$("#rbtn").attr("disabled", true);
					}
				}
				
			}, error : function(){
				console.log("실패");
			}
		});
	});
	
	$("#nick1").blur(function() {
		var url = "nickCheck.do";
		var nickname = $("#nick1").val();
		var idJ = /^[0-9a-zA-Z가-힣]{2,10}$/;
		$.ajax({
			data : {
				id : nickname
			},
			dataType :"text",
			url : url,
			type : "get",
			contentType: "application/x-json; charset=UTF-8",
			success : function(data){

				console.log(data);
				if(data === "NO"){
					$("#nickCheck").text("사용중인 별명입니다!");
					$("#nickCheck").css("color", "red");
					nflag = 0;
					$("#rbtn").attr("disabled", true);
				}else {
					if(idJ.test(nickname)){
						$("#nickCheck").text("사용 가능한 별명입니다!");
						$("#nickCheck").css("color", "green");
						nflag = 1;
						if(uflag + nflag >= 2){
							$("#rbtn").removeAttr("disabled");
						}
						console.log("nflag : " + nflag);
						console.log("flag : " + flag);
					} else if(nickname == ""){
						$("#nickCheck").text("별명를 입력해주세요!");
						$("#nickCheck").css("color", "red");
						nflag = 0;
						$("#rbtn").attr("disabled", true);
					} else if(!idJ.test(nickname)){
						$('#nickCheck').text("한영 소문자, 숫자 2~10자리 입력!");
						$('#nickCheck').css('color', 'red');
						nflag = 0;
						$("#rbtn").attr("disabled", true);
					}
				}
				
			}, error : function(){
				console.log("실패");
			}
		});
	});
	
	function goBack(){
		window.history.back();
	}
	
	
	function check_pw(){
		if(document.getElementById('password1').value !='' && document.getElementById('password2').value!=''){
		    if(document.getElementById('password1').value==document.getElementById('password2').value){
		        document.getElementById('check').innerHTML='비밀번호가 일치합니다.'
		        document.getElementById('check').style.color='green';
		    }
		    else{
		        document.getElementById('check').innerHTML='비밀번호가 일치하지 않습니다.';
		        document.getElementById('check').style.color='red';
		    }
		}}

	
	</script>
</body>
</html>