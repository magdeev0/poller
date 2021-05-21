<!DOCTYPE html>
<html lang="ru">
<head>
    <title>POLLER - Страница загрузки аватара</title>
    <style>
        body {
            margin: 20px
        }

        .div-center {
            position: absolute;
            top: 40%;
            left: 50%;
            margin: -55px 0 0 -126px;
            width: 252px
        }
    </style>
</head>
<body>
<h2 style="text-align: center">Страница загрузки аватара</h2>
<h4 style="text-align: center">
    <a href="/">Главная страница</a> ||
    <a href="/polls">Мои опросы</a>
</h4>
<div class="div-center">
    <div style="text-align: center"><#if message??>${message}</#if></div>
    <form method="post" enctype="multipart/form-data">
        <div style="margin-bottom: 7px"><input type="file" name="file" style="width: 250px"/></div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Загрузить" style="width: 258px"/></div>
    </form>
</div>
</body>
</html>