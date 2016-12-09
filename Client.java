// File Name GreetingClient.java
import java.net.*;
import java.io.*;
import com.google.gson.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class Client {

   public static void main(String [] args) {
      String serverName = "localhost";
      int port =2;
	

      
      
      try {
    	  
         System.out.println("Connecting to " + "localhost" + " on port " + port);
         Socket client = new Socket(serverName, port);
	
	System.out.println(get("1",client));
	
	client= new Socket(serverName, port);
	//client.setSoTimeout(2000);
	TrafficCount trafficCount=new TrafficCount(5,"August",106);
	Gson gson=new Gson();
	put("5",gson.toJson(trafficCount).toString(),client);


	 try
      {
	TimeUnit.SECONDS.sleep (7);
      }
      catch (Exception e)
      {
	System.out.println (e);
      }
	client= new Socket(serverName, port);
	System.out.println(get("5",client));

      }catch(IOException e) {
         e.printStackTrace();
      }

	
   }
public static String get(String key,Socket client){
	
	ArrayList servers=null;
	String res="";
	try{
	client.setSoTimeout(2000);
	OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer); 
         out.writeUTF("get-"+key);
	
	InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
	Gson gson=new Gson();
	String k=in.readUTF();
	if(k.equals("Cluster is down"))
		res="Cluster is down";
	else{
	servers=gson.fromJson(k,ArrayList.class);
	
	
	for(Object obj : servers){
		Double d=(Double)obj;
		Integer port=d.intValue();
		System.out.println("Port:"+port);
		Scanner scan=new Scanner(System.in);
		int x=scan.nextInt();
	
		try{
		client = new Socket("localhost", port);
		client.setSoTimeout(2000);
		outToServer = client.getOutputStream();
         	 out = new DataOutputStream(outToServer); 
         	out.writeUTF("get-"+key);

		inFromServer = client.getInputStream();
         	in = new DataInputStream(inFromServer);
		res=in.readUTF();
		client.close();
		break;
		}
		catch(Exception e){
			System.out.println("Server not available "+port);		
		}	
	}
	client.close();
	}
	}
	catch(Exception e){
		res="Data not found";	
	}
	
return res;

}
public static void put(String key,String Value,Socket client){
	System.out.println("--------Inside Put----------");
	ArrayList servers=null;
	String res="";
	try{
	client.setSoTimeout(2000);
	OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer); 
         out.writeUTF("put-"+key+"-"+Value);

	/*InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
	System.out.println(in.readUTF());*/
	InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
	Gson gson=new Gson();
	String k=in.readUTF();
	if(k.equals("Cluster is down"))
		System.out.println("Cluster is down");
	else{	
	servers=gson.fromJson(k,ArrayList.class);
	
	
	for(Object obj : servers){
		Double d=(Double)obj;
		Integer port=d.intValue();
		System.out.println("Port:"+port);
		Scanner scan=new Scanner(System.in);
		int x=scan.nextInt();
		try{
		client = new Socket("localhost", port);
		client.setSoTimeout(2000);
		outToServer = client.getOutputStream();
         	 out = new DataOutputStream(outToServer); 
         	out.writeUTF("put-"+key+"-"+Value);
		
		inFromServer = client.getInputStream();
         	in = new DataInputStream(inFromServer);
		res=in.readUTF();
		client.close();
		//break;
		}
		catch(Exception e){
			//System.out.println(e);
					try{
						client = new Socket("localhost", 7);
						outToServer = client.getOutputStream();
         	 				out = new DataOutputStream(outToServer); 
         					out.writeUTF("client-"+port+"-"+key+"-"+Value);

						inFromServer = client.getInputStream();
         					in = new DataInputStream(inFromServer);
						System.out.println(in.readUTF());
						client.close();
				
		//break;
					}
					catch(Exception z){
						System.out.println(z);
					}
					
		}	
	}
	client.close();
	}
	}
	catch(Exception e){
		System.out.println(e);	
	}

}
}
