<!DOCTYPE html>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<body>

Add document:
<form method="post" action="${pageContext.request.contextPath}/addDocument">
    Key:
    <input type="text" name="key" value="key">
    <br>
    Value:
    <input type="text" name="value" value="value">
    <br>
    <input type="submit" value="Submit">
</form>

<br>

Get document:
<form method="get" action="${pageContext.request.contextPath}/getDocument">
    Key:
    <input type="text" name="key" value="key">
    <br>
    <input type="submit" value="Submit">
</form>

<br>

Find documents:
<form method="get" action="${pageContext.request.contextPath}/findDocuments">
    Query:
    <input type="text" name="key" value="key">
    <br>
    <input type="submit" value="Submit">
</form>

</body>
</html>