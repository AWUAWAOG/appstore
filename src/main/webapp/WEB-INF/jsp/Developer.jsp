<%@ page import="com.apps.domain.Developer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Developer developerJsp = (Developer) request.getAttribute("developer");%>
<html>
<head>
    <title>DEVELOPER INFO</title>
</head>
<body>
<h1>DEVELOPER'S INFO</h1>
<h3><%=developerJsp.getId()%></h3>
<h3><%=developerJsp.getFirst_name()%></h3>
<h3><%=developerJsp.getLast_name()%></h3>
<h3><%=developerJsp.getAge()%></h3>
<h3><%=developerJsp.getBirth_date()%></h3>
</body>
</html>
