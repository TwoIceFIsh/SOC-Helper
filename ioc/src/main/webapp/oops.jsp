<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link href="css/my.css" rel="stylesheet">
<link href="scss/my.scss" rel="stylesheet">

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
<link rel="favicon" href="http://www.kokonut.today:8080/images/favicon.ico">
<!-- Custom styles for this template-->
<link href="css/sb-admin-2.css?after" rel="stylesheet">

</head>



<body class="bg-main-image text-blur-out">

	<div class="container ">

		<%
		String value = (String) session.getAttribute("ok");
		if (value != null) {
		%>

		<!-- Outer Row -->
		<div class="row justify-content-center slide-in-blurred-top" id="big2"
			name="big2">

			<div class="col-xl-12 col-lg-14 col-md-10">

				<div class="card o-hidden border-0 shadow-lg my-5">

					<div class="card-body p-0">
						<div class="card shadow mb-4">
							<div class=" card-header py-3   text-center m-0 font-weight-bold">
								SOC Helper</div>






							<div class="text-right">
								<%=value%>님 환영합니다. <a class=" " href="logout.jsp">(로그아웃)</a>
							</div>

						</div>
						<!-- Nested Row within Card Body -->
						<div class="row">

							<div
								class="slit-in-vertical col-lg-6 d-none d-lg-block   badge-black1">
								<div class="p-2"><jsp:include page="status.jsp" /></div>
							</div>

							<div class=" tilt-in-fwd-tr col-lg-6 ">
								<div class="p-3 text-left">


									<h1 class="h4 text-gray-900 mb-2">500</h1>
									<p class="mb-5">오우 이런!!</p>
								</div>
								<div>
									</br> 확인해주세요 </br>1. 허용 파일확장자 사용(txt)</br>2. 파일내용 한글 포함 금지 </br>3. 파일이름 한글 포함 금지</br>4.
									업로드 시 파일 선택 필수</br>5. 파일내용확인(CVE/IOC) </br> </br>기타문의사항은 병호에게 문의주세요!</br>.</br> <a
										href="javascript:history.back()"
										class="btn btn-primary btn-user btn-block">돌아가기</a>



									<hr>
									<hr>
								</div>


							</div>




						</div>

					</div>
				</div>
			</div>
		</div>

		<%
		} else {
		response.sendRedirect("/2/login.jsp");

		}
		%>
	</div>

	</div>

	</div>

	</div>

	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="js/common2.js" charset="UTF-8"></script>
	<script src="js/my.js" charset="UTF-8"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>

</body>
