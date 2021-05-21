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
    <form action="/polls/create" method="post">
        <div style="margin-bottom: 7px"><input type="text" name="name" placeholder="Название опроса" style="width: 232px" required/></div>
        <div style="margin-bottom: 7px"><select style="width: 240px" size="1" required name="countOfQuestions">
                <option disabled>Выберите количество вопросов</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
            </select></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Создать" style="width: 240px"/></div>
    </form>
</div>
</body>
</html>