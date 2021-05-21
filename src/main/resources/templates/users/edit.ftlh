<!DOCTYPE html>
<html lang="ru">
<head>
    <title>POLLER - Страница редактирования пользователя</title>
    <style>
        body {
            margin: 20px
        }

        .div-center {
            position: absolute;
            top: 40%;
            left: 50%;
            margin: -55px 0 0 -85px;
            width: 170px
        }
    </style>
</head>
<body>
<h2 style="text-align: center">Страница редактирования пользователя ${user.get().getUsername()}</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/polls">Мои опросы</a>
</h4>
<div class="div-center">
    <#if message??><h4 style="color: darkred; text-align: center">${message}</h4></#if>
    <form action="/user/edit" method="post">
        <div style="margin-bottom: 7px"><input type="password" name="password" placeholder="Пароль" style="width: 160px"/></div>
        <div style="margin-bottom: 7px"><input type="password" name="confirmPassword" placeholder="Пароль ещё раз" style="width: 160px"/></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Изменить" style="width: 168px"/></div>
    </form>
</div>
</body>
</html>