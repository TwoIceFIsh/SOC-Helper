<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>SOC Helper</title>
</head>
<body>
<%
    // 1: ������ ���� �����͸� ��� ����
    session.invalidate();
    // 2: �α��� �������� �̵���Ŵ.
    response.sendRedirect("/2/login.jsp");
%>
</body>
</html>