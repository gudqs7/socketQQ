package cn.gg.view;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cn.gg.tools.ServerSocketThread;

public class GGServer extends JFrame implements Runnable, ActionListener {

	private static final long serialVersionUID = -1802024087582007881L;

	public static void main(String[] args) {
		new GGServer();

	}
	
	public static GGServer gg;

	// 定义需要的东东
	JPanel jplTop;
	JButton jbtnOpen, jbtnClose;
	ScrollPane sp;
	JTextArea jTextArea;

	ServerSocket serversocket = null;

	public GGServer() {

		jplTop = new JPanel();
		jplTop.setSize(200, 200);

		jbtnOpen = new JButton("打开服务器");
		jbtnOpen.addActionListener(this);
		jbtnOpen.setActionCommand("open");
		jbtnClose = new JButton("关闭服务器");
		jbtnClose.addActionListener(this);
		jbtnClose.setActionCommand("close");
		
		jTextArea=new JTextArea(20,10);
		jTextArea.setText(loggerStr);
		
		sp=new ScrollPane();
		sp.add(jTextArea);

		jplTop.add(jbtnOpen);
		jplTop.add(jbtnClose);
		

		this.add(jplTop, BorderLayout.NORTH);
		this.add(sp,BorderLayout.SOUTH);
		
		this.setBounds(300, 100, 400, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		
		gg=this;
	}
	
	public static String loggerStr="";

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == jbtnOpen) {

				openServer();

			} else if (e.getSource() == jbtnClose) {

				closeServer();

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void openServer() throws IOException {
		Thread t=new Thread(this);
		t.start();
	}
	
	public void putLoggerInfo(){
		loggerStr+="\r\n";
		jTextArea.setText(loggerStr);
	}

	public void closeServer() throws IOException {
		serversocket.close();
		System.out.println("服务器已关闭端口6666的监听.........");
		loggerStr+="服务器已关闭端口6666的监听.........";
		putLoggerInfo();
		JOptionPane.showMessageDialog(this,"服务已经关闭!","成功",JOptionPane.INFORMATION_MESSAGE);
		jbtnOpen.setEnabled(true);
	}

	public void run() {
		try {
			serversocket = new ServerSocket(6666);
			System.out.println("服务器正在端口6666监听.........");
			loggerStr+="服务器正在端口6666监听.........";
			putLoggerInfo();
			JOptionPane.showMessageDialog(this,"服务已经打开,点击确定继续!","成功",JOptionPane.INFORMATION_MESSAGE);
			jbtnOpen.setEnabled(false);
			while (true) {
				Socket s = serversocket.accept();
				ServerSocketThread sst=new ServerSocketThread(s);
				sst.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
