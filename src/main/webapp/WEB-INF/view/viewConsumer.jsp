<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<body>
<h2>Consumers:</h2>
<table>
    <tr>
        <th>
            id
        </th>
        <th>
            Name
        </th>
    </tr>
    <c:forEach var="consumer" items="${consumers}">
        <c:url var ="updateButton" value="/updateConsumer">
            <c:param name="consumerId" value="${consumer.id}"></c:param>
        </c:url>
        <c:url var ="deleteButton" value="/deleteConsumer">
            <c:param name="consumerId" value="${consumer.id}"></c:param>
        </c:url>
        <c:url var ="showProductButton" value="/showProductByConsumer">
            <c:param name="consumerId" value="${consumer.id}"></c:param>
        </c:url>
        <c:url var ="addProductToCart" value="/addProductToCart">
            <c:param name="consumerId" value="${consumer.id}"></c:param>
        </c:url>
        <tr>
            <td>${consumer.id}</td>
            <td>${consumer.name}</td>
            <td>
                <input type="button" value="Update" onclick="window.location.href = '${updateButton}'">
            </td>
            <td>
                <input type="button" value="Delete" onclick="window.location.href = '${deleteButton}'">
            </td>
            <td>
                <input type="button" value="showProducts" onclick="window.location.href = '${showProductButton}'">
            </td>
            <td>
                <input type="button" value="addProduct" onclick="window.location.href = '${addProductToCart}'">
            </td>

        </tr>
    </c:forEach>
</table>
<br><br>
<input type="button" value="addConsumer" onclick="window.location.href = 'addConsumer'"/>
<br><br>
<a href="/showProducts">Products</a>

</body>
</html>