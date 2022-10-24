<%@ page contentType="text/html;charset=utf-8" %>

<jsp:include page="../header.jsp"/>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<div class="hello">
    <h2> Привет, ${user.name} ${user.surname},<br><br>${user.userJob.role}<br><br>
        из ОПС ${user.userJob.postoffice}!<br><br>Поздравляю с регистрацией<br><br>
        в социальной сети "ВОтделении"!</h2>
</div>
<div class="confirm" style="margin-top: 50px; margin-left: 40px;">
    <a href="${pageContext.request.contextPath}"
       class="btn btn-primary" role="button" aria-pressed="true">На главную страницу</a>
</div>
<jsp:include page="../footer.jsp"/>