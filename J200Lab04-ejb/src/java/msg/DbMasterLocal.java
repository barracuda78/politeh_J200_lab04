/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msg;

import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author ENVY
 */
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
