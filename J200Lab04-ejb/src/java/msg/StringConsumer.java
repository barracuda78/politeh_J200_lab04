
package msg;

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
public class StringConsumer implements MessageListener {
    
    @EJB
    private DbMasterLocal dbMaster;
    
    public StringConsumer() {
    }
    
    @Override
    public void onMessage(Message message) {
        if(message instanceof TextMessage){
            try {
                String mg = ((TextMessage) message).getText();
                dbMaster.writeMessage(mg);
            } catch (JMSException ex) {
                //Logger.getLogger(StringConsumer.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("------Ошибка в методе onMessage бина StringConsumer");
            }
        }else{
            System.out.println("------StringConsumer получил левое сообщение");
        }
    }
}
