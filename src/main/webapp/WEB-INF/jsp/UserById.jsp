<%@ page import="com.apps.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User userJsp = (User) request.getAttribute("user");%>
<html>
<head>
    <title>USER BY ID</title>
</head>
<body>
<h1>USER'S INFO</h1>
<h3><%=userJsp.getId()%></h3>
<h3><%=userJsp.getFirst_name()%></h3>
<h3><%=userJsp.getLast_name()%></h3>
<h3><%=userJsp.getUser_login()%></h3>
<h3><%=userJsp.getUser_password()%></h3>
<h3><%=userJsp.getEmail()%></h3>
<h3><%=userJsp.getCreated()%></h3>
<h3><%=userJsp.getEdited()%></h3>
</body>
</html>
