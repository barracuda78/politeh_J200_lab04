<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style type="text/css">
            #main-header{
                text-align:center;
                background-color: black;
                color:white;
                padding:10px;
            }
        </style>
        <title>Лабораторная 4</title>
    </head>
    <body>
        <header id="main-header">
            <aside><a href="Test">Статистика</a> по базе данных</aside>
        
        <hr>
        
        <form action="Chooser">
            <div>
                <div>
                    <br/>
                    <label>Тип сообщения</label>
                    <select>
                        <option value="textType">Текстовое сообщение</option>
                        <option value="numberType">Целое число</option>
                    </select>
                    <br/>
                </div>
                <label>Введите сообщение:</label>
                <textarea name="info" placeholder="ваш текст или число..."></textarea>    
            <!--input type="text" name="info" value="" size="60"/-->
            <input type="submit" value="Отправить" name="send" />
            </div>
            <br/>
            <div>
            <input type="submit" value="Получить список сообщений" name="list" />
            </div>
            <div>
            <input type="submit" value="Получить список чисел" name="numbers" />
            </div>
            <div>
            <input type="submit" value="Получить сумму чисел" name="sum" />
            </div>
            <br/>
            <hr>
            <input type="submit" value="Clean messages from DB" name="cleanMessages" />
            <div>
            <input type="submit" value="Clean integers from DB" name="cleanIntegers" />
            </div>
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
        
        <p1> <%= (msg == null) ? "" : msg %> </p1>
        
        <% 
            Integer sum = (Integer)request.getAttribute("sum");
            ArrayList<Integer> numbers = (ArrayList<Integer>)request.getAttribute("numbers");
            
        %>
        <p1><%= (sum == null) ? "" : numbers.size() > 0 ? ("сумма чисел в базе = " + sum) : "В базе данных нет ни одного числа." %></p1>
        
        <% 
            ArrayList<String> list = (ArrayList<String>)request.getAttribute("list");
            if(list != null && list.size() > 0){ %>
                <p1>Список сообщений</p1>
        <%  
                for(String s : list){ %>
                    <%="<h2>" + s + "</h2>"%>
                <%
                }
            }
        %>
        <%
            if(list != null && list.size() == 0){ %>
                <p1>Список сообщений <strong>пуст</strong>.</p1>
            <%}%>
                
            <%
                ArrayList<Integer> numbersList = (ArrayList<Integer>)request.getAttribute("numbersList");
                if(numbersList != null && numbersList.size() > 0 ){ %>
                    <p1>Список чисел в базе данных:</p1>
                    <ul>
                        <%for(Integer i: numbersList){%>
                        <li><%= i %></li>
                        <% } %>
                    </ul>
                    <%
                }else if(numbersList != null && numbersList.size() == 0 ){ 
                    %>
                        <p1>Список чисел <strong>пуст</strong>.</p1>
                    <%
                }
            %>    
        </header>
    </body>
</html>
