# Выпускной проект "Социальная сеть"
<p>Требования к выпускному проекту для выпускников IT-Academy, претендующих на сертификат, можно увидеть в документах 
"JD2 - дипломный проект.pdf" и "Проект Социальная сеть.pdf"</p>
<p>Социальная сеть "ВОтделении" предназначена для работников отделений связи производства "Минская почта" (специалистов 
по почтовой деятельности, операторов и почтальонов), то есть для всех не руководящих работников минских почт.
Так как времени на написание было мало, фактически получилась соцсеть с функциями "на людей посмотреть 
да себя показать" :-) По возможности, при наличии свободного времени, буду дорабатывать сервис. В планах добавление 
мессенджера и форума для общения.</p>
Реализованные фичи:
<ul>
    <li>Незарегистрированный пользователь:
        <ul>
            <li>Зарегистрироваться в социальной сети</li>
            <li>Создать публичный и приватный профиль</li>
            <li>Видеть публичную информацию зарегистрированных пользователей</li>
            <li>Искать публичную информацию зарегистрированных пользователей</li>
        </ul>
    </li>
    <li>Зарегистрированный пользователь:
        <ul>
            <li>Видеть публичную и приватную информацию зарегистрированных пользователей</li>
            <li>Искать публичную и приватную информацию зарегистрированных пользователей</li>
            <li>Редактировать публичный и приватный профиль</li>
        </ul>
    </li>
    <li>Администратор:
        <ul>
            <li>Видеть публичную и приватную информацию пользователей</li>
            <li>Искать публичную и приватную информацию пользователей</li>
            <li>Активировать и деактивировать учётные записи пользователей</li>
            <li>Удалять учётные записи пользователей</li>
        </ul>
    </li>
</ul>
<p>Приложение устанавливается на Tomcat-9, разворачивается в контекстном пути http://localhost:8080/postnet</p>
<p>Использованные технологии: Java 11, Maven, Tomcat, Liquibase, Hibernate, JDBC, Spring Core, Spring MVC, Spring Security,
MySQL, Lombok, немного HTML, CSS и JS.</p>

![img.png](img.png)
![img_1.png](img_1.png)
![img_2.png](img_2.png)
![img_3.png](img_3.png)
![img_4.png](img_4.png)
![img_7.png](img_7.png)
![img_8.png](img_8.png)
![img_9.png](img_9.png)