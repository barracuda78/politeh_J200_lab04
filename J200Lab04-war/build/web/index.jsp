<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Лабораторная 4</title>
    </head>
    <body>
        <div><a href="Test">Тестовый сервлет</a></div>
        
        <hr>
        
        <form action="Chooser">
            <input type="text" name="info" value="" size="60"/>
            <input type="submit" value="Отправить" name="send" />
            <br/>
            <input type="submit" value="Получить список сообщений" name="list" />
            <input type="submit" value="Получить сумму" name="sum" />
            <br/>
            <hr>
            <input type="submit" value="Clean messages from DB" name="cleanMessages" />
            <input type="submit" value="Clean integers from DB" name="cleanIntegers" />
        </form>
        
        <hr>
        
        <%
            String stringCleaned = (String)request.getAttribute("stringCleaned");
            String integerCleaned = (String)request.getAttribute("integerCleaned");
        %>
        <%= 
            (stringCleaned == null) ? "" : stringCleaned
        %>
        
        <%= 
            (integerCleaned == null) ? "" : integerCleaned
        %>
        
        <% 
            String msg = (String)request.getAttribute("msg");
        %>
        
        <h1> <%= (msg == null) ? "" : msg %> </h1>
        
        <% 
            Integer sum = (Integer)request.getAttribute("sum");
        %>
        <h1><%= (sum == null) ? "" : "сумма чисел в базе = " + sum%></h1>
        
        <% 
            ArrayList<String> list = (ArrayList<String>)request.getAttribute("list");
            if(list != null){ %>
            <h1>Список сообщений</h1>
        <%  
            for(String s : list){ %>
                <%="<h2>" + s + "</h2>"%>
            
                <%
                }
            }
        %>
    </body>
</html>
