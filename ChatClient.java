import java.util.Collection;
import java.util.Scanner;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPTCPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

public class ChatClient
{
	static Collection<RosterEntry> entries;
	public static void login(String uName, String pass)throws XMPPException
	{
		//ConnectionConfiguration config = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
		XMPPTCPConnection con = new XMPPTCPConnection("igniterealtime.org");
		ley.connection = new XMPPConnection(con);
		ley.connection.connect();
		ley.connection.login(uName, pass);

	}

	public static void displayBuddyList()
	{
		Roster rost = ley.connection.getRoster();
		entries = rost.getEntries();
		String s = null;
		for(RosterEntry r: entries)
		{
			if(rost.getPresence(r.getUser()).isAvailable())
			{
				System.out.print(rost.getPresence(r.getUser()).getFrom());
				s = r.toString();
			}
		}
	}

	public void disconnect()
	{
		ley.connection.disconnect();
	}

	public static void setStatus(String stas)throws InterruptedException
	{
		Presence presence = new Presence(Presence.Type.available);
		presence.setStatus(stas);
		String temp = presence.getStatus();
		System.out.println(temp);
		presence.setPriority(0);
		presence.setMode(Presence.Mode.available);
		//sending the presence packet through connection
		String service = ley.connection.getServiceName();
		System.out.print(service);
		ley.connection.sendPacket(presence);
		Thread.sleep(20000);
	}
}