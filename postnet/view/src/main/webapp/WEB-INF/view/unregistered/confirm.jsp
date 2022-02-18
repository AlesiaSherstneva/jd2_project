<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>ВОтделении</title>
        <link rel="stylesheet"
            href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <style>
            body {
                background: #00BFFF url(${pageContext.request.contextPath}/resources/image/belpost.jpg);
                }
        </style>
    </head>
    <body>
        <div class="hello" style="margin-top: 200px; margin-left: 20px; color: #FFFFFF;font-family: Verdana, Arial, Helvetica, sans-serif;">
            <h2> Привет, ${user.name} ${user.surname}, </h2>
            <h2> ${user.userJob.role} </h2>
            <h2>из ОПС ${user.userJob.postoffice}! </h2>
            <h2>Поздравляю с регистрацией </h2>
            <h2>в социальной сети "ВОтделении"!</h2>
        </div>
        <div class="confirm" style="margin-top: 50px; margin-left: 20px;">
            <a href="${pageContext.request.contextPath}"
                class="btn btn-primary" role="button" aria-pressed="true">На главную страницу</a>
        </div>
    </body>
</html>