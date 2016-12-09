import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import java.util.concurrent.locks.*;
public class MasterServer extends Thread {
	public void run(){
	Lock lock = new ReentrantLock();
	
	while(true){
	lock.lock();
	String serverName = "localhost";
		//int seq=zookeeper.findMasterServer();
		int i=1;
		int seq=0;
		Integer master=null;
		String helper="";
		zookeeper1.seqServer=new HashMap<Integer,Integer>();
	while(i<=3){
			String ip="localhost";
	
			
			//String res="";
	//int v=10;
	try{
	Socket client = new Socket(ip, (9+i));
	client.setSoTimeout(2000);
	OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer); 
         out.writeUTF("zookeeper");
	//System.out.println("before active");
	InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
	if(in.readUTF().equals("active")){
		//activeServers.add(ip);
		//System.out.println("after active");
		//flag=true;
		if(master==null){
			master=new Integer((9+i));		
		}
		/*else{
		znode p=master;
		while(p.getNext()!=null){
			p=p.getNext();					
		}
		p.setNext(new znode((9+i)));
		}*/
		
		int v=9+i;
		System.out.println("Server Port: "+v+" is active");
		zookeeper1.seqServer.put(seq++,v);
		helper+="-"+v;	
	}
	client.close();
	}
	catch(Exception e){
		//res=e.toString();
		System.out.println("Server: "+(9+i)+" is not active");	
	}

	i++;	
	}
	if(master!=null){
      		int port =master;
		try{
		Socket client = new Socket(serverName, port);
		System.out.println("--------------------");
		OutputStream outToServer = client.getOutputStream();
         	DataOutputStream out = new DataOutputStream(outToServer); 
         	out.writeUTF("Masterzookeeper"+helper);
		InputStream inFromServer = client.getInputStream();
         	DataInputStream in = new DataInputStream(inFromServer);
		Gson gson=new Gson();
		
		zookeeper1.keyServer=gson.fromJson(in.readUTF(),HashMap.class);
		
		client.close();
		}
		catch(Exception e){
			System.out.println("Master is not active");		
		}		
	
	System.out.println("----------------");
	lock.unlock();
	
	}
	try{
	Thread.sleep(5000);
	}
	catch(Exception e){
		System.out.println(e);	
	}
	}	
	}
					
}
