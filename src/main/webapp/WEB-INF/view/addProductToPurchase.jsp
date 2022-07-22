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
        <c:url var ="addProduct" value="/saveProductToPurchase">
            <c:param name="productId" value="${product.id}"></c:param>
            <c:param name="consumerId" value="${consumerId}"></c:param>
        </c:url>
        <tr>
            <td>${product.id}</td>
            <td>${product.title}</td>
            <td>${product.price}</td>
            <td>
                <input type="button" value="addProductToPurchase" onclick="window.location.href = '${addProduct}'">
            </td>

        </tr>
    </c:forEach>
</table>
<br><br>
<a href="/">Consumers</a>

</body>
</html>