<!DOCTYPE html>
<html lang="ru">
<head>
    <title>POLLER - Страница опроса</title>
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
<h2 style="text-align: center">Страница опроса</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/polls/create">Создать опрос</a>
</h4>
<div class="div-center">
    <h3 style="text-align: center">${poll.get().getName()}</h3>
    <form action="/poll/${poll.get().getId()}" method="post">
        <#list poll.get().getQuestion() as question>
            <hr>
            <b>${question.getQuestion()}</b><br>
            <#list question.getAnswers() as answer>
                <input type="radio" id="${answer.id}" value="${answer.getId()}" name="answer${question.getId()}" required>
                <label for="${answer.id}">${answer.getAnswer()}</label><#sep><br></#sep>
            </#list>
        </#list>
        <hr>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div style="text-align: center; margin: 20px 0 10px 0">
            <input type="submit" value="Проголосовать" style="width: 168px"/>
        </div>
    </form>
    <div style="text-align: center">
        <a href="/poll/results/${poll.get().getId()}">Посмотреть результаты</a>
    </div>
</div>
</body>
</html>