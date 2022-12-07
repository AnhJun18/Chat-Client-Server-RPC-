package rpc.chat.gui2;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.Hashtable;

import javax.naming.NameAlreadyBoundException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import rpc.chat.client.Client;
import rpc.chat.client.ClientProxy;
import rpc.chat.client.ClientSideServerProxy;
import rpc.chat.client.RPCRuntime;
import rpc.chat.interfaces.IProxy;
import rpc.chat.interfaces.IProxyFactory; 

public class UIClient {
	private JPanel panelLogin;
	private JPanel panelChat;
	private ClientProxy clientP;
	Hashtable<String, JPanel> listPanel = new Hashtable<String,JPanel>();
	private JComboBox<String> comboBox;
	private Client myClient ;
	private JPanel panel_chat;
	private JScrollPane scrollPane_1;
	String mode="ALL";
	boolean isupdate=false;
	int num=0;
	private JFrame frame;
	boolean isOpen=false;
	public static String serverIP="169.254.22.126";
	public static int serverPort=8080;

	//private boolean isLogin=false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIClient window = new UIClient();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public UIClient() throws Exception {
	//	this.isLogin = false;
		initialize();
		panellogin();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
//    public void actionPerformed(ActionEvent e) throws Exception, Exception {
//    	 if(UiLogin.getIsLogin()) {
//    		 UiLogin.getJpanel().setVisible(false);
//    		 UiClient.run(UiLogin.getUserName(),Integer.parseInt(UiLogin.getPort()));
//    		 frame.add(UiClient.getScreen()).setVisible(true);;
//    	 }
//    }
	
	private void addPanel(String name) {
		JPanel panel_chat_new = new JPanel();
		panel_chat_new.setBounds(0, 44, 448, 1000);	
		panel_chat_new.setLayout(null);
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setViewportView(panel_chat_new);
	    listPanel.put(name, panel_chat_new);
	}
	public void run(String name) throws Exception {
		
		try {
			myClient = new Client(name);
			if(!isOpen) {
			RPCRuntime rpc = new RPCRuntime(new ServerSocket(6666));
			rpc.register("ChatClient", new IProxyFactory() {
				@Override
				public IProxy createProxy(BufferedReader inputStream, PrintWriter outputStream) {
					return new ClientSideServerProxy(inputStream, outputStream, myClient);
				}
			});
			Thread ab=new Thread(rpc);ab.start();
			isOpen=true;
			clientP = new ClientProxy(serverIP, serverPort, rpc);
			}
		
		    clientP.login(myClient);
		    ScreenChat();
			listenForMessages();
			panelLogin.setVisible(false);
			panelChat.setVisible(true);
		}catch (NameAlreadyBoundException e) {
			JOptionPane.showMessageDialog(frame,e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(frame,"Lỗi Port đã được sử dụng: "+ e.getMessage(),"ERROR",JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public void listenForMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                    	Thread.sleep(500);
                        if(myClient.getNewMsg()) {
                        	num++;
                        	JLabel lblNewLabel = new JLabel(myClient.getMsg());
                        	lblNewLabel.setBounds(36, 43+num*20, 367, 66);
                        	String sender=myClient.getMsg().split(":")[0];
                        	listPanel.get(sender).add(lblNewLabel); 
                        	listPanel.get(sender).repaint();
                    		myClient.setNewMsg(false);
                        }
                        if(myClient.getNewMember()) {
                        	isupdate=true;
                        	 comboBox.removeAllItems();
                        	 for (String client: myClient.getMember()) {                 
								 comboBox.addItem(client); 
           					  }	
                        	 myClient.setNewMember(false); 
                        	 comboBox.setSelectedItem(mode);
                        	 isupdate=false;
                        }
  
                    }catch(Exception e){
                        System.err.println("=>"+e.getMessage());
                    }
                }
            }
        }).start();
    }
	public void ScreenChat() {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
       
		frame.setBounds(100, 100, 667, 477);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		panelChat= new JPanel();
		panelChat.setBackground(new Color(192, 192, 192));
		panelChat.setBounds(113, 29, 448, 385);		
		panelChat.setLayout(null);
		frame.getContentPane().add(panelChat);
		
		
		JTextPane textMsg = new JTextPane();
		textMsg.setBounds(39, 344, 267, 33);
		panelChat.add(textMsg);
		
	    
		JButton btnNewButton = new JButton("Send");
	
		btnNewButton.setBounds(316, 344, 96, 33);
		panelChat.add(btnNewButton);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(0, 44, 448, 277);
		panelChat.add(scrollPane_1);
		
		panel_chat = new JPanel();
		panel_chat.setBounds(0, 44, 448, 1000);	
		panel_chat.setLayout(null);
		scrollPane_1.setAutoscrolls(true);
		scrollPane_1.setViewportView(panel_chat);
		listPanel.put("ALL",panel_chat);
		
		JLabel lblNewLabel_1 = new JLabel(myClient.getName());
		lblNewLabel_1.setBounds(99, 0, 231, 34);
		panelChat.add(lblNewLabel_1);
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
				  clientP.broadcast(textMsg.getText(),myClient,mode); 
				  textMsg.setText(null);
				  listPanel.get(mode).repaint(); 
			}
		});
		
	}
	public void panellogin() {
		try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UIClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
		panelLogin = new JPanel();
		panelLogin.setBounds(0, 0, 434, 261);
		panelLogin.setLayout(null);
		JTextField txtName = new JTextField();
	
		txtName.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		txtName.setBounds(161, 89, 155, 32);
		panelLogin.add(txtName);
		
		JLabel lblNewLabel = new JLabel("USER NAME");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		lblNewLabel.setBounds(74, 94, 77, 23);
		panelLogin.add(lblNewLabel);
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		btnLogin.setBounds(191, 147, 89, 32);
		panelLogin.add(btnLogin);
		frame.getContentPane().add(panelLogin);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name= txtName.getText();
				if(name.trim().isEmpty() ) {
					JOptionPane.showMessageDialog(frame,"Nhập đầy đủ các thông số");
				}
				else {
				     try {
						run(name);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				 
			}
		});
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	if(isOpen) {
            		clientP.logout(myClient);		
            	}
            	
                System.exit(0);
            }
        });
		
	}
}
