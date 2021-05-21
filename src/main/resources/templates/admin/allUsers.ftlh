<!DOCTYPE html>
<html lang="ru">
<head>
    <title>ADMIN - Список всех пользователей</title>
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
    <#if users??>
        <table align="center" border="1" style="text-align: center">
            <caption>Список всех пользователей</caption>
            <tr>
                <th>Логин</th>
                <th>Роли</th>
            </tr>
            <#list users as user>
                <tr>
                    <td><a href="/user/${user.get().getId()}">${user.getUsername()}</a></td>
                    <td><#list user.getRoles() as role>${role}<#sep>, </#sep></#list></td>
                </tr>
            </#list>
        </table>
    <#elseif foundUser??>
        <#if foundUser.present>
            <table align="center" border="1" style="text-align: center">
                <caption>Список найденных пользователей</caption>
                <tr>
                    <th>Логин</th>
                    <th>Роли</th>
                </tr>
                <tr>
                    <td><a href="/user/${foundUser.get().getId()}">${foundUser.get().getUsername()}</a></td>
                    <td><#list foundUser.get().getRoles() as role>${role}<#sep>, </#sep></#list></td>
                </tr>
            </table>
        <#else>
            <h3 style="text-align: center">Пользователь не найден</h3>
        </#if>
    </#if>
    <form action="/admin/users" method="post" style="text-align: center">
        <div style="margin: 7px 0 7px 0">
            <input type="text" name="username" placeholder="Имя пользователя" style="width: 120px" required/>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Найти" style="width: 128px"/></div>
    </form>
</div>
</body>
</html>