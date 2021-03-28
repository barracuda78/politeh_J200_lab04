package msg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;

@Singleton
public class DbMaster implements DbMasterLocal {

    Connection conn;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void writeMessage(String message) {
        try {
            conn = getConnection();
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO MESSAGES VALUES ('"+ message +"')");
            //stmt.executeUpdate("INSERT INTO MESSAGES VALUES ('просто захардкодил сообщение')");
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка записи сообщений " + message + " в БД");
            ex.printStackTrace();
        }
    }

    @Override
    public void writeInteger(Integer number) {
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO numbers VALUES (?)");
            stmt.setInt(1, number);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка записи числа " + number + " в БД");
        }
    }

    @Override
    public ArrayList<String> getMessageList() {
        ArrayList<String> messages = new ArrayList<>();
        try {
            conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM messages");
            while(rs.next()){
                messages.add(rs.getString(1));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка извлечения списка сообщений");
        }
        return messages;
    }

    @Override
    public int getTotal() {
        int sum = 0;
        try {
            conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT sum(number) FROM numbers");
            if(rs.next()){
                sum = rs.getInt(1);
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка извлечения суммы чисел из базы");
        }
        return sum;
    }
    
    
    @Override
    public ArrayList<Integer> getNumbers() {
        ArrayList<Integer> numbers = new ArrayList<>();
        try {
            conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM numbers");
            while(rs.next()){
                numbers.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка извлечения списка чисел");
        }
        return numbers;
    }
    
    @Override
    public int cleanMessages() {
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/j200lab04", "test", "test");
            Statement statement = conn.createStatement();
            int deletedCount = statement.executeUpdate("DELETE FROM MESSAGES");
            return deletedCount;
            
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("не удалось очистить таблицу MESSAGES в базе данных");
        }
        return -1;
    }
    
    @Override
    public int cleanNumbers() {
        try {
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/j200lab04", "test", "test");
            Statement statement = conn.createStatement();
            int deletedCount = statement.executeUpdate("DELETE FROM NUMBERS");
            return deletedCount;
            
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("не удалось очистить таблицу NUMBERS в базе данных");
        }
        return -1;
    }

    private Connection getConnection() {

        try {
            if (conn == null || conn.isClosed()) {

                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/j200lab04", "test", "test");
                System.out.println("Мы соединились с " + conn.getSchema() + " object: " + conn);
            }

        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Connection to DB j200lab04 failed");
        }
        return conn;
    }
    
    
    
//    public static void main(String[] args) {
//        new DbMaster().writeMessage("message");
//    }






}
