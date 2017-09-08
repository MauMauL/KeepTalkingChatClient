package chat;

import java.rmi.*;
import java.util.*;

public interface IServer extends Remote {

	public boolean login (IClient ic)throws RemoteException ;
	public void publish (String s)throws RemoteException ;
	public Vector getConnected() throws RemoteException ;
	
}
