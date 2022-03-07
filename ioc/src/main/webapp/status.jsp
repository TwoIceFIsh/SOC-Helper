<%@page import="org.apache.poi.util.SystemOutLogger"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.util.Date"%>

<%@ page import="java.text.SimpleDateFormat"%>

<%@ page import="site.siteDAO"%>

<%@ page import="site.siteDTO"%>

<%@ page import="java.util.ArrayList"%>

<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%
Date nowTime = new Date();

SimpleDateFormat sf = new SimpleDateFormat("yy.MM.dd");

String value = (String) session.getAttribute("ok");

ArrayList<siteDTO> site = new ArrayList<siteDTO>();

siteDAO siteF = new siteDAO();

site = siteF.getMail();

if (value == null) {

	response.sendRedirect("http://222.110.22.168:8080/ioc/login.jsp");

}
%><link rel="favicon" href="http://222.110.22.168:8080/ioc/images/favicon.ico">
<head>

<link href="css/my.css" rel="stylesheet">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<div class="row ">

	<div class=" col-md-5 mb-2">

		<div class="card border-left-primary h-60 py-3">

			<div class="card-body">

				<div class="row no-gutters align-items-left">

					<div class="col-auto">

						<div
							class="text-xs font-weight-bold text-success text-uppercase mb-1">

							오늘은</div>

						<div class="h3 mb-1 font-weight-bold text-gray-800"><%=sf.format(nowTime)%></div>


						</br>

						<div
							class="text-xs font-weight-bold text-success text-uppercase mb-1">

							누적 데이터 처리건수(실시간)</div>

						<div id="statusMessage2"
							class="h3 mb-1 font-weight-bold text-gray-800"></div>

						</br>

						<div
							class="text-xs font-weight-bold text-warning text-uppercase mb-1">

							누적 메일발송건(실시간)</div>

						<div id="statusMessage"
							class="h3 mb-1 font-weight-bold text-gray-800"></div>


					</div>


				</div>

			</div>

		</div>

	</div>


	<div class=" col-md-7 mb-2">

		<div class="card border-left-danger h-100 py-2">

			<div class="card-body">

				<div class="row no-gutters align-items-left">

					<div class="col-auto">

						<div
							class="text-xs font-weight-bold text-warning text-uppercase mb-1">

							메일수신대상</div>

						<div id="statusMessage6"
							class="h6 mb-1 font-weight-bold text-gray-800"></div>


						<div id="aas" class="text-xs mb-1 font-weight-bold text-gray-800">업로드

							버튼 클릭 전 메일 수신자를 선택해주세요!(추가별도문의)</div>


						<div class="dropdown mb-1 ">


							<button class="h6 btn btn-primary dropdown-toggle" type="button"
								id="dropdownMenuButton" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">메일수신선택</button>


							<div class="dropdown-menu animated--fade-in"
								aria-labelledby="dropdownMenuButton">


								<a id="a" class="dropdown-item" href="#">보안관제팀👍</a> <a id="b"
									class="dropdown-item" href="#">부장님😁</a> <a id="c"
									class="dropdown-item" href="#">승환😎</a> <a id="d"
									class="dropdown-item" href="#">명훈😊</a> <a id="e"
									class="dropdown-item" href="#">병호🤑</a> <a id="f"
									class="dropdown-item" href="#">성민😴</a> <a id="g"
									class="dropdown-item" href="#">예지😚</a> <a id="h"
									class="dropdown-item" href="#">형욱😡</a> <a id="j"
									class="dropdown-item" href="#">테스트🥰</a>

							</div>

						</div>


						<%-- <div class="dropdown mb-1 ">


							<button class="h6 btn btn-primary dropdown-toggle" type="button"
								id="dropdownMenuButtonQ" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">TEST BUTTONS</button>


							<div class="dropdown-menu animated--fade-in"
								aria-labelledby="dropdownMenuButtonQ">


								<%
								for (int i = 0; i < site.size(); i++) {
								%>

								<a id="A<%=i%>" name="A<%=i%>" class="dropdown-item" href="#" onclick="sample();"><%=site.get(i).getName()%></a>



								<script type="text/javascript"> 
								function sample(){
									var name = $('#A<%=i%>').val();
									
									var xx = document.getElementById("A<%=i%>");
									
									alert(xx);
								$.ajax({
									type: 'POST', url:
										'./mailCheckServlet2', data: { name: name }, success:
										function(result) { $('#statusMessage6').html(result); }
								});}
								
								$('#A<%=i%>').click(function() {
									var name = $('#A<%=i%>').val();
									alert(name)


									$.ajax({
										type: 'POST', url:
											'./mailCheckServlet2', data: { name: name }, success:
											function(result) { $('#statusMessage6').html(result); }
									});

								});
								</script>


								<%
								}
								%>


							</div>

						</div> --%>

					</div>


				</div>

			</div>


		</div>

	</div>


	<div class=" col-md-12 mb-2">

		<div class="card border-left-info  h-100 py-2">

			<div class="card-body">

				<div class="row no-gutters align-items-left">

					<div class="col-auto">

						<div
							class="text-xs font-weight-bold text-info text-uppercase mb-1">

							모듈 상태(실시간) (🟢정상 🟡작업 🔴다운)</div>

						<div
							class=" col-md-12  align-items-center font-weight-bold text-gray-800">


							<div class="col-md-12">

								<div class="row">


									<div id="moduleE" class="mb-0"></div>

									__Manager(+Recover)


									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;처리현황 :

									<div id="nokori" class="mb-0"></div>

									<div id="nokori2" class="mb-0"></div>

									<div id="nokori3" class="mb-0"></div>

								</div>


								<div class="row">


									<div id="moduleA" class="mb-0"></div>

									__CVE Module

								</div>

								<div class="row">


									<div id="moduleB" class="mb-0"></div>

									__IOC Module

								</div>

								<div class="row">


									<div id="moduleC" class="mb-0"></div>

									__JOIN Module

								</div>

								<div class="row">

									<div id="moduleD" class="mb-0"></div>

									__FIND Module

								</div>

							</div>



						</div>


					</div>

				</div>


			</div>

		</div>

	</div>



	<!-- Pending Requests Card Example -->



	<!-- 로그 -->

	<div class=" col-md-12 mb-2">

		<div class="card border-left-success shadow h-100 py-2">

			<div class="card-body">

				<div class=" no-gutters align-items-center">

					<div class="col-auto">

						<div class="text-xs font-weight-bold  text-uppercase mb-1">

							로그(실시간)</div>

						<div class=" h8 mb-0 font-weight-bold text-gray-800">

							<div id="statusMessage99"
								class="text-xs  font-weight-bold text-gray-800"></div>

							<div id="statusMessage100"
								class=" text-xs  font-weight-bold badge-danger"></div>


						</div>

					</div>


				</div>

			</div>

		</div>

	</div>


	<div class=" col-md-12 mb-4">

		<div class="card border-left-warning shadow h-100 py-2">

			<div class="card-body">

				<div class="row no-gutters align-items-center">

					<div class="col-auto">

						<div
							class="text-xs font-weight-bold text-warning text-uppercase mb-1">

							공지사항</div>

						<div class=" h8 mb-0 font-weight-bold text-gray-800">

							<div id="statusMessage11" class=" mb-1 font-weight-bold "></div>

							2022.02.07(월) : 솔루션 개발 시작</br> 2022.02.11(금) : 1차 개발 완료(CVE/IOC)</br>

							2022.02.14(월) : 컨트롤 패널 구현 완료</br> 2022.02.25(금) : 접근제어 시스템 구현

							완료(로그인/회원가입) </br> 2022.02.26(토) : 디자인 리뉴얼 및 모듈 복구시스템 구현 완료 </br> </br> 기능개선 및

							문의사항 : 병호🤑

						</div>

					</div>


				</div>

			</div>

		</div>

	</div>


</div>

<script src="js/my.js" charset="UTF-8"></script>

<!-- Content Row -->