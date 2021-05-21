<!DOCTYPE html>
<html lang="ru">
<head>
    <title>POLLER - Создать опрос</title>
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
<h2 style="text-align: center">Страница создания опроса</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/user/">Моя страница</a> ||
    <a href="/polls">Мои опросы</a>
</h4>
<div class="div-center">

    <form action="/polls/create-confirm/${id}" method="post">
        <#list questions as question>
            <div style="margin-bottom: 7px"><input type="text" name="question" placeholder="Вопрос" style="width: 232px" required/></div>
            <div style="margin-bottom: 7px"><input type="text" name="answers" placeholder="Варианты ответа через ;" style="width: 232px" required/></div>
            <#sep><hr></#sep>
        </#list>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Подтвердить" style="width: 240px"/></div>
    </form>
</div>
</body>
</html>