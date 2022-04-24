<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<body>
<h2>Products:</h2>
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
    </tr>
    <c:forEach var="product" items="${products}">
        <c:url var ="updateButton" value="/updateProduct">
            <c:param name="productId" value="${product.id}"></c:param>
        </c:url>
        <c:url var ="deleteButton" value="/deleteProduct">
            <c:param name="productId" value="${product.id}"></c:param>
        </c:url>
        <c:url var ="showConsumersButton" value="/showConsumersByProduct">
            <c:param name="productId" value="${product.id}"></c:param>
        </c:url>

        <tr>
            <td>${product.id}</td>
            <td>${product.title}</td>
            <td>${product.price}</td>
            <td>
                <input type="button" value="Update" onclick="window.location.href = '${updateButton}'">
            </td>
            <td>
                <input type="button" value="Delete" onclick="window.location.href = '${deleteButton}'">
            </td>
            <td>
                <input type="button" value="showConsumers" onclick="window.location.href = '${showConsumersButton}'">
            </td>


        </tr>
    </c:forEach>
</table>
<br><br>
<input type="button" value="addProduct" onclick="window.location.href = 'addProduct'"/>
<br><br>
<a href="/">Consumers</a>

</body>
</html>