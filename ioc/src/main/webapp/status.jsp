<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
Date nowTime = new Date();
SimpleDateFormat sf = new SimpleDateFormat("yy.MM.dd");
%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String value = (String) session.getAttribute("ok");
if (value == null) {
	response.sendRedirect("http://222.110.22.168:8080/ioc/login.jsp");

}
%><link rel="favicon" href="/images/favicon.ico">
<head><link href="css/my.css" rel="stylesheet">
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
							버튼 클릭 전 메일 수신자를 선택해주세요!</div>

						<div class="dropdown mb-1 ">

							<button class="h6 btn btn-primary dropdown-toggle" type="button"
								id="dropdownMenuButton" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">메일수신변경</button>
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
					</div>

				</div>
			</div>
		</div>
	</div>



	<!-- Earnings (Monthly) Card Example -->
	<div class=" col-md-5 mb-2">
		<div class="card border-left-info   h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-left">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold text-info text-uppercase mb-1">
							처리상태(실시간)</div>
						<div class=" no-gutters align-items-center">
							<div class="col-auto">

								<div id="statusMessage5"
									class="h6 mb-0 mr-3 font-weight-bold text-gray-800">보안정보
									: 🛠️</div>
								<div id="statusMessage3"
									class="h6 mb-0 mr-3 font-weight-bold text-gray-800"></div>


								<div id="statusMessage4"
									class="h6 mb-0 mr-3 font-weight-bold text-gray-800"></div>


								<div id="statusMessage5"
									class="h6 mb-0 mr-3 font-weight-bold text-gray-800">캡쳐 :
									🛠️</div>
								<div id="nokori"
									class="h6 mb-0 mr-3 font-weight-bold text-gray-800"></div>

							</div>

						</div>
					</div>

				</div>
			</div>
		</div>
	</div>


	<div class=" col-md-7 mb-2">
		<div class="card border-left-info  h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-left">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold text-info text-uppercase mb-1">
							작업 진행률 - (개발중🛠️)</div>
						<div class=" no-gutters align-items-left col-md-12">

							CVE 작업[1] 진척율
							<div class="progress mb-1">
								<div class="progress-bar" role="progressbar" style="width: 75%"
									aria-valuenow="75" aria-valuemin="0" aria-valuemax="100"></div>
							</div>


							IOC 작업[2] 진척율
							<div class="progress mb-1">
								<div class="progress-bar bg-gradient-danger" role="progressbar"
									style="width: 100%" aria-valuenow="100" aria-valuemin="0"
									aria-valuemax="100"></div>


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
							2022.02.11(금) : 메일 발송 대상 지정 기능 추가</br> 2022.02.12(토) : 남은 작업 시간 및 건 수
							추가</br> 2022.02.13(일) : 데이터 변환 알고리즘 개선</br> 2022.02.13(일) : 작업 콘솔 로그 기능 추가</br>
							2022.02.14(월) : 작업대기열 기능 추가</br>2022.02.15(화) : (알고리즘 개선 및 버그
							픽스) </br> 2022.02.17(목) : 1차 개발 완료(CVE/IOC)</br> 2022.02.25(금) : 접근제어 기능 구현 완료(로그인/회원가입) </br>2022.02.26(토) : 모듈 복구시스템 구현 </br> </br> 기능개선 및 문의사항 : 병호🤑
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

</div>

<!-- Content Row -->