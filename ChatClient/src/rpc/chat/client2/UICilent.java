package rpc.chat.client2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import rpc.chat.client.Client;
import rpc.chat.client.ClientProxy;
import rpc.chat.client.ClientSideServerProxy;
import rpc.chat.client.RPCRuntime;
import rpc.chat.interfaces.IProxy;
import rpc.chat.interfaces.IProxyFactory;

public class UICilent {

	private JFrame frame;
	private JPanel panel;
	private ClientProxy clientP;
	 Hashtable<String, JPanel> listPanel = new Hashtable<String,JPanel>();
	private JComboBox<String> comboBox;
	private Client myClient ;
	private JPanel panel_chat;
	private JScrollPane scrollPane_1;
	String mode="ALL";
	boolean isupdate=false;
	int num=0;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UICilent window = new UICilent();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the application.
	 */
	public UICilent() throws Exception {
	
		System.out.println("-- UI --");
		myClient = new Client("PA");
		RPCRuntime rpc = new RPCRuntime(new ServerSocket(6666));
		rpc.register("ChatClient", new IProxyFactory() {
			@Override
			public IProxy createProxy(BufferedReader inputStream, PrintWriter outputStream) {
				return new ClientSideServerProxy(inputStream, outputStream, myClient);
			}
		});
		(new Thread(rpc)).start();

		clientP = new ClientProxy("localhost", 8080, rpc);
		clientP.anmelden(myClient);
		initialize();
		listenForMessages();
	}

	public void listenForMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                    	Thread.sleep(500);
                        if(myClient.gibStatus()) {
                        	num++;
                        	JLabel lblNewLabel = new JLabel(myClient.gibMsg());
                        	lblNewLabel.setBounds(36, 43+num*20, 367, 66);
                        	String sender=myClient.gibMsg().split(":")[0];
                        	listPanel.get(sender).add(lblNewLabel); 
                        	listPanel.get(sender).repaint();
                    		myClient.setStatus(false);
                        }
                        if(myClient.getStatusMember()) {
                        	isupdate=true;
                        	 comboBox.removeAllItems();
                        	 for (String client: myClient.getMember()) {                 
								 comboBox.addItem(client); 
           					  }	
                        	 myClient.setStatusMember(false); 
                        	 comboBox.setSelectedItem(mode);
                        	 isupdate=false;
                        }
  
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        }).start();
    }
	private void addPanel(String name) {
		JPanel panel_chat_new = new JPanel();
		panel_chat_new.setBounds(0, 44, 448, 1000);	
		panel_chat_new.setLayout(null);
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setViewportView(panel_chat_new);
	    listPanel.put(name, panel_chat_new);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UICilent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UICilent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UICilent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UICilent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
		frame = new JFrame();
		frame.setBounds(100, 100, 667, 477);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		panel= new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(113, 29, 448, 385);		
		panel.setLayout(null);
		frame.getContentPane().add(panel);
		
		
		JTextPane textMsg = new JTextPane();
		textMsg.setBounds(39, 344, 267, 33);
		panel.add(textMsg);
		
	    
		JButton btnNewButton = new JButton("Send");
	
		btnNewButton.setBounds(316, 344, 96, 33);
		panel.add(btnNewButton);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 44, 448, 277);
		panel.add(scrollPane_1);
		
		panel_chat = new JPanel();
		panel_chat.setBounds(0, 44, 448, 1000);	
		panel_chat.setLayout(null);
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setViewportView(panel_chat);
		listPanel.put("ALL",panel_chat);
		
		JLabel lblNewLabel_1 = new JLabel(myClient.gibName());
		lblNewLabel_1.setBounds(99, 0, 231, 34);
		panel.add(lblNewLabel_1);
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isupdate==false) {
				  listPanel.get(mode).setVisible(false);
				  mode=(String)comboBox.getSelectedItem();
				  if(!listPanel.containsKey(mode)){ addPanel(mode);
				  listPanel.get(mode).setVisible(true);
				  scrollPane_1.setViewportView(listPanel.get(mode));
				  listPanel.get(mode).repaint(); } else { listPanel.get(mode).setVisible(true);
				  scrollPane_1.setViewportView(listPanel.get(mode));
				  listPanel.get(mode).repaint(); }
				}
				 
			}
		});
		
		comboBox.setBounds(37, 29, 77, 44);
		frame.getContentPane().add(comboBox);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				num=0;
				  JLabel lblNewLabel = new JLabel(textMsg.getText());
				  num++;
				  lblNewLabel.setBounds(10,20+num*20, 367, 66); 
					lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
					listPanel.get(mode).add(lblNewLabel);
				  System.out.println(listPanel.get(mode).toString());
				  clientP.broadcast(textMsg.getText(),myClient,mode); 
				  textMsg.setText(null);
				  listPanel.get(mode).repaint(); 
			}
		});
		
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	clientP.abmelden(myClient);
                System.exit(0);
            }
        });
	
		
		
	}
}
