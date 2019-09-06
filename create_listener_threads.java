import java.util.Collection;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

public class create_listener_threads implements MessageListener
{
	XMPPConnection connection;
	Roster roster;
	Collection<RosterEntry> list;

	public create_listener_threads()
	{
		connection = ley.connection;
		roster = connection.getRoster();
		list = roster.getEntries();
		for(RosterEntry r : list)
		{
			if(roster.getPresence(r.getJid()).isAvailable())
			{
				String buddy = r.getUser();
				ley.connection.getChatMessenger().createChat(buddy, this);

			}
		}
	}
	public void processMessage(Message msg)
	{
		if(msg.getType() == Message.Type.chat)
		{
			new receive_new_chat(msg.getBody(), arg);
			arg.removeMessageListener(this);
		}
	}
}