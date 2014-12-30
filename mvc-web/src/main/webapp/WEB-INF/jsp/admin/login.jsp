<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>登录</title>
</head>
<body>
<form method="post" action="${ctx}/public/login.html">
    <input type="text" name="userName"> <br/>
    <input type="password" name="password"> <br/>
    <input type="submit" value="提交">
</form>

</body>
</html>