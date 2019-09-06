import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.chat2.ChatManager;
public class add_to_list_refresh implements MessageListener
{
	XMPPConnection connection;
	public add_to_list_refresh(String fresh)
	{
		connection = ley.connection;
		System.out.println("in add to list refresh");
		connection.getXmppAdressOfChatPartner().createChat(fresh, this);
	}
	/*@Override
	public void processMessage(Message msg){}*/
	
	//@Override
	public void processMessage(Message msg, Chat arg)		//overriding the function from new_chat.java // removed the chat argument
	{
		if(msg.getType() == Message.Type.chat)
		{
			new receive_new_chat(msg.getBody(), arg);
			arg.removeMessageListener(this);
		}
	}
}