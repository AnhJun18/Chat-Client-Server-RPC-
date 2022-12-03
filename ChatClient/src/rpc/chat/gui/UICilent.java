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

public class UICilent {

	private JFrame frame;
	private JPanel panel;
	private ClientProxy clientP ;
	private Client myClient ;
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
		myClient = new Client("JUN");
		System.out.println();
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
		clientP.broadcast("testMsg", myClient);
		initialize();
		System.out.println("đã xg");
		listenForMessages();
	}

	public void listenForMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        if(myClient.gibStatus()) {
                        	System.out.println("rep:"+myClient.gibMsg());
                        	JLabel lblNewLabel = new JLabel(myClient.gibMsg());
                        	lblNewLabel.setBounds(50, 17, 309, 56);
                    		panel.add(lblNewLabel);
                    		panel.repaint();
                    		myClient.setStatus(false);
                        }
                       
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        }).start();
    }
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 667, 477);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		panel= new JPanel();
		panel.setBackground(new Color(192, 192, 192));
		panel.setBounds(124, 37, 408, 336);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextPane textMsg = new JTextPane();
		textMsg.setBounds(34, 293, 267, 33);
		panel.add(textMsg);
		
	    
		JButton btnNewButton = new JButton("Send");
	
		btnNewButton.setBounds(302, 293, 96, 33);
		panel.add(btnNewButton);
		
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				  JLabel lblNewLabel = new JLabel(textMsg.getText()); lblNewLabel.setBounds(10,
				 20, 45, 13); panel.add(lblNewLabel); clientP.broadcast(textMsg.getText(),
				  myClient); textMsg.setText(null); panel.repaint();
				 
				
			}
		});
	
		
		
	}
}
