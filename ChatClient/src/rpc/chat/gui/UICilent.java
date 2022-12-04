package rpc.chat.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;

import rpc.chat.client.Client;
import rpc.chat.client.ClientProxy;
import rpc.chat.client.ClientSideServerProxy;
import rpc.chat.client.RPCRuntime;
import rpc.chat.interfaces.IProxy;
import rpc.chat.interfaces.IProxyFactory;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;
import java.awt.ScrollPane;
import java.awt.Panel;
import java.awt.TextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingConstants;

public class UICilent {

	private JFrame frame;
	private JPanel panel;
	private ClientProxy clientP;
	 Hashtable<String, JPanel> listPanel = new Hashtable<String,JPanel>();
	private JComboBox comboBox;
	private Client myClient ;
	private JPanel panel_chat;
	private JScrollPane scrollPane_1;
	String mode="ALL";
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
		myClient = new Client("MANH");
		System.out.println();
		RPCRuntime rpc = new RPCRuntime(new ServerSocket(9090));
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
                        	System.out.print(myClient.gibMsg()+"    "+ mode);
                        	JLabel lblNewLabel = new JLabel(myClient.gibMsg());
                        	lblNewLabel.setBounds(36, 43+num*20, 367, 66);
                        	listPanel.get(mode).add(lblNewLabel);
                        	 System.out.print(mode);
                        	 
                        	 listPanel.get(mode).repaint();
                    		myClient.setStatus(false);
                        }
                        if(myClient.getStatusMember()) {
                        	comboBox.removeAllItems();
                        	 for (String client: myClient.getMember()) { 
                        		 comboBox.addItem(client);
           					  }	
                        	 myClient.setStatusMember(false);
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
	private JPanel createPanelChat() {
		JPanel panel_chat_new = new JPanel();
		panel_chat_new.setBounds(0, 44, 448, 1000);	
		panel_chat_new.setLayout(null);
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setViewportView(panel_chat);
		return panel_chat_new;
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
		System.out.println("111111111111111");
		comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("222222222");
				listPanel.get(mode).setVisible(false);
				mode=(String)comboBox.getSelectedItem();
				
				if(!listPanel.containsKey(mode)){
					System.out.println("444444444444");
			    addPanel(mode);
			    listPanel.get(mode).setVisible(true);
			    scrollPane_1.setViewportView(listPanel.get(mode));
				listPanel.get(mode).repaint(); 
		}
				else {
					System.out.println("111111333333333333111111111");
				listPanel.get(mode).setVisible(true);
				scrollPane_1.setViewportView(listPanel.get(mode));
				listPanel.get(mode).repaint(); 
				}
				/* panel.setVisible(false); */
			}
		});
		
		comboBox.setBounds(37, 29, 77, 44);
		frame.getContentPane().add(comboBox);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
	
		
		
	}

}
