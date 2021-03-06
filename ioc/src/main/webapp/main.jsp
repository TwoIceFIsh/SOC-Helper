<%@page import="user.userDTO"%>
<%@page import="user.userDAO"%>
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
<link rel="favicon"
	href="http://www.kokonut.today:8080/images/favicon.ico">
<!-- Custom styles for this template-->
<link href="css/sb-admin-2.css?after" rel="stylesheet">

</head>



<body class="bg-main-image text-blur-out">

	<div class="container ">

		<%
		String value = (String) session.getAttribute("ok");

		userDTO user = new userDTO();

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


									<h1 class="h6 text-gray-900 mb-2">1. CVE 정보수집</h1>
									<p class="mb-4 h6">
										CVE 코드와 관련된 정보를 수집하여 보고서로 받아보세요!</br> </br> CVE로 시작하는 코드를 다음과 같은 텍스트 파일
										업로드!</br> </br> (sample.txt) </br> CVE-2020-1123 </br> CVE-2020-1231 </br>
										CVE-2020-11245
									<form class="  form-control-user" method="POST"
										action="FileHandleServlet" enctype="multipart/form-data">
										<input type="file" class="form-control form-control-user"
											name="file" id="file" /> <br /> <input
											class="btn btn-success btn-user btn-block color-change-2x"
											type="submit" value="txt 파일 업로드" name="upload" id="upload" />
									</form>


									<hr>

									<h1 class="h6   mb-2">2. HX 파일생성</h1>
									<p class="mb-4">
										정보를 올려 HX 파일(MD5/SHA256/SHA1/IP/URL)을 생성해요!</br>문자열 자동처리됨 hxxp
										-&gt; http / [.] -&gt; .</br> </br> (sample.txt)</br>
										hxxps://www.sdifjsod.com</br>111.222[.]111.222</br>md5</br>sha1</br>sha256
									<form class=" form-control-user" method="POST"
										action="FileHandleServlet2" enctype="multipart/form-data">
										<input type="file" class="form-control form-control-user"
											name="file" id="file" /> </br> <input
											class="btn btn-success btn-user btn-block color-change-2x"
											type="submit" value="txt 파일 업로드" name="upload" id="upload" />
									</form>
									<hr>

									<p class="mb-4"></p>
									<h1 class="h6 mb-2">굿굿잼 11기 채팅방</h1>
									<p class="mb-4"></p>

									<div id="selecter" style="height: 250px; overflow: auto;">
										<a name="messageA" id="messageA">A</a><br> <a
											name="messageB" id="messageB"></a>
									</div>




									<div class="row">
										<input class="col-9 form-control" type="text" name="sendM"
											id="sendM" placeholder="채팅 입력" onkeyup="enterkey2();"></input>
										<p class="mb-4"></p>
										<button type="submit" class="col-2 btn btn-primary mb-3"
											onclick="sendMessage();">전송</button>

									</div>
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
