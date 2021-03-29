<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="css/style01.css"/>
        <title>Messages</title>
    </head>
    <body>
        <header id="main-header">
            <h1>Message service</h1>
        </header>
    <p1 class="menu">
        DataBase <a href="Test">statistics</a>
        Numbers <a href="Num">statistics</a>
        Messages <a href="Messg">statistics</a>
    </p1>

        <hr style="border-color: #666666">

        <form action="Chooser" method="GET">
            <div class="container">
                <div class="box-1">
                    <div>
                        <br/>
                        <label>Тип сообщения:</label>
                        <select class="b1">
                            <option value="textType" class="b1">Текстовое сообщение</option>
                            <option value="numberType" class="b1">Целое число</option>
                        </select>
                        <br/>
                    </div>
                    <label>Введите сообщение:</label>
                    <textarea name="info" placeholder="ваш текст или число..." class="t1"></textarea>    
                    <!--input type="text" name="info" value="" size="60"/-->
                    <input type="submit" value="Отправить" name="send" class="b1"/>
                </div>
            </div>
            <br/>
            <div class="container">
                <div class="box-2">
                    <p><center>DataBase requests:</center></p>
                    <input type="submit" value="Получить список сообщений" name="list" class="b1"/>
                    <input type="submit" value="Получить список чисел" name="numbers" class="b1"/>
                    <input type="submit" value="Получить сумму чисел" name="sum" class="b1"/>
                </div>
            </div>
            <br/>
            <hr style="border-color: #666666">
            <input type="submit" value="Clean messages from DB" name="cleanMessages" class="b1"/>
            <div>
                <input type="submit" value="Clean integers from DB" name="cleanIntegers" class="b1"/>
            </div>
        </form>
        <hr style="border-color: #666666">

        <%
            String stringCleaned = (String) request.getAttribute("stringCleaned");
            String integerCleaned = (String) request.getAttribute("integerCleaned");
        %>
        <%=(stringCleaned == null) ? "" : stringCleaned%>

        <%=(integerCleaned == null) ? "" : integerCleaned%>

        <%
            String msg = (String) request.getAttribute("msg");
        %>

    <p1> <%= (msg == null) ? "" : msg%> </p1>

    <%
        Integer sum = (Integer) request.getAttribute("sum");
        ArrayList<Integer> numbers = (ArrayList<Integer>) request.getAttribute("numbers");

    %>
    <p1><%= (sum == null) ? "" : numbers.size() > 0 ? ("сумма чисел в базе = " + sum) : "В базе данных нет ни одного числа."%></p1>

    <%
        ArrayList<String> list = (ArrayList<String>) request.getAttribute("list");
        if (list != null && list.size() > 0) { %>
        <h1>Список сообщений</h1><ul>
        <%
            for (String s : list) {%>
        <%="<p2><li>" + s + "</li></p2>"%>
        <%
                }
            }
        %>
        </ul>
        <%
            if (list != null && list.size() == 0) { %>
    <p1>Список сообщений <strong>пуст</strong>.</p1>
        <%}%>

    <%
        ArrayList<Integer> numbersList = (ArrayList<Integer>) request.getAttribute("numbersList");
        if (numbersList != null && numbersList.size() > 0) { %>
    <p1>Список чисел в базе данных:</p1>
    <ul>
        <%for (Integer i : numbersList) {%>
        <li><%= i%></li>
            <% } %>
    </ul>
    <%
    } else if (numbersList != null && numbersList.size() == 0) {
    %>
    <p1>Список чисел <strong>пуст</strong>.</p1>
        <%
            }
        %>    

</body>
</html>
