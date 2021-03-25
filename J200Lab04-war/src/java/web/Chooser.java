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
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        
        if(request.getParameter("list") != null){
            ArrayList<String> messages = dbMaster.getMessageList();
            request.setAttribute("list", messages);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }else if(request.getParameter("sum") != null){
            Integer sum = dbMaster.getTotal();
            request.setAttribute("sum", sum);
            request.getRequestDispatcher("index.jsp").forward(request, response);

        }else{
            String info = request.getParameter("info");
            int number = 0;
            try{
                number = Integer.parseInt(info);
                System.out.println("Из запроса извлечено число " + number);
                sendObjectMessage(number);
            }catch(NumberFormatException e){
                System.out.println("в запросе текст " + info);
                sendTextMessage(info);
            }
            request.setAttribute("msg", "Сообщение " + info + "отправлено");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet Chooser</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet Chooser at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void sendObjectMessage(int number) {
        try {
            QueueConnection con = factory.createQueueConnection();
            QueueSession ses = con.createQueueSession(true, 0);
            QueueSender sender = ses.createSender(siq);
            ObjectMessage tm = ses.createObjectMessage(new Integer(number));
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
