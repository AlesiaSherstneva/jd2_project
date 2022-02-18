<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="unreg-header.jsp"/>
    <form:form class="postcard" accept-charset="UTF-8"
        action="${pageContext.request.contextPath}/register-step-2"
        modelAttribute="user" method="POST">

        <h3>Публичный профиль - Шаг 1</h3>

        Имя:<br>
        <form:input path="name" placeholder="Введи имя"/><br>
        <form:errors path="name" cssClass="error"/>
        <br>

        Фамилия:<br>
        <form:input path="surname" placeholder="Введи фамилию"/><br>
        <form:errors path="surname" cssClass="error"/>
        <br>

        Пол:<br>
        Мужчина <form:radiobutton path="gender" value="мужской"/><br>
        Женщина <form:radiobutton path="gender" value="женский"/><br>
        <form:errors path="gender" cssClass="error"/>
        <br>

        Электронная почта:<br>
        <form:input path="email" placeholder="Введи e-mail"/><br>
        <form:errors path="email" cssClass="error"/>
        <br>

        Пароль:<br>
        <i>Должен состоять не менее, чем из 8 символов, содержать как минимум одну прописную букву,
        одну строчную букву и одну цифру </i><br>
        <form:input path="password" type="password" id="pass" placeholder="Придумай пароль"/>
        <div style="margin-bottom: 5px">
            <input type="checkbox" onclick="show()"/>Показать пароль
                <script type="text/javascript">
                    function show() {
                        var x = document.getElementById("pass");
                        if (x.type === "password") {
                        x.type = "text";
                        } else {
                        x.type = "password";
                        }
                    }
                </script>
            </div>
        <form:errors path="password" cssClass="error"/>
        <br><br>

        <input type="submit" value="Подтвердить" class="add-button"/>
    </form:form>
<jsp:include page="unreg-footer.jsp"/>