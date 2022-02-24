<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">



<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>보안관제 업무도우미</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.css?after" rel="stylesheet">

</head>


<body class="bg-gradient-primary">

	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl-10 col-lg-12 col-md-9">

				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
							<div class="col-lg-6">
								<div class="p-5">
									<div class="text-center">
										<h3 class="h6 text-gray-900 mb-4">보안관제 포털에 오신것을 환영합니다</h2>
										<h3 class="h4 text-gray-900 mb-4">현재 테스트 계정만 접근 가능합니다(문의: 병호)</h3>
									</div>
									<form class="user" action="loginServlet" method="post">
										<div class="form-group">
											<input type="email" class="form-control form-control-user"
												id="id" name="id" aria-describedby="emailHelp"
												placeholder="이메일을 입력해주세요">
										</div>
										<div class="form-group">
											<input type="password" class="form-control form-control-user"
												id="pw" name="pw" placeholder="비밀번호를 입력해주세요">
										</div>
										<div class="form-group">
											<div class="custom-control custom-checkbox small">
												<input type="checkbox" class="custom-control-input"
													id="customCheck"> <label
													class="custom-control-label" for="customCheck">Remember
													Me</label>
											</div>
										</div>

										<input class="btn btn-primary btn-user btn-block"
											type="submit" value="login"  name="login" id="login" />
									 
									</form>
									<hr>
									<div class="text-center">
										<a class="small" href="find.jsp">비밀번호 찾기</a>
									</div>
									<div class="text-center">
										<a class="small" href="reg.jsp">계정생성</a>
									</div>
								</div>
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