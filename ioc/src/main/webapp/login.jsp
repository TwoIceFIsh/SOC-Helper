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

<title>SOC Helper</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<link rel="favicon" href="/images/favicon.ico">
<!-- Custom styles for this template-->
<link href="css/sb-admin-2.css?after" rel="stylesheet">
<link href="css/my.css" rel="stylesheet">
</head>


<body class="login-bg">

	<div class="container fade-in">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl-10 col-lg-12 col-md-9">

				<div class="card o-hidden border-0 shadow-lg my-5 badge-black1">
					<div class="card-body p-0">
						<!-- Nested Row within Card Body -->
						<div class="row ">
							<div class="col-lg-6 d-none d-lg-block bg-login-image"></div>
							<div class="col-lg-6 tracking-in-expand">
								<img class="text-center" src="images/ms-icon-144x144.png"
									alt="My Image">
								<div class="p-4">
									<div class="title2 badge-black1 text-center"
										data-content="SOC Helper">SOC Helper</div>

									<div class="text-center h6 mb-4">ID: 개인메일 / PW: P! (문의:
										병호)</div>
									<form class="user " action="loginServlet" method="post">
										<div class="form-group">
											<input type="email" class="form-control form-control-user"
												id="id" name="id" aria-describedby="emailHelp"
												placeholder="XXXX@s-oil.com">
										</div>
										<div class="form-group  ">
											<input type="password" class="form-control form-control-user"
												id="pw" name="pw" placeholder="********">
										</div>
										<div class="form-group">
											<div class="custom-control custom-checkbox small">
												<input type="checkbox" class="custom-control-input"
													id="customCheck"> <label
													class="custom-control-label" for="customCheck">Remember
													Me</label>
											</div>
										</div>

										<input
											class="btn btn-primary btn-user btn-block   color-change-2x"
											type="submit" value="login" name="login" id="login" />
									</form>
									<hr>
									<div class="text-center">
										<a class="small" href="reg.jsp">계정생성</a>
									</div>

									<div class="text-center">
										<a class="small" href="#">비밀번호 찾기</a>
									</div>
									<hr>
									<div class="text-center">
										<a class="small" href="https://twoicefish-secu.tistory.com/">개발자
											블로그 방문</a>
									</div>
									<hr>
									<div class="text-center small">Copyright 2022. 이병호 all
										rights reserved.</div>
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