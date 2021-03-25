
package msg;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@MessageDriven(activationConfig = {
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "jms/StringIntegerQ")
    ,
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/StringIntegerQ")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
        @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "jms/StringIntegerQ")
    ,
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
})
public class IntegerConsumer implements MessageListener {
    
    @EJB
    private DbMasterLocal dbMaster;
    
    public IntegerConsumer() {
    }
    
    @Override
    public void onMessage(Message message) {
        if(message instanceof ObjectMessage){
            ObjectMessage om = (ObjectMessage)message;
            try {
                Integer num = (Integer)om.getObject();
                dbMaster.writeInteger(num);
            } catch (JMSException ex) {
                //Logger.getLogger(IntegerConsumer.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Ошибка извлечения числа из сообщения");
            }
        }
    }
    
}
