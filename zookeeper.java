import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
class zookeeper{
	//private ArrayList<String> activeServers=new ArrayList<String>();
	//private MasterServer masterServer;
	public static  znode master=null;
	private static HashMap keyServer=null;
	private static String helper="";
	public static  znode findMasterServer(){
		//boolean flag=false
		//String master=null;
		
		int i=1;
		while(i<=3){
			String ip="localhost";
			
			//String res="";
	try{
	Socket client = new Socket(ip, (9+i));
	OutputStream outToServer = client.getOutputStream();
         DataOutputStream out = new DataOutputStream(outToServer); 
         out.writeUTF("zookeeper");
	
	InputStream inFromServer = client.getInputStream();
         DataInputStream in = new DataInputStream(inFromServer);
	if(in.readUTF().equals("active")){
		//activeServers.add(ip);
		//flag=true;
		if(master==null){
			master=new znode((9+i));		
		}
		else{
		znode p=master;
		while(p.getNext()!=null){
			p=p.getNext();					
		}
		p.setNext(new znode((9+i)));
		}
		System.out.println("Server:"+ip+"is active");
		int v=9+i;
		helper+="-"+v;	
	}
	client.close();
	}
	catch(Exception e){
		//res=e.toString();
		System.out.println("Server:"+ip+"is not active");	
	}

	i++;	
	}
	return master;	
	}
	public static void main(String[] args){
		String serverName = "localhost";
		znode master=zookeeper.findMasterServer();
      		int port =master.port;
		try{
		Socket client = new Socket(serverName, port);
		System.out.println("--------------------");
		OutputStream outToServer = client.getOutputStream();
         	DataOutputStream out = new DataOutputStream(outToServer); 
         	out.writeUTF("Masterzookeeper"+helper);
		InputStream inFromServer = client.getInputStream();
         	DataInputStream in = new DataInputStream(inFromServer);
		Gson gson=new Gson();
		keyServer=gson.fromJson(in.readUTF(),HashMap.class);
		client.close();
		}
		catch(Exception e){
			System.out.println("Master is not active");		
		}		
	
	System.out.println("----------------");
	
	try{
	String stringIp="localhost";
	System.out.println("************");
	   	InetAddress ip=InetAddress.getByName(stringIp);
      		ServerSocket serverSocket = new ServerSocket(6,6,ip);
	System.out.println("Waiting for client on port " + 
        serverSocket.getLocalPort() + "...");
	while(true){
        Socket server = serverSocket.accept();
            
         System.out.println("Just connected to " + server.getRemoteSocketAddress());
         DataInputStream in = new DataInputStream(server.getInputStream());
	String[] k=in.readUTF().split("-");
		String res="";
	if(k[0].equals("get")){
		ArrayList a=null;
		a=(ArrayList)keyServer.get(k[1]);
		if(a==null){
			res="No such element";		
		}
		else{
		Gson gson=new Gson();
		
		res=gson.toJson(a).toString();
		}
		
	
	}
	//if(k[0].equals(""))
	DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(res
         );
            server.close();
	}
	}
	catch(Exception e){
		System.out.println(e);	
	}
	
					
	}	
	
	
}
