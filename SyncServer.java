import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import java.util.concurrent.locks.*;
public class SyncServer extends Thread{
	HashMap<Integer,HashMap<String,String>> portMap=new HashMap<Integer,HashMap<String,String>>();
	/*int port;
	public SyncServer(HashMap<String,String> portMap,int port){
	this.portMap=portMap;
	this.port=port;
	}*/
	public void run(){
	
		Lock lock = new ReentrantLock();
		
	try{
		/*Socket client = new Socket("localhost", 6);
		System.out.println("Inside Sync Server");
		OutputStream outToServer = client.getOutputStream();
         	DataOutputStream out = new DataOutputStream(outToServer); 
         	out.writeUTF("Server-"+port);
		InputStream inFromServer = client.getInputStream();
         	DataInputStream in = new DataInputStream(inFromServer);
		String updates=in.readUTF();
		if(!updates.equals("No Updates")){
		Gson gson=new Gson();
		
		HashMap hashMap=gson.fromJson(in.readUTF(),HashMap.class);
		lock.lock();
		for(Object obj : hashMap.entrySet()){
			Map.Entry entry=(Map.Entry)obj; 
			portMap.put((String)entry.getKey(),(String)entry.getValue());		
		}
		lock.unlock();
		}
		client.close();
		Thread.sleep(5000);*/
		String stringIp="localhost";
		System.out.println("************");
	   	InetAddress ip=InetAddress.getByName(stringIp);
      		ServerSocket serverSocket = new ServerSocket(7,6,ip);
		System.out.println("Waiting for client on port " + 
        	serverSocket.getLocalPort() + "...");
		while(true){
        	Socket server = serverSocket.accept();

         	System.out.println("Just connected to " + server.getRemoteSocketAddress());
         	DataInputStream in = new DataInputStream(server.getInputStream());
		String[] k=in.readUTF().split("-");
		String res="";
		if(k[0].equals("client")){
			Integer serverPort=Integer.parseInt(k[1]);
			HashMap<String,String> hashMap=portMap.get(serverPort);
			if(hashMap!=null){
				hashMap.put(k[2],k[3]);			
			}
			else{
				hashMap=new HashMap<String,String>();
				hashMap.put(k[2],k[3]);
			}
			res="Data kept in sync server";					
		}
		else{
			Integer serverPort=Integer.parseInt(k[1]);
			System.out.println("Port:"+serverPort);
			HashMap<String,String> hashMap=portMap.get(serverPort);
			if(hashMap!=null){
				for(Map.Entry<String,String> map : hashMap.entrySet()){
					try{
						Socket client = new Socket("localhost", serverPort);
						OutputStream outToServer = client.getOutputStream();
         	 				DataOutputStream out = new DataOutputStream(outToServer); 
         					out.writeUTF("put-"+map.getKey()+"-"+map.getValue());

						InputStream inFromServer = client.getInputStream();
         					in = new DataInputStream(inFromServer);
						res=in.readUTF();
						client.close();
						hashMap.remove(map.getKey());
				
		//break;
					}
					catch(Exception e){
						System.out.println(e);
					}
			
			}
			//res="Data successfully synced";
			}
		
		}
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(res);
            server.close();
	
	
	
				
			
	}

}
catch(Exception e){
			System.out.println("Sync Server is not active:"+e);		
}
}
}
