<!DOCTYPE html>
<html lang="ru">
<head>
    <title>ADMIN - Список всех опросов</title>
    <style>
        body {
            margin: 20px
        }

        .div-center {
            position: absolute;
            top: 30%;
            left: 50%;
            margin: -55px 0 0 -150px;
            width: 300px
        }
    </style>
</head>
<body>
<h2 style="text-align: center">Панель администратора</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/admin/users">Список всех пользователей</a> ||
    <a href="/admin/polls">Список всех опросов</a>
</h4>
<div class="div-center">
    <table border="1" style="text-align: center">
        <caption>Список всех созданных опросов</caption>
        <tr>
            <th>id</th>
            <th>Название</th>
            <th>Всего голосов</th>
            <th>Автор</th>
            <th>Доступен для голосования</th>
            <th>Управление</th>
        </tr>
        <#if polls?has_content>
            <#list polls as poll>
                <tr>
                    <td>${poll.getId()}</td>
                    <td><a href="/poll/${poll.getId()}">${poll.getName()}</a></td>
                    <td>${poll.getVotes()}</td>
                    <td>${poll.getOwner().getUsername()}</td>
                    <td><#if poll.isDeleted()>Нет<#else>Да</#if></td>
                    <td><#if poll.isDeleted()>
                            <a href="/admin/polls/${poll.getId()}/restore">Восстановить</a>
                        <#else>
                            <a href="/admin/polls/${poll.getId()}/delete">Удалить</a>
                        </#if></td>
                </tr>
            </#list>
        <#else>
            Ещё нет созданных опросов
        </#if>
    </table>
</div>
</body>
</html>