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
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO messages (message) VALUES (?)");
            stmt.setString(1, message);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Ошибка записи сообщений " + message + " в БД");
        }
    }

    @Override
    public void writeInteger(Integer number) {
        try {
            Connection conn = getConnection();
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
            Connection conn = getConnection();
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
            Connection conn = getConnection();
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

    private Connection getConnection() {

        try {
            if (conn == null || conn.isClosed()) {

                conn = DriverManager.getConnection("jdbc:derby://localhost:1527/j200lab04", "test", "test");
                System.out.println("Connected to " + conn.getSchema() + " object:" + conn);

            }

        } catch (SQLException ex) {
            //Logger.getLogger(DbMaster.class.getName()).log(Level.SEVERE, null, ex);

            System.out.println("Connection to DB j200lab04 failed");
        }
        return conn;
    }

}
