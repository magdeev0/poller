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
    <a href="/user">Моя страница</a> ||
    <a href="/polls/create">Создать опрос</a>
</h4>
<table border="1" style="text-align: center">
    <caption>Мои созданные опросы</caption>
    <#if polls?has_content>
        <tr>
            <th>Название</th>
            <th>Всего проголосовавших</th>
            <th>Видимость</th>
            <th>Заполнен</th>
        </tr>
        <#list polls as poll>
            <tr>
                <td><#if poll.confirmed>
                        <a href="/poll/${poll.getId()}">${poll.getName()}</a>
                    <#else>
                        <a href="/polls/create-confirm/${poll.getId()}">${poll.getName()}</a>
                    </#if></td>
                <td>${poll.getVotes()}</td>
                <td><#if poll.isDeleted()>
                        Да
                    <#else>
                        <a href="/poll/${poll.getId()}/delete">Удалить</a>
                    </#if></td>
                <td><#if poll.confirmed>
                        Да
                    <#else>
                        <a href="/polls/create-confirm/${poll.getId()}">Заполнить</a>
                    </#if>
                </td>
            </tr>
        </#list>
    <#else>
        <tr>
            <td>Вы ещё не создавали опросов</td>
        </tr>
    </#if>
</table>
</body>
</html>