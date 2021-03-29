package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import msg.DbMasterLocal;

@WebServlet(name = "Chooser", urlPatterns = {"/Chooser"})
public class Chooser extends HttpServlet {

    @EJB
    private DbMasterLocal dbMaster;
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private QueueConnectionFactory factory;
    
    @Resource(lookup="jms/StringStringQ")
    private Queue ssq;
    
    @Resource(lookup="jms/StringIntegerQ")
    private Queue siq;

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        if(request.getParameter("list") != null){
            ArrayList<String> messages = dbMaster.getMessageList();
            request.setAttribute("list", messages);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else if(request.getParameter("sum") != null){

            Integer sum = dbMaster.getTotal();
            ArrayList<Integer> numbers = dbMaster.getNumbers();
            request.setAttribute("numbers", numbers);
            request.setAttribute("sum", sum);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        }else if(request.getParameter("send") != null){
            String info = request.getParameter("info");
            int number;
            try{
                number = Integer.parseInt(info);
                System.out.println("Из запроса извлечено число " + number);
                sendObjectMessage(number);
                
                //dbMaster.writeInteger(number);//////////////////////////////////////////////////------> Удалить. Это для теста записи в бд из сервлета без JMS
           
            }catch(NumberFormatException e){
                System.out.println("в запросе текст " + info);
                sendTextMessage(info);

                //dbMaster.writeMessage(info); //////////////////////////////////////////////////------> Удалить. Это для теста записи в бд из сервлета без JMS
            }
            request.setAttribute("msg", "Сообщение " + info + " отправлено");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else if(request.getParameter("cleanMessages") != null){
            dbMaster.cleanMessages();
            request.setAttribute("stringCleaned", "All messages removed from DB.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else if(request.getParameter("cleanIntegers") != null){
            dbMaster.cleanNumbers();
            request.setAttribute("integerCleaned", "All numbers removed from DB");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else if(request.getParameter("numbers") != null){
            ArrayList<Integer> numbers = dbMaster.getNumbers();
            request.setAttribute("numbersList", numbers);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void sendObjectMessage(int number) {
        try {
            QueueConnection con = factory.createQueueConnection();
            QueueSession ses = con.createQueueSession(true, 0);
            QueueSender sender = ses.createSender(siq);
            ObjectMessage tm = ses.createObjectMessage(number);
            sender.send(tm);
        } catch (JMSException ex) {
            //Logger.getLogger(Chooser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка отправки числового сообщения");
        }
    }

    private void sendTextMessage(String info) {
        try {
            QueueConnection con = factory.createQueueConnection();
            QueueSession ses = con.createQueueSession(true, 0);
            QueueSender sender = ses.createSender(ssq);
            TextMessage tm = ses.createTextMessage(info);
            sender.send(tm);
        } catch (JMSException ex) {
            //Logger.getLogger(Chooser.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка отправки текстового сообщения");
        }
    }

}
