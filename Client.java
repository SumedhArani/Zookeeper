// File Name Client.java
import java.net.*;
import java.io.*;
public class Client
{
	private String serverName;
	private int port;
	private Socket client;

	Client(String s, String portNumber)
	{
		serverName =  s;
		port = Integer.parseInt(portNumber);
		
	}

	Client(String s, int portNumber)
	{	
		serverName = s;
		port = portNumber;
		
 	}

	public String getServerName()
	{
		return this.serverName;
	}

	public int getPortNumber()
	{
		return this.port;
	}

	public Socket getClientSocket()
	{
		return this.client;
	}

	public void connect()
	{
		try
		{
			System.out.println("Connecting to " + serverName + " on port " + port);
			this.client = new Socket(this.serverName, this.port);
			System.out.println("Just connected to " + client.getRemoteSocketAddress());	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void send(String msg)
	{
		try
		{
			OutputStream outToServer = this.client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer); 
			out.writeUTF(msg);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

	public void receive()
	{
		try
		{
			InputStream inFromServer = this.client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			System.out.println("Server: " + in.readUTF());
		}
		catch (IOException e) 
		{
			e.printStackTrace();	
		}
	}

	public void close()
	{
		try
		{
			this.client.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();	
		}
	}
}