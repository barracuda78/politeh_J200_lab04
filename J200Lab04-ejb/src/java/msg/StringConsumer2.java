package msg;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StringStringQ")
    ,
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class StringConsumer2 implements MessageListener {
    
    @EJB
    private DbMaster dbMaster;
    
    public StringConsumer2() {
    }
    
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            TextMessage tm = (TextMessage)message;
            try {
                dbMaster.writeMessage(tm.getText());
                System.out.println("StringConsumer2 - текстовое сообщение  " + tm +  " записано в базу");
            } catch (JMSException ex) {
                System.out.println("StringConsumer2 - ошибка в методе getText()");
            }
        }
        else{
            System.out.println("StringConsumer2 ПОЛУЧИЛ НЕ ТЕКСТОВОЕ СООБЩЕНИЕ");
        }
    }
}
