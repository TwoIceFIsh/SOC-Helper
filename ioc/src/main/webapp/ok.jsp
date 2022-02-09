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

<title>CVE 정보수집 솔루션</title>

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.css?after" rel="stylesheet">

</head>



<body class="bg-gradient-success">

	<div class="container">

		<!-- Outer Row -->
		<div class="row justify-content-center">

			<div class="col-xl-12 col-lg-14 col-md-10">

				<div class="card o-hidden border-0 shadow-lg my-5">
					<div class="card-body p-0">
						<div class="card shadow mb-4">
							<div class="card-header py-3">
								<h6 class="text-center m-0 font-weight-bold text-primary">보안관제팀
									업무지원 통합 플랫폼💕 by 병호</h6>
							</div>

						</div>
						<!-- Nested Row within Card Body -->
						<div class="row">
							<div class="bg-password-imagea col-lg-6 d-none d-lg-block ">
								<div class="p-2"><jsp:include page="status.jsp" /></div>
							</div>
							<div class="col-lg-6">
								<div class="p-3">
									<div class="text-center">
										<h1 class="h4 text-gray-900 mb-2">요청 완료!</h1>
										<p class="mb-5">질하셨어요!</p>
									</div>
									<div>
										</br> 10분 이내로 메일을 보내드릴게요!</br>(md5 변환시 건당 15초 소요)</br> 발송대상 : 보안관제팀</br> </br>추가가 필요하면
										병호에게 문의주세요!</br>.</br> <a href="javascript:history.back()"
											class="btn btn-primary btn-user btn-block">돌아가기</a>



										<hr>

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
		<script src="js/common.js"></script>
		<!-- Core plugin JavaScript-->
		<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

		<!-- Custom scripts for all pages-->
		<script src="js/sb-admin-2.min.js"></script>
</body>