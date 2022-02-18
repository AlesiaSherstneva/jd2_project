<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <title>ВОтделении</title>
        <link rel="stylesheet" type="text/css"
            href="${pageContext.request.contextPath}/resources/css/user-profile.css"/>
    </head>
    <body>
        <c:set var="gender" value="${user.gender}"/>
        <c:choose>
            <c:when test="${gender == 'женский'}">
                <div class="woman-postcard" accept-charset="UTF-8" modelAttribute="user">

                    <h1>${user.name} ${user.surname}</h1>
                    <h2><i>${user.userJob.postoffice}, ${user.userJob.role}</i></h1>

                    <hr/>
                    <h3><b>Дата рождения:</b> ${user.userDetails.birthday}</h3>
                    <h3><b>О себе:</b> ${user.userDetails.about}</h3>
                    <h3><b>Хобби:</b> ${user.userDetails.hobby}</h3>

                    <div style="margin-bottom: 5px;">
                        <a href="${pageContext.request.contextPath}/edit-1"
                            class="add-button">Редактировать ФИО</a>
                    </div>
                    <div style="margin-top: 5px; margin-bottom: 5px;">
                        <a href="${pageContext.request.contextPath}/edit-2"
                            class="add-button">Редактировать место работы</a>
                    </div>
                    <div style="margin-top: 5px; margin-bottom: 15px;">
                        <a href="${pageContext.request.contextPath}/edit-3"
                            class="add-button">Редактировать личные данные</a>
                    </div>
                    <div style="margin-top: 15px; margin-bottom: 10px;">
                        <form:form action="${pageContext.request.contextPath}/users/1" method="GET" class="search">
                            <input type="text" name="searchString" placeholder="Введи фамилию или номер ОПС" class="input" />
                            <input type="submit" name="" value="" class="submit" />
                        </form:form>
                    </div>
                    <form:form accept-charset="UTF-8"
                        action="${pageContext.request.contextPath}/logout" method="POST">
                        <input type="submit" value="Выход из аккаунта" class="add-button"/>
                    </form:form>
                </div>
            </c:when>
            <c:otherwise>
                <div class="man-postcard" accept-charset="UTF-8" modelAttribute="user">

                    <h1>${user.name} ${user.surname}</h1>
                    <h2><i>${user.userJob.postoffice}, ${user.userJob.role}</i></h1>

                    <hr/>
                    <h2><b>Дата рождения:</b> ${user.userDetails.birthday}</h2>
                    <h3><b>О себе:</b> ${user.userDetails.about}</h3>
                    <h3><b>Хобби:</b> ${user.userDetails.hobby}</h3>

                    <div style="margin-bottom: 5px;">
                        <a href="${pageContext.request.contextPath}/edit-1"
                        class="add-button">Редактировать ФИО</a>
                    </div>
                    <div style="margin-top: 5px; margin-bottom: 5px;">
                        <a href="${pageContext.request.contextPath}/edit-2"
                            class="add-button">Редактировать место работы</a>
                    </div>
                    <div style="margin-top: 5px; margin-bottom: 15px;">
                        <a href="${pageContext.request.contextPath}/edit-3"
                            class="add-button">Редактировать личные данные</a>
                    </div>
                    <div style="margin-top: 15px; margin-bottom: 10px;">
                        <form:form action="${pageContext.request.contextPath}/users/1" method="GET" class="search">
                            <input type="text" name="searchString" placeholder="Введи фамилию или номер ОПС" class="input" />
                            <input type="submit" name="" value="" class="submit" />
                        </form:form>
                    </div>
                    <form:form accept-charset="UTF-8"
                        action="${pageContext.request.contextPath}/logout" method="POST">
                        <input type="submit" value="Выход из аккаунта" class="add-button"/>
                    </form:form>
                </div>
            </c:otherwise>
        </c:choose>
    </body>
</html>