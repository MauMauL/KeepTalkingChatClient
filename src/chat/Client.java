package chat;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
 
public class Client  extends UnicastRemoteObject implements IClient{
	
	private static final long serialVersionUID = 6809372179943232435L;
	private String name;
	private ChatUI ui;	
	public Client (String n) throws RemoteException {
		name = n;
		}
	
	public void tell(String st) throws RemoteException{
		System.out.println(st);
		ui.writeMsg(st);
	}
	public String getName() throws RemoteException{
		return name;
	}
	
	public void setGUI(ChatUI t) { 
		ui = t ; 
	} 	
}