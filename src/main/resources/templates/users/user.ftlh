<!DOCTYPE html>
<html lang="ru">
<head>
    <title>POLLER - Страница пользователя</title>
    <style>
        body {
            margin: 20px
        }
    </style>
</head>
<body>
<h2 style="text-align: center">Страница пользователя ${user.get().getUsername()}</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/polls">Мои опросы</a> ||
    <a href="/user/edit">Изменить пароль</a>
</h4>
<ul>
    <li><b>ID:</b> ${user.get().getId()}</li>
    <li><b>Логин:</b> ${user.get().getUsername()}</li>
    <li><b>Роли:</b> <#list user.get().getRoles() as role>${role}<#sep>, </#sep></#list></li>
</ul>
</body>
</html>