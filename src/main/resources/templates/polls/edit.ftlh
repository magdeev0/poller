<!DOCTYPE html>
<html lang="ru">
<head>
    <title>POLLER - Редактирование опроса</title>
    <style>
        body {
            margin: 20px
        }

        .div-center {
            position: absolute;
            top: 40%;
            left: 50%;
            margin: -55px 0 0 -120px;
            width: 240px
        }
    </style>
</head>
<body>
<h2 style="text-align: center">Страница редактирования опроса</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/user/">Моя страница</a> ||
    <a href="/polls">Мои опросы</a>
</h4>
<div class="div-center">

    <form action="/poll/edit/${id}" method="post">
        <div style="margin-bottom: 15px"><input type="text" name="name" value="${poll.get().getName()}" style="width: 232px" required></div>
        <hr>
        <#list poll.get().getQuestion() as question>
            <div style="margin-bottom: 7px"><input type="text" name="question" value="${question.getQuestion()}" style="width: 232px" required/></div>
            <div style="margin-bottom: 7px"><input type="text" name="answers" value="<#list question.getAnswers() as answer>${answer.getAnswer()}<#sep>; </#sep></#list>" style="width: 232px" required/></div>
            <#sep><hr></#sep>
        </#list>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Изменить" style="width: 240px"/></div>
    </form>
</div>
</body>
</html>