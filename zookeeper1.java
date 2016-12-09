import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
import java.util.concurrent.locks.*;
class zookeeper1{
	//private ArrayList<String> activeServers=new ArrayList<String>();
	//private MasterServer masterServer;
	public static  znode master=null;
	public static HashMap keyServer=null;
	public static HashMap<Integer,HashMap> syncServer=new HashMap<Integer,HashMap>();
	public static HashMap<Integer,Integer> seqServer=null;
	private static String helper="";
	/*public static  int findMasterServer(){
		//boolean flag=false
		//String master=null;
		
	return seq;	
	}*/
	public static void main(String[] args){
		Lock lock = new ReentrantLock();
		Gson gson=new Gson();
		Thread t=new MasterServer();
		t.start();	
	
	try{
	String stringIp="localhost";
	System.out.println("************");
	   	InetAddress ip=InetAddress.getByName(stringIp);
      		ServerSocket serverSocket = new ServerSocket(2,6,ip);
	System.out.println("Waiting for client on port " + 
        serverSocket.getLocalPort() + "...");
	while(true){
        Socket server = serverSocket.accept();

         System.out.println("Just connected to " + server.getRemoteSocketAddress());
         DataInputStream in = new DataInputStream(server.getInputStream());
	String[] k=in.readUTF().split("-");
		String res="";
	if(seqServer.size()<2){
		res="Cluster is down";	
	}
	
	else if(k[0].equals("get")){
		ArrayList a=null;
		lock.lock();
		a=(ArrayList)keyServer.get(k[1]);
		lock.unlock();
		if(a==null){
			res="No such element";		
		}
		else{
		
		
		res=gson.toJson(a).toString();
		}
		
	
	}
	
	
	else if(k[0].equals("put")){
	System.out.println("-------------------------------------Inside Server------------------------------------");
	int maximum=seqServer.size()-1;
	int randomNum = 0 + (int)(Math.random() * maximum);
	int replica=(randomNum+1)%(seqServer.size());
	ArrayList<Integer> arrayList=new ArrayList<Integer>();
	arrayList.add(seqServer.get(randomNum));
	arrayList.add(seqServer.get(replica));
	res=gson.toJson(arrayList).toString();
		/*lock.lock();
			try{
			Integer writePort=seqServer.get(randomNum);
			if(writePort!=null){
				HashMap hashMap=syncServer.get(writePort);
				hashMap.put(k[1],k[2]);			
			}
			else{
				HashMap hashMap=new HashMap();
				hashMap.put(k[1],k[2]);
				syncServer.put(randomNum,hashMap);
			}
			writePort=seqServer.get(replica);
			if(writePort!=null){
				HashMap hashMap=syncServer.get(writePort);
				hashMap.put(k[1],k[2]);				
			}
			else{
				HashMap hashMap=new HashMap();
				hashMap.put(k[1],k[2]);
				syncServer.put(replica,hashMap);
			}
			}
			catch(Exception e){
				System.out.println(e);			
			}
			
		lock.unlock();*/
		//res="Element Inserted"; 
	}
	DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(res);
            server.close();
	}
	}
	catch(Exception e){
		System.out.println(e);				
	}	
	}
	
	
	
}
