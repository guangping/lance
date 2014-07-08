<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="resources/css/common.css" type="text/css">
    <script type="text/javascript" src="resources/jquery/jquery-1.3.2.js"></script>
    <script type="text/javascript" src="resources/js/common.js"></script>
</head>
<body>
<h1>测试js</h1>

<div id="content"></div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        Service.executeAsync('/main/message/json.form', null, function (reply) {
            alert(reply);
        })
    })
</script>