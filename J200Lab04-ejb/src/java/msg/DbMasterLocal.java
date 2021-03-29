package msg;

import java.util.ArrayList;
import javax.ejb.Local;


@Local
public interface DbMasterLocal {

    void writeMessage(String message);

    void writeInteger(Integer number);

    ArrayList<String> getMessageList();

    int getTotal();

    int cleanMessages();

    int cleanNumbers();

    ArrayList<Integer> getNumbers();
    
}
