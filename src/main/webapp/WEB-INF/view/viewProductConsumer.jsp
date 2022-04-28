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
        <c:forEach var="product" items="${products}">

        <td>${product.id} </td>
        <td>${product.title} </td>
        <td>${product.price} </td>

    </c:forEach>
    <c:forEach var="count" items="${countProduct}">

            <td>${count}</td>

    </c:forEach>
    </tr>
</table>
<br><br>
</body>
</html>