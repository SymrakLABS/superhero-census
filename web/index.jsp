<%--
  Created by IntelliJ IDEA.
  User: Symrak
  Date: 11.11.2018
  Time: 22:26
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
        <a href="/new">Add New Superhero</a>
        &nbsp;&nbsp;&nbsp;
        <a href="/list">List All Superheroes</a>

    </h2>
</center>
<div align="center">
    <c:if test="${superhero != null}">
    <form action="update" method="post">
        </c:if>
        <c:if test="${superhero == null}">
        <form action="insert" method="post">
            </c:if>
            <table border="1" cellpadding="5">
                <caption>
                    <h2>
                        <c:if test="${superhero != null}">
                            Edit Superhero
                        </c:if>
                        <c:if test="${superhero == null}">
                            Add New Superhero
                        </c:if>
                    </h2>
                </caption>
                <c:if test="${superhero != null}">
                    <input type="hidden" name="id" value="<c:out value='${superhero.id}' />"/>
                </c:if>
                <tr>
                    <th>Name:</th>
                    <td>
                        <input type="text" name="name" size="45"
                               value="<c:out value='${superhero.name}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Universe:</th>
                    <td>
                        <input type="text" name="universe" size="45"
                               value="<c:out value='${superhero.universe}' />"
                        />
                    </td>
                </tr>
                <tr>
                    <th>Power:</th>
                    <td>
                        <input type="text" name="power" size="5"
                               value="<c:out value='${superhero.power}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th>Description:</th>
                    <td>
                        <input type="text" name="description" size="45"
                               value="<c:out value='${superhero.description}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <th>Alive:</th>
                    <td>
                        <input type="text" name="alive" size="5"
                               value="<c:out value='${superhero.alive}' />"
                        />
                    </td>
                </tr>

                <tr>
                    <td colspan="2" align="center">
                        <input type="submit" value="Save"/>
                    </td>
                </tr>
            </table>
        </form>
</div>
</body>
</html>
