<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<body>
<h2>Add consumer</h2>
<form:form action="/saveOrUpdateConsumer" modelAttribute="consumer">
    <form:hidden path="id"/>
    <br><br>
    Name  <form:input path="name"/>
    <input type="submit">
</form:form>

</body>
</html>