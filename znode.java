public class znode{
	public int port;
	private znode next=null;
	public znode(int port){
		this.port=port;
	}
	public int getPort(){
		return port;	
	}
	public znode getNext(){
		return next;	
	}
	public void setNext(znode z){
		next=z;	
	}
}

