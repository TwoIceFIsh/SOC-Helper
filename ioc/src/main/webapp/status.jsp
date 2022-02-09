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
						<div id="statusMessage2"
							class="h4 mb-0 font-weight-bold text-gray-800"></div>
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
								<div id="statusMessage3"
									class="h5 mb-0 mr-3 font-weight-bold text-gray-800"></div>
								<div id="statusMessage4"
									class="h5 mb-0 mr-3 font-weight-bold text-gray-800"></div>
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
						<div id="statusMessage"
							class="h4 mb-0 font-weight-bold text-gray-800"></div>
					</div>

				</div>
			</div>
		</div>
	</div>
</div>

<!-- Content Row -->