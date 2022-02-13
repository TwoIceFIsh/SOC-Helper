<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
Date nowTime = new Date();
SimpleDateFormat sf = new SimpleDateFormat("yy.MM.dd");
%>
<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<div class="row ">

	<!-- Earnings (Monthly) Card Example -->
	<div class=" col-md-6 mb-4">
		<div class="card border-left-primary shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold text-primary text-uppercase mb-1">
							오늘은</div>
						<div class="h4 mb-0 font-weight-bold text-gray-800"><%=sf.format(nowTime)%></div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class=" col-md-6 mb-4">
		<div class="card border-left-success shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold text-success text-uppercase mb-1">
							누적 데이터 처리건수(실시간)</div>
						<div class="row h4 mb-0 font-weight-bold text-gray-800">
							<div id="statusMessage2"
								class="h4 mb-0 font-weight-bold text-gray-800"></div>
							건
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- Earnings (Monthly) Card Example -->
	<div class=" col-md-6 mb-4">
		<div class="card border-left-info shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold text-info text-uppercase mb-1">
							처리상태(실시간)</div>
						<div class=" no-gutters align-items-center">
							<div class="col-auto">

								<div id="statusMessage5"
									class="h5 mb-0 mr-3 font-weight-bold text-gray-800">보안정보
									: 🛠️</div>
								<div id="statusMessage3"
									class="h5 mb-0 mr-3 font-weight-bold text-gray-800"></div>


								<div id="statusMessage4"
									class="h5 mb-0 mr-3 font-weight-bold text-gray-800"></div>

								<div id="nokori"
									class="h8 mb-0 mr-3 font-weight-bold text-gray-800"></div>

							</div>

						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- Pending Requests Card Example -->
	<div class=" col-md-6 mb-4">
		<div class="card border-left-warning shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold text-warning text-uppercase mb-1">
							누적 메일발송건(실시간)</div>
						<div class="row h4 mb-0 font-weight-bold text-gray-800">
							<div id="statusMessage"
								class="h4 mb-0 font-weight-bold text-gray-800"></div>
							건
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<!-- Pending Requests Card Example -->
	<div class=" col-md-6 mb-4">
		<div class="card border-left-secondary shadow h-100 py-2">
			<div class="card-body">
				<div class="row no-gutters align-items-center">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold text-warning text-uppercase mb-1">
							메일수신대상</div>
						<div id="statusMessage6"
							class="h4 mb-0 font-weight-bold text-gray-800"></div>

						<div id="aas" class="text-xs mb-0 font-weight-bold text-gray-800">업로드
							버튼 클릭 전 메일 수신자를 선택해주세요!</div>
					</div>

				</div>
			</div>
		</div>
	</div>

	<div class=" col-md-6 dropdown mb-4">
		<button class="btn btn-primary dropdown-toggle" type="button"
			id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true"
			aria-expanded="false">메일수신변경</button>
		<div class="dropdown-menu animated--fade-in"
			aria-labelledby="dropdownMenuButton">
			<a id="a" class="dropdown-item" href="#">보안관제팀👍</a> <a id="b"
				class="dropdown-item" href="#">부장님😁</a> <a id="c"
				class="dropdown-item" href="#">승환😎</a> <a id="d"
				class="dropdown-item" href="#">명훈😊</a> <a id="e"
				class="dropdown-item" href="#">병호🤑</a> <a id="f"
				class="dropdown-item" href="#">성민😴</a> <a id="g"
				class="dropdown-item" href="#">예지😚</a> <a id="h"
				class="dropdown-item" href="#">형욱😡</a>

		</div>
	</div>

	<!-- 로그 -->
	<div class=" col-md-12 mb-8">
		<div class="card border-left-success shadow h-100 py-2">
			<div class="card-body">
				<div class=" no-gutters align-items-center">
					<div class="col-auto">
						<div
							class="text-xs font-weight-bold  text-uppercase mb-1">
							명령 처리 로그</div>
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


</div>

<!-- Content Row -->