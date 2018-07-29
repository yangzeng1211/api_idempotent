<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/addUserForPage" method="post">
    <input type="hidden" id="token" name="token" value="${token}">
        name: <input id="name" name="name" />
    <p>
        age:  <input id="age" name="age" />
    <p>
        <input type="submit" value="submit" />
</form>
</body>
</html>