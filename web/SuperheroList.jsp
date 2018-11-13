<%--
  Created by IntelliJ IDEA.
  User: Symrak
  Date: 12.11.2018
  Time: 2:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Superhero Store Application</title>
</head>
<body>
<center>
    <h1>Superheroes Management</h1>
    <h2>
        <a href="/">Add New Superhero</a>
        &nbsp;&nbsp;&nbsp;
        <a href="/list">List All Superhero</a>

    </h2>
</center>
<div align="center">
    <table border="1" cellpadding="5">

        <caption><h2>List of Superheroes</h2></caption>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Universe</th>
            <th>Power</th>
            <th>Description</th>
            <th>Alive</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="superhero" items="${listSuperhero}">
            <tr>
                <td><c:out value="${superhero.id}" /></td>
                <td><c:out value="${superhero.name}" /></td>
                <td><c:out value="${superhero.universe}" /></td>
                <td><c:out value="${superhero.power}" /></td>
                <td><c:out value="${superhero.description}" /></td>
                <td><c:out value="${superhero.alive}" /></td>
                <td>
                    <a href="/edit?id=<c:out value='${superhero.id}' />">Edit</a>
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <a href="/delete?id=<c:out value='${superhero.id}' />">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>