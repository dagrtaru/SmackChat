import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.roster.RosterListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

public class retrieve_roster
{
	JFrame frame;
	ArrayList<String> buttons;
	XMPPConnection connection;
	JButton connect;
	Collection<RosterEntry> list;		//Iterator<RemoteRosterEntry>

	public retrieve_roster(String username, String password)throws XMPPException
	{
		System.out.println("retrieve roster begining...");
		frame = ley.frame;
		connection = ley.connection;
		frame.removeAll();
		buttons = new ArrayList<String>();
		System.out.println("Send information");
		ChatClient.login(username, password);
		ChatClient.setStatus("Available");

		Roster roster = ( ley.connection).getRoster();
		list = roster.getEntries();

		System.out.println("List is: " + list);
		final JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		//final JPanel subpane1 = new JPanel();
		//subpanel.setLayout(new BoxLayout(subpanel, BoxLayout.Y_AXIS));

		roster.addRosterListener(new RosterListener(){
				@Override
				public void entriesAdded(Collection<String> arg0)
				{
					System.out.println("Entries added");
				}

				@Override
				public void entriesDeleted(Collection<String> arg0)
				{
					System.out.println("Entries removed");
				}

				@Override
				public void entriesUpdated(Collection<String> arg0){}
				@Override
				public void presenceChanged(Presence presence)
				{
					System.out.println("Presence changed: " + presence.getFrom() + " " + presence.getStatus());
					String newcomer = presence.getFrom().split("/")[0];
					//the entry is available from the begining only its status has changed...
					if((presence.isAvailable()) && buttons.contains(newcomer)){
						presence.getMode();			//should be setMode i think
						System.out.println("Was available and has changed" + presence.getMode());
						
					}
					//entry is not available, we need to add it to list and make it display...
					else if(presence.isAvailable() && buttons.contains(newcomer))
					{
						System.out.println("Someone new has come");
						JPanel subpanel = new JPanel();
						subpanel.setLayout(new BorderLayout());
						subpanel.add((connect = new JButton(newcomer)));
						buttons.add(newcomer);
						connect.setActionCommand(newcomer);
						connect.addActionListener(new ActionListener(){
								@Override
								public void actionPerformed(ActionEvent event){
									new_chat chat_box = new new_chat(event.getActionCommand());
									chat_box.start();
								}
							});
							panel.add(subpanel);
							panel_to_frame(panel);
							new add_to_list_refresh(newcomer);
					}

					else if(!presence.isAvailable() && buttons.contains(newcomer))
					{
						System.out.println("Has gone offline");
						int index = buttons.indexOf(newcomer);
						System.out.println("Index is:" + index);
						panel.remove(panel.getComponent(index));
						panel_to_frmae(panel);
					}
				}
			});

			//This will make an ArrayList<String> of all the online users
			for(RosterEntry r : list)
			{
				System.out.println("In for loop of retrieve roster");
				if(roster.getPresence(r.getUser()).isAvailable()){
					System.out.println("Name of friend is: " + r);
					buttons.add(r.getUser());
				}
			}

			show_list(panel);
			panel_to_frame(panel);
			new create_listener_threads();
			// new add_to list();
			// new receive_file();
	}

	void show_list(JPanel panel)
	{
		System.out.println("In retrieve roster, show_list");
		for(String s : buttons)
		{
			JPanel subpanel = new JPanel();
			subpanel.setLayout(new BorderLayout());
			subpanel.add((connect = new JButton(s)));
			connect.setActionCommand(s);

			connect.addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent event)
				{
					new_chat chat_box = new new_chat(event.getActionCommand());
					chat_box.start();
				}
			});
			panel.add(subpanel);
		}
	}

	void panel_to_frame(JPanel panel)
	{
		System.out.println("In retrieve roster, Panel to Frame");
		frame.removeAll();
		frame.setVisible(false);

		frame = new JFrame("BUDDY LIST");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/* frame add.WindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					connection.disconnect();
					System.out.println("in window closing...");
					ley.make_exit();
					System.exit(ABORT);
				}
			});*/
			JScrollPane scrollar = new JScrollPane(panel);
			//scrollar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			//scrollar.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			frame.getContentPane().add(BorderLayout.NORTH, scrollar);
			frame.setBackground(Color.WHITE);
			frame.setSize(350, 500);
			frame.setVisible(true);
	}

	/*public class add_to_list_refresh implements MessageListener{
		public add_to_list_refresh(String newcomer)
		{
			System.out.println("In add to list refresh");
			connection.getChatManager().createChat(newcomer, this);
		}

		@Override
		public void processMessage(Chat arg, Message msg)
		{
			if(msg.getType() == Message.Type.chat)
			{
				new receive_new_chat(msg.getBody(), arg);
				arg.removeMessageListener(this);
			}
		}
	}*/
}