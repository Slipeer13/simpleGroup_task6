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
            Quantity
        </th>
    </tr>
    <tr>
        <c:forEach var="order" items="${orders}">
    <tr>
        <td>${order.product.id} </td>
        <td>${order.product.title} </td>
        <td>${order.product.price} </td>
        <td>${order.quantity} </td>
    </tr>
    </c:forEach>
    </tr>
</table>
<br><br>
</body>
</html>