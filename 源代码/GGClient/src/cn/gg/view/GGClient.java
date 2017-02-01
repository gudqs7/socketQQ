package cn.gg.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cn.gg.comon.Message;
import cn.gg.comon.MessageType;

public class GGClient extends JFrame implements ActionListener, WindowListener,
		Runnable {

	private static final long serialVersionUID = -1802024087582007881L;

	// 定义需要的东东
	JTextArea jtaRecord;
	ScrollPane sp;
	JPanel jplFoot;
	JButton jbtnSend;
	JTextField jtfWord;
	JLabel jlblTip;
	@SuppressWarnings("rawtypes")
	JComboBox jcboFriend;

	String ownerId;
	String friendId;

	private Socket socket;

	@SuppressWarnings("rawtypes")
	public GGClient(String ownerId, Socket s,ObjectOutputStream write) {
		this.ownerId = ownerId;
		this.socket = s;
		this.write=write;
		sp = new ScrollPane();
		jtaRecord = new JTextArea();

		jplFoot = new JPanel(new FlowLayout());
		jbtnSend = new JButton("发送");
		jbtnSend.addActionListener(this);
		jlblTip = new JLabel("输入信息: ");
		jtfWord = new JTextField(12);

		jcboFriend = new JComboBox();

		jplFoot.add(jcboFriend);
		jplFoot.add(jlblTip);
		jplFoot.add(jtfWord);
		jplFoot.add(jbtnSend);

		sp.add(jtaRecord);

		this.add(jplFoot, BorderLayout.SOUTH);
		this.add(sp);

		this.setTitle("代表自己: " + ownerId);
		this.setBounds(300, 100, 400, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(this);
		this.setVisible(true);
		Thread t = new Thread(this);
		t.start();
	}
	
	private int count=0;

	@SuppressWarnings("unchecked")
	public void updateFriend(String[] friends) {
		if(count!=0){
			JOptionPane.showMessageDialog(this,"您的好友"+friends[0]+"上线了!","上线提示",JOptionPane.INFORMATION_MESSAGE);
		}
		for (String friend : friends) {
			jcboFriend.addItem(friend);
		}
		count++;
	}

	public void sendMsgToYou(Message msg) {
		jtaRecord.append(msg.getSender() + "    " + msg.getDate() + "\r\n");
		jtaRecord.append("   " + msg.getContent() + "\r\n");
	}

	SimpleDateFormat formater = new SimpleDateFormat("YYYY/MM/dd/ hh:mm:ss");

	ObjectOutputStream write;

	public void actionPerformed(ActionEvent e) {
		Message msg = new Message();
		friendId = jcboFriend.getSelectedItem().toString();
		msg.setMessageType(MessageType.chat_msg);
		msg.setGetter(friendId);
		msg.setSender(ownerId);
		if(friendId.equals(ownerId)){
			JOptionPane.showMessageDialog(this,"您正在和自己发送消息!","提示",JOptionPane.INFORMATION_MESSAGE);
		}
		msg.setContent(jtfWord.getText());
		msg.setDate(formater.format(new Date()));
		if (e.getSource() == jbtnSend) {
			String str = msg.getSender() + " 给 " + msg.getGetter() + "  发送消息:"
					+ msg.getContent();
			System.out.println(str);
			jtaRecord.append(ownerId + "    " + msg.getDate() + "\r\n");
			jtaRecord.append("   " + msg.getContent() + "\r\n");
			
			jtfWord.setText("");
			try {
				write.writeObject(msg);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	ObjectInputStream read;

	public void run() {
		try {
			while (true) {
				read = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) read.readObject();
				if (msg.getMessageType() == MessageType.chat_msg) {
					sendMsgToYou(msg);
				} else if (msg.getMessageType() == MessageType.returnfriend) {
					String[] friends = msg.getContent().split("_");
					updateFriend(friends);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			socket.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}