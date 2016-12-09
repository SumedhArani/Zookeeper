import java.util.concurrent.TimeUnit;
import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;
class Control extends Thread{
	
	public static void main(String[] args){
	Scanner scan=new Scanner(System.in);
	while(true){
	System.out.println("Write server port for stopping the server");
	int y=scan.nextInt();
	System.out.println("Press 1 for stop and any other key for stop");
	int x=scan.nextInt();
	String v="";
	if(x==1)
		v="stop-"+y;
	else
		v="start-"+y;
		
	try{
						Socket client = new Socket("localhost", 1);
						OutputStream outToServer = client.getOutputStream();
         	 				DataOutputStream out = new DataOutputStream(outToServer); 
         					out.writeUTF(v);

						InputStream inFromServer = client.getInputStream();
         					DataInputStream in = new DataInputStream(inFromServer);
						System.out.println(in.readUTF());
						client.close();
						//hashMap.remove(map.getKey());
				
		//break;
					}
					catch(Exception e){
						System.out.println(e);
					}
	}
	}
}
