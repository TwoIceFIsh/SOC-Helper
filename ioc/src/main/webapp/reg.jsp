<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="favicon" href="/images/favicon.ico">
<link href="css/my.css" rel="stylesheet">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>SOC Helper</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.css?after" rel="stylesheet">

</head>

<body class="login-bg">
	<script src="js/common2.js" charset="UTF-8"></script>
	<div class="container">

		<div class="card o-hidden border-0 shadow-lg my-5 badge-black1">
			<div class="card-body p-0">
				<!-- Nested Row within Card Body -->
				<div class="row">
					<div class="col-lg-5 d-none d-lg-block login-box"></div>
					<div class="col-lg-7">
						<div class="p-5">
							<div class="text-center">
								<h1 class="h4 mb-4">계정생성</h1>
							</div>
							<form class="user" action="regServlet" method="post">


								<div class="form-group row">
									<div class="col-sm-6 mb-3 mb-sm-0">
										<input type="email" class="form-control form-control-user"
											id="mail" placeholder="계정입력(회사이메일)">

									</div>

									<div class="col-sm-3 mb-3 mb-sm-0">
										<input class="btn btn-primary btn-user btn-block"
											type="submit" value="인증코드발송" name="reg" id="reg" />
									</div>
								</div>

								<div class="form-group row">
									<div class="col-sm-6 mb-3 mb-sm-0">
										<input type="email" class="form-control form-control-user"
											id="mail" placeholder="인증코드 입력">

									</div>

									<div class="col-sm-3 mb-3 mb-sm-0">
										<input class="btn btn-primary btn-user btn-block"
											type="submit" value="인증코드확인" name="reg" id="reg" />
									</div>
								</div>

								<div class="form-group row">
									<div class="col-sm-6 mb-3 mb-sm-0">
										<input type="password" class="form-control form-control-user"
											id="pw1" name="pw1" placeholder="비밀번호 입력"
											onkeyup="passwordCheckFunction();">
									</div>
									<div class="col-sm-6">
										<input type="password" class="form-control form-control-user"
											id="pw2" name="pw2" placeholder="비밀번호 재입력"
											onkeyup="passwordCheckFunction();">
									</div>
									
								</div>
								<div class="form-group" id="statusMessagepw"></div>




								

								<div class="form-group row">
									<div class="col-sm-6 mb-3 mb-sm-0">
										<input type="text" class="form-control form-control-user"
											id="name" placeholder="이름">
									</div>

								</div>
								
								<input
											class="btn btn-primary btn-user btn-block   color-change-2x"
											type="submit" value="회원가입" name="reg" id="reg" />
								 
							</form>
							<hr>
							<div class="text-center">
								<a class="small" href="#">비밀번호를 잊으셨습니까?</a>
							</div>
							<div class="text-center">
								<a class="small" href="login.jsp">로그인 페이지로 이동</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>

</body>

</html>