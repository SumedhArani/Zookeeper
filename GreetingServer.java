import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

/*class TrafficCount{
	int reportId;
	String month;
	int vehicleCount;
	public TrafficCount(int a,String b,int c){
		reportId=a;
		month=b;
		vehicleCount=c;
			
	}
}*/

public class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   //private  int sequenceNumber=0;
   private HashMap<String,ArrayList<Integer>> keyServer=null;
   private volatile boolean exit = false;
   private HashMap<String,String> hashMap=new HashMap<String,String>();
	private int serverPort=0;
   public GreetingServer(int port) throws IOException {
	   String stringIp="localhost";
	   InetAddress ip=InetAddress.getByName(stringIp);
		serverPort=port;
      serverSocket = new ServerSocket(port,6,ip);

      //serverSocket.setSoTimeout(100000);
   }

   public void run() {
	Gson gson=new Gson();
	hashMap.put("1",gson.toJson(new TrafficCount(1,"August",45)).toString());
	hashMap.put("2",gson.toJson(new TrafficCount(2,"August",53)).toString());
	hashMap.put("3",gson.toJson(new TrafficCount(3,"August",65)).toString());
	//Thread thread=new SyncServer(hashMap,serverPort);
	//thread.start();
					try{
						Socket client = new Socket("localhost", 7);
						OutputStream outToServer = client.getOutputStream();
         	 				DataOutputStream out = new DataOutputStream(outToServer); 
         					out.writeUTF("server-"+serverPort);

						InputStream inFromServer = client.getInputStream();
         					DataInputStream in = new DataInputStream(inFromServer);
						System.out.println(in.readUTF());
						client.close();
				
		//break;
					}
					catch(Exception e){
						System.out.println(e);
					}





	 
	
	
	//hashMap.put("1",gson.toJson(new TrafficCount(1,"August",45)).toString());
      while(!exit) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
           Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress()+":"+serverSocket.getLocalPort());
            DataInputStream in = new DataInputStream(server.getInputStream());
	String zoo=in.readUTF();
	System.out.println(zoo);
	String[] master=zoo.split("-");
	    
	if(zoo.equals("zookeeper")){
		
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
            	out.writeUTF("active");
            	server.close();	
	}
	
	else if(master[0].equals("Masterzookeeper")){
			
		//znode master=zookeeper.master;
		//znode p=master;
		//System.out.println("******"+p.port);
		System.out.println("Master Server Port: "+serverSocket.getLocalPort());
		int seq=1;
		keyServer=new HashMap<String,ArrayList<Integer>>();
		for(int i=1;i<master.length;i++){
			if(serverSocket.getLocalPort()!=Integer.parseInt(master[i])){
			 String ip="localhost";
			System.out.println("-------------------");
			Socket client = new Socket(ip,Integer.parseInt(master[i]));
			System.out.println("************");
			try{
	OutputStream outToServer = client.getOutputStream();
        DataOutputStream out = new DataOutputStream(outToServer); 
        out.writeUTF("MasterServer");
	
	InputStream inFromServer = client.getInputStream();
         in = new DataInputStream(inFromServer);
	//Gson gson=new Gson();
	Set set=gson.fromJson(in.readUTF(),Set.class);
	for(Object obj : set){
		String str=(String)obj;
		ArrayList<Integer> a=keyServer.get(str);
		if(a==null){
			a=new ArrayList<Integer>();
			a.add(Integer.parseInt(master[i]));
			keyServer.put(str,a);
		}
		else{
			a.add(Integer.parseInt(master[i]));		
		}
	}
	
	
	}
	catch(Exception e){
		System.out.println("This Port:"+" is not active");
	}
	client.close();
	//p=p.getNext();
	}
	else{
	Set s=hashMap.keySet();
	for(Object obj : s){
		String str=(String)obj;
		ArrayList<Integer> a=keyServer.get(str);
		if(a==null){
			a=new ArrayList<Integer>();
			a.add(Integer.parseInt(master[i]));
			keyServer.put(str,a);
		}
		else{
			a.add(Integer.parseInt(master[i]));		
		}
	}	
	}
	}
	for (Map.Entry<String, ArrayList<Integer>> entry : keyServer.entrySet())
	{
    		System.out.println(entry.getKey() + "/" + entry.getValue().toString());
	}
	//Gson gson=new Gson();
	DataOutputStream out = new DataOutputStream(server.getOutputStream());
        out.writeUTF(gson.toJson(keyServer).toString());
        server.close();
	
	}
	
	else if(zoo.equals("MasterServer")){
		//Gson gson=new Gson();
		System.out.println("--------");
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
            	out.writeUTF(gson.toJson(hashMap.keySet()).toString());
            	server.close();
	}
	/*else if(readUTF().equals("GetServerMapping")){
		Gson gson=new Gson();
		DataOutputStream out = new DataOutputStream(server.getOutputStream());
            	out.writeUTF(gson.ToJson(hashMap).toString());
            	server.close();
	}*/
	else{
            System.out.println("******");
            	String[] k=zoo.split("-");
		String res="";
	if(k[0].equals("get")){
		res=hashMap.get(k[1]);
		if(res==null){
			res="No such element";		
		}
	
	}
	else{
		try{
			//System.out.println(k[1],k[2])			
			hashMap.put(k[1],k[2]);
			res="Element Inserted";		
		}
		catch(Exception e){
			res=e.toString();
		}	
	}
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF("Thank you for connecting to " + res
               + "\nGoodbye!");
            server.close();
	}
            
         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            //break;
         }catch(IOException e) {
            e.printStackTrace();
            //break;
         }
      }
      //while(exit);
   }
   public void Stop(){
	exit=true;	
   }
   public void Start(){
	
	exit=false;
   }
   
   
}
