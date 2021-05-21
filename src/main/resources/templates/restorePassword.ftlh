<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Poller - восстановление пароля</title>
    <style>
        .div-center {position: absolute; top: 40%; left: 50%; margin: -55px 0 0 -85px; width: 170px}
    </style>
</head>
<body>
<h2 style="text-align: center">Страница восстановления пароля</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/login">Авторизация</a> ||
    <a href="/registration">Регистрация</a>
</h4>
<div class="div-center">
    <#if message??><h4 style="color: darkred; text-align: center">${message}</h4></#if>
    <form action="/restore" method="post">
        <div style="margin-bottom: 7px"><input type="text" name="username" placeholder="Логин" style="width: 160px" required/></div>
        <div style="margin-bottom: 7px"><input type="text" name="secret" placeholder="Секретное слово" style="width: 160px" required></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Восстановить" style="width: 168px"/></div>
    </form>
</div>
</body>
</html>