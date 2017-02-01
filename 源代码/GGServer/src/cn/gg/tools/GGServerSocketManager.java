package cn.gg.tools;

import java.net.Socket;
import java.util.HashMap;

public class GGServerSocketManager {
	
	private static HashMap<String,Socket> ht=new HashMap<String,Socket>();
	
	public static void addServerSocket(String key,Socket value){
		ht.put(key, value);
	}
	
	public static Socket getServerSocket(String key){
		return ht.get(key);
	}
	
	public static String[] getServerSocketList(){
		String[]list=new String[ht.size()];
		int i=0;
		for(String s : ht.keySet()){
			list[i++]=s;
		}
		return list;
	}
	
}
