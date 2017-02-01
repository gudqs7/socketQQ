package cn.gg.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.gg.comon.Message;
import cn.gg.comon.MessageType;
import cn.gg.tool.ClientThreadManager;

@SuppressWarnings("serial")
public class GGLogin extends JFrame implements ActionListener{

	public static void main(String[] args) {
		new GGLogin();
	}
	
	JPanel jpl;
	JLabel jlblTip,jlblhost;
	JTextField jtxtId,jtxthost;
	JButton jbtnLogin;
	
	public GGLogin(){
		jpl=new JPanel();
		jlblTip=new JLabel("你的ID");
		jtxtId=new JTextField(12);
		jlblhost=new JLabel("连接服务器:");
		jtxthost=new JTextField(12);
		jtxthost.setText(hostip);
		jbtnLogin=new JButton("登录");
		jbtnLogin.addActionListener(this);
		
		jpl.add(jlblTip);
		jpl.add(jtxtId);
		jpl.add(jbtnLogin);
		
		this.add(jpl);
		
		this.setBounds(300,100,300,100);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private String hostip="127.0.0.1";
	
	public void actionPerformed(ActionEvent e) {
//		if(jtxthost.getText().trim().equals("")){
//			
//			return;
//		}
		//hostip=jtxthost.getText();
		Socket socket;
		try {
			socket = new Socket(hostip,6666);
			ObjectOutputStream write=new ObjectOutputStream(socket.getOutputStream());
			Message msg=new Message();
			msg.setMessageType(MessageType.checklogin);
			msg.setSender(jtxtId.getText());
			msg.setContent("kkkkkk");
			write.writeObject(msg);
			
			//读取返回登录验证信息
			ObjectInputStream read=new ObjectInputStream(socket.getInputStream());
			msg=(Message)read.readObject();
			if(msg.getMessageType()==MessageType.checklogin){
				if(msg.getContent().equals("ok")){

					msg.setMessageType(MessageType.getfriend);
					msg.setSender(msg.getGetter());
					write.writeObject(msg);
					GGClient gl= new GGClient(jtxtId.getText(),socket,write);
					ClientThreadManager.addGGClient(jtxtId.getText(), gl);

					this.setVisible(false);
				}else{
					JOptionPane.showMessageDialog(this,"您登录失败,可能是密码错误!","提示",JOptionPane.INFORMATION_MESSAGE);
				}
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this,"登录错误,服务器异常,请联系管理员开启服务!","提示",JOptionPane.INFORMATION_MESSAGE);
			e1.printStackTrace();
		} 
		
	}
}
