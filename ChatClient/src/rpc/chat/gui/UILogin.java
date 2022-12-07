//package rpc.chat.gui;
//
//import java.awt.Font;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//
//import javax.swing.JButton;
//import javax.swing.JEditorPane;
//import javax.swing.JFormattedTextField;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//import rpc.chat.client.Client;
//import rpc.chat.client.ClientProxy;
//
//
//public class UILogin implements ActionListener{
//    public Boolean getIsLogin() {
//		return isLogin;
//	}
//
//	public void setIsLogin() {
//		this.isLogin = !isLogin;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String user_name) {
//		this.userName = user_name;
//	}
//
//	public String getPort() {
//		return port;
//	}
//
//	public void setPort(String port) {
//		this.port = port;
//	}
//
//	private Boolean isLogin;
//    private String userName;
//    private String port;
//	private JFrame frame;
//	private ClientProxy clientP;
//	private Client myClient ;
//	public UILogin(JFrame frame) throws Exception{
//		try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(UILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(UILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(UILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(UILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//		this.frame = frame;
//		this.isLogin = false;
//		this.userName= "";
//		this.port = "";
//	}
//		
//	public JPanel getJpanel() {
//		JPanel panel = new JPanel();
//		panel.setBounds(0, 0, 434, 261);
//		panel.setLayout(null);
//		JEditorPane txtName = new JEditorPane();
//		txtName.setFont(new Font("Times New Roman", Font.PLAIN, 13));
//		txtName.setBounds(156, 60, 145, 20);
//		panel.add(txtName);
//		
//		JLabel lblNewLabel = new JLabel("USER NAME");
//		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
//		lblNewLabel.setBounds(69, 66, 77, 14);
//		panel.add(lblNewLabel);
//		
//		JButton btnLogin = new JButton("Dô");
//		btnLogin.setFont(new Font("Times New Roman", Font.PLAIN, 13));
//		btnLogin.setBounds(174, 146, 89, 32);
//		panel.add(btnLogin);
//		
//		JLabel lblNewLabel_1 = new JLabel("PORT");
//		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 13));
//		lblNewLabel_1.setBounds(69, 96, 46, 14);
//		panel.add(lblNewLabel_1);
//		
//		
//	    JFormattedTextField txtPort = new JFormattedTextField();
//	    txtPort.addKeyListener(new KeyAdapter() {
//	        public void keyTyped(KeyEvent e) {
//	            char c = e.getKeyChar();
//	            if (!((c >= '0') && (c <= '9') ||
//	               (c == KeyEvent.VK_BACK_SPACE) ||
//	               (c == KeyEvent.VK_DELETE))) {  
//	              e.consume();
//	            }
//	          }
//	        });
//		txtPort.setFont(new Font("Times New Roman", Font.PLAIN, 13));
//		txtPort.setBounds(156, 90, 107, 20);
//		panel.add(txtPort);
//		btnLogin.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				String name= txtName.getText();
//				String port= txtPort.getText();
//				if(name.isEmpty()|| port.isEmpty() ) {
//					JOptionPane.showMessageDialog(frame,"Nhập đầy đủ các thông số");
//				}
//				else {
//					setUserName(name);
//					setPort(port);
//					setIsLogin();
//					JOptionPane.showMessageDialog(frame,"Thành công");
//				}
//				 
//			}
//		});
//		return panel;
//		
//	}
//
//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		
//	}
//}
