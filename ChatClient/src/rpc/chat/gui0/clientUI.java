package rpc.chat.gui0;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JDesktopPane;
import java.awt.FlowLayout;
import java.awt.Cursor;
import javax.swing.BoxLayout;

public class clientUI extends JFrame {

	private JPanel mainPane;
	private JTextField chatField;

	/**
	 * Launch the application.
	 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					clientUI frame = new clientUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/
	/**
	 * Create the frame.
	 */
	public clientUI() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setTitle("Messenger");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 602, 388);
		
		// Create a mainPane
		mainPane = new JPanel();
		mainPane.setBackground(new Color(128, 255, 255));
		mainPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(mainPane);
		mainPane.setLayout(null);
		
		// Create a scrollPane model
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		// Configuration for scrollPane
		scrollPane.setBounds(30, 50, 465, 164);
		mainPane.add(scrollPane);
		
		// Create a chatArea model
		JTextArea chatArea = new JTextArea();
		chatArea.setLineWrap(true);
		chatArea.setEditable(false);
		scrollPane.setViewportView(chatArea);
		// Configuration for chatArea
		chatArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		JLabel messageLb = new JLabel("Message:");
		messageLb.setFont(new Font("Tahoma", Font.PLAIN, 13));
		messageLb.setBounds(30, 223, 63, 25);
		mainPane.add(messageLb);
		
		chatField = new JTextField();
		chatField.setFont(new Font("Tahoma", Font.PLAIN, 13));
		chatField.setBounds(96, 224, 304, 25);
		mainPane.add(chatField);
		chatField.setColumns(10);
		
		// Button Send
		JButton sendBtn = new JButton("Send");
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {						
				try {
					if (chatField.getText().equals(""))
						return;
					else {
						chatArea.setText(chatArea.getText() + "\n" + "Hieu: " + chatField.getText());
					
					// Create a animator scroll
					scrollChat scrollAnimator = new scrollChat(scrollPane);
					// Get value of scrcollVertival and do animation of scroll VerticalScrollBar
					scrollAnimator.scrollVertical(scrollPane, scrollPane.getVerticalScrollBar().getValue());
					// Get value of scrcollHorizontal and do animation of scroll HorizontalScrollBar
					scrollAnimator.scrollHorizontal(scrollPane, scrollPane.getHorizontalScrollBar().getValue());
					
					}	
				} catch (Exception e1) {
				}
			}
		});
		sendBtn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		sendBtn.setBounds(410, 224, 85, 25);
		mainPane.add(sendBtn);
		
		
		JPanel header = new JPanel();
		FlowLayout flowLayout = (FlowLayout) header.getLayout();
		header.setBounds(30, 26, 465, 25);
		mainPane.add(header);
		header.setBackground(new Color(0, 255, 64));
		header.setForeground(new Color(240, 240, 240));
	}
}
