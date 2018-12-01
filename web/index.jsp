<!doctype html>
<html lang="en">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <style>
        <%@include file="style.css"%>
    </style>

    <script type="text/javascript">
        <%@include file="validity.js"%>
    </script>

</head>
<body>

<center>
    <title>Superhero Store Application</title>
    <h1>Superheroes Management</h1>
    <h2>
        <a href="/new">Add New Superhero</a>
        <a href="/list">List All Superheroes</a>
    </h2>
</center>

<div class="container">
    <form action="insert" method="post">
        <h1>Registration Superhero</h1>

        <label for="name">
            <span>Name</span>

            <input type="text" id="name" required name="name" size="45"
                   value="<c:out value='${superhero.name}'  />"
            />

            <ul class="input-requirements">
                <li>At least 3 characters long</li>
                <li>Must only contain letters and numbers</li>
            </ul>
        </label>

        <label for="universe">
            <span>Universe</span>

            <input type="text"  id="universe" required name="universe" size="45"
                   value="<c:out value='${superhero.universe}'  />"
            />

            <ul class="input-requirements">
                <li>Marvel or Dc universe</li>
            </ul>
        </label>

        <label for="power">
            <span>Power</span>

            <input type="number"  id="power" required name="power" min="0" max="100" size="45"
                   value="<c:out value='${superhero.power}'  />"
            />

            <ul class="input-requirements">
                <li>Power from 0 to 100</li>
            </ul>
        </label>

        <label for="description">
            <span>Description</span>

            <input type="text"  id="description" required name="description" size="45"
                   value="<c:out value='${superhero.description}'  />"
            />

            <ul class="input-requirements">
                <li>Сan't be empty</li>
            </ul>
        </label>

        <label for="alive">
            <span>Alive</span>

            <input type="text"  id="alive" required name="alive" size="45"
                   value="<c:out value='${superhero.alive}'  />"
            />

            <ul class="input-requirements">
                <li>True or false</li>
            </ul>
        </label>

        <button id="genAddress" type="button" class="btn btn-default btn-lg active">
            Export
        </button>

        <tr>
            <td colspan="2" align="center">
                <input type="submit" value="Save"/>
            </td>
        </tr>

        <br>

    </form>
</div>

<script type="text/javascript">
    <%@include file="validity.js"%>
</script>

</body>
</html>










