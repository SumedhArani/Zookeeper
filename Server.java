// File Name GreetingServer.java
import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

/*class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   
   public GreetingServer(int port) throws IOException {
	   String stringIp="localhost";
	   InetAddress ip=InetAddress.getByName("localhost");
      serverSocket = new ServerSocket(port,6,ip);
      serverSocket.setSoTimeout(100000);
   }

   public void run() {
	 
	HashMap<String,String> hashMap=new HashMap<String,String>();
	Gson gson=new Gson();
	hashMap.put("1",gson.toJson(new TrafficCount(1,"August",45)).toString());
	hashMap.put("2",gson.toJson(new TrafficCount(2,"August",53)).toString());
	hashMap.put("3",gson.toJson(new TrafficCount(3,"August",65)).toString());
	//hashMap.put("1",gson.toJson(new TrafficCount(1,"August",45)).toString());
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
           Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            String[] k=in.readUTF().split("-");
		String res="";
	if(k[0].equals("get")){
		res=hashMap.get(k[1]);
		if(res==null){
			res="No such element";		
		}
	
	}
	else{
		try{
			//System.out.println(k[1]+k[2]);			
			hashMap.put(k[1],k[2]);
			res="Element Inserted";
			for(Map.Entry<String,String> entry : hashMap.entrySet()){
				System.out.println(entry.getKey()+":"+entry.getValue());			
			}		
		}
		catch(Exception e){
			res=e.toString();
		}	
	}
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + res
               + "\nGoodbye!");
            server.close();
            
         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   
}*/
class Server{
		public static void main(String [] args) {
      //int port = Integer.parseInt(args[0]);
	//Scanner in=new Scanner(System.in);
      try {
	Thread t4 = new SyncServer();
         t4.start();
         GreetingServer t1 = new GreetingServer(10);
         t1.start();
	GreetingServer t2 = new GreetingServer(11);
         t2.start();
	GreetingServer t3 = new GreetingServer(12);
         t3.start();
	GreetingServer t5=null;
	//Control.controlThread(t1,t2,t3);
         //t5.start();
	String stringIp="localhost";
	   InetAddress ip=InetAddress.getByName("localhost");
      ServerSocket serverSocket = new ServerSocket(1,6,ip);
	while(true){
		System.out.println("Waiting for client on port 1");
		try{
		Socket server = serverSocket.accept();
            
            	System.out.println("Just connected to " + server.getRemoteSocketAddress());
            	DataInputStream in = new DataInputStream(server.getInputStream());
		String[] k=in.readUTF().split("-");
		String res="";
		if(k[1].equals("10"))
			t5=t1;
		else if(k[1].equals("11"))
			t5=t2;
		else if(k[1].equals("12"))
			t5=t3;
		if(t5==null){
			res="Incorrect Port";		
		}
		else if(k[0].equals("stop")){
			t5.suspend();
			res="Thank you for connecting  " + "Request Acknowleged"
               + "\nGoodbye!";
			
			//t1.join();
		}
		else{
			t5.resume();
			res="Thank you for connecting  " + "Request Acknowleged"
               + "\nGoodbye!";
		}
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(res);
            server.close();
		//Thread.sleep(10000);
		//t1.join();
		//t1=new GreetingServer(10);
		//t1.start();
		}
		catch(Exception z){
			System.out.println(z);
		}
	}
		//int a=in.nextInt();
		/*if(a==1)
			t1.suspend();
		else if(a==2)
			t1.resume();
		else if(a==3)
			t2.suspend();
		else if(a==4)
			t1.resume();*/

	
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}

