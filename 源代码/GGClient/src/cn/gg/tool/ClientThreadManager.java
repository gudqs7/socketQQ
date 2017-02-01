package cn.gg.tool;

import java.util.HashMap;

import cn.gg.view.GGClient;

public class ClientThreadManager{
	
	private static HashMap<String, GGClient> ht=new HashMap<String, GGClient>();
	
	public static void addGGClient(String key, GGClient value){
		ht.put(key, value);
	}
	
	public static GGClient getGGClient(String key){
		return ht.get(key);
	}
	
}
