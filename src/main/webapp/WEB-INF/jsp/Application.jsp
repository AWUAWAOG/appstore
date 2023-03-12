<%@ page import="com.apps.domain.Application" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Application applicationJsp = (Application) request.getAttribute("application");%>
<html>
<head>
    <title>APPLICATION INFO</title>
</head>
<body>
<h1>APPLICATION'S INFO</h1>
<h3><%=applicationJsp.getId()%></h3>
<h3><%=applicationJsp.getApp_name()%></h3>
<h3><%=applicationJsp.getApp_category()%></h3>
<h3><%=applicationJsp.getDescription()%></h3>
<h3><%=applicationJsp.getRating()%></h3>
<h3><%=applicationJsp.getRating()%></h3>
<h3><%=applicationJsp.getApp_year()%></h3>
</body>
</html>
