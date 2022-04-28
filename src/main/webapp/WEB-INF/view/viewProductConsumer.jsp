<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<body>
<h2>Products:</h2>
<p>Consumer: ${nameConsumer}</p>
<table>
    <tr>
        <th>
            id
        </th>
        <th>
            Title
        </th>
        <th>
            Price
        </th>
        <th>
            Count
        </th>
    </tr>
    <tr>
        <c:forEach var="entry" items="${products}">
    <tr>
        <td>${entry.key.id} </td>
        <td>${entry.key.title} </td>
        <td>${entry.key.price} </td>
        <td>${entry.value} </td>
    </tr>
    </c:forEach>
    </tr>
</table>
<br><br>
</body>
</html>