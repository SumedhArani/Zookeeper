// File Name GreetingClient.java
import java.net.*;
import java.io.*;
import com.google.gson.*;

public class GreetingClient {

   public static void main(String [] args) {
      String serverName = "127.0.1.1";
      int port =11;
      
      
      try {
    	  
         System.out.println("Connecting to " + serverName + " on port " + port);
         Socket client = new Socket(serverName, port);
         
         /*System.out.println("Just connected to " + client.getRemoteSocketAddress());
         OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer);
         
         out.writeUTF("Hello from " + client.getLocalSocketAddress());
         InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
         
         System.out.println("Server says " + in.readUTF());
	out.writeUTF("Hello from " + client.getLocalSocketAddress());*/

	System.out.println(get("1",client));
	client.close();
	client = new Socket(serverName, port);
	TrafficCount trafficCount=new TrafficCount(4,"August",66);
	Gson gson=new Gson();
	put("4",gson.toJson(trafficCount).toString(),client);
	client.close();	
         
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
public static String get(String key,Socket client){
	String res="";
	try{
	OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer); 
         out.writeUTF("get-"+key);
	
	InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
	res=in.readUTF();
	}
	catch(Exception e){
		res=e.toString();	
	}
return res;

}
public static void put(String key,String Value,Socket client){
	try{
	OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer); 
         out.writeUTF("put-"+key+"-"+Value);

	InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
	System.out.println(in.readUTF());
	}
	catch(Exception e){
		System.out.println(e);	
	}

}
}
