<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<body>
<h2>View consumers:</h2>
<p>Product: ${titleProduct}</p>
<table>
    <tr>
        <th>
            id
        </th>
        <th>
            Name
        </th>

    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.consumer.id}</td>
            <td>${order.consumer.name}</td>
        </tr>
    </c:forEach>
</table>
<br><br>
</body>
</html>