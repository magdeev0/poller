<!DOCTYPE html>
<html lang="ru">
<head>
    <title>POLLER</title>
    <style>
        body {
            margin: 20px
        }
    </style>
</head>
<body>
<h2 style="text-align: center">Сервис по созданию опросов</h2>
<#if isAnonymous??>
    <h4 style="text-align: center">
        <a href="/registration">Регистрация</a> ||
        <a href="/login">Авторизация</a> ||
        <a href="/restore">Забыл пароль</a>
    </h4>
<#else>
    <ul>
        <li>Привет, ${user.get().getUsername()}!</li>
        <li><a href="/user">Моя страница</a></li>
        <li><a href="/polls">Мои опросы</a></li>
        <li><a href="/user/edit">Редактировать информацию</a></li>
        <li><a href="/user/uploadAvatar">Загрузить аватар</a></li>
        <li style="margin-top: 4px">
            <form action="/logout" method="post">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="submit" value="Выйти"/>
            </form>
        </li>
    </ul>
</#if>
</body>
</html>