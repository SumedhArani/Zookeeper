import java.net.*;
import java.io.*;
import java.util.*;
public class Application extends Thread implements Runnable
{
	private String serverName;
	private int port;

	public Application(String serverName, String port)
	{
		this.serverName = serverName;
		this.port = Integer.parseInt(port);
	}

	public Application(String serverName, int port)
	{
		this.serverName = serverName;
		this.port = port;
	}

	public void run()
	{
    	Client c1 = new Client(this.serverName, this.port);
    	c1.connect();
		String msg ="Hello from";
		c1.send(msg+c1.getClientSocket().getLocalSocketAddress());
		c1.receive();
		c1.close();
	}
	public static void main(String [] args)
	{
		//Scanner scanIn = new Scanner(System.in);
       	//sWhatever = scanIn.nextLine();
		String serverName = args[0];
	    int port = Integer.parseInt(args[1]);
		Thread t = new Thread(new Application(serverName, port));
	    t.start();	
	}
}