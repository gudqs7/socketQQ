package cn.gg.comon;

public class Message implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2845285600136690735L;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getMessageType() {
		return MessageType;
	}

	public void setMessageType(int messageType) {
		MessageType = messageType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	private int MessageType;
	
	private String content;
	
	private String sender;
	
	private String getter;
	
	private String date;
	
}
