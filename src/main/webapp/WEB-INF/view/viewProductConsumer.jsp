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
            Title
        </th>
        <th>
            Price
        </th>
        <th>
            Quantity
        </th>
    </tr>
    <tr>
        <c:forEach var="purchase" items="${purchases}">
    <tr>
        <td>${purchase.product.title} </td>
        <td>${purchase.price} </td>
        <td>${purchase.quantity} </td>
    </tr>
    </c:forEach>
    </tr>
</table>
<br><br>
</body>
</html>