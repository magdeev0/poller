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
        <#list poll.get().getQuestion() as question>
            <hr>
            <div style="font-weight: bold; text-align: center">${question.getQuestion()}<br></div>
            <#list question.getAnswers() as answer>
                <div style="font-weight: normal; text-align: left">${answer.getAnswer()} — ${answer.getVotes()}<#sep><br></#sep></div>
            </#list>
        </#list>
        <hr>
    Всего проголосовавших: ${votes}
</div>
</body>
</html>