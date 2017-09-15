package chat;

import javax.swing.*;
import javax.swing.border.*;
 
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.util.*;
 
public class ChatUI{
  private Client client;
  private IServer server;
  public void doConnect()
  {
	    if (connect.getText().equals("Conectar"))
	    {
	    	if (name.getText().length()<2)
	    	{
	    		JOptionPane.showMessageDialog(frame, "Por favor, digite um nome.");
	    		
	    		return;
	    	}
	    	
	    	if (ip.getText().length()<2)
	    	{
	    		JOptionPane.showMessageDialog(frame, "Por favor, digite um IP."); 
	    		
	    		return;
	    	}	    	
	    	try{
				client = new Client(name.getText());
	    		client.setGUI(this);
	    		
				server = (IServer)Naming.lookup("rmi://"+ip.getText()+":" + port.getText() + "/chat");
				server.login(client);
				
				updateUsers(server.getConnected());
			    connect.setText("Desconectado");
			    
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		
	    		JOptionPane.showMessageDialog(frame, "Erro, problema ao tentar conectar. " + e.getMessage());	    	
	    	}		  
	      }
	    	else
	    	{
	    	  	updateUsers(null);
	    	  	connect.setText("Conectar");
		}
	  }  
  
  public void sendText()
  {
    if (connect.getText().equals("Conectar"))
    {
    	JOptionPane.showMessageDialog(frame, "Por favor, conecte ao servidor."); 
    	
    	return;	
    }
      String st=tf.getText();
      st="["+name.getText()+"]: "+st;
      tf.setText("");

      try
      {
    	  server.publish(st);
  	  }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
  }
 
  public void writeMsg(String st)
  {  
	  tx.setText(tx.getText()+"\n"+st);  
  }
 
  public void updateUsers(Vector v)
  {
      DefaultListModel listModel = new DefaultListModel();
      
      if(v != null) 
    	  for (int i=0; i < v.size(); i++)
    	  {    	  
    		  try
    		  {  
    			  String tmp = ((IClient)v.get(i)).getName();
    	  		  listModel.addElement(tmp);
    		  }
    		  catch(Exception e)
    		  {
    			  e.printStackTrace();
    		  }
      }
      lst.setModel(listModel);
  }
  
  public static void main(String [] args)
  {
	ChatUI c = new ChatUI();
  }  
  
  public ChatUI()
  {
	    frame=new JFrame("Keep Talking Chat");
	    JPanel main =new JPanel();
	    JPanel top =new JPanel();
	    JPanel cn =new JPanel();
	    JPanel bottom =new JPanel();
	    ip=new JTextField();
	    tf=new JTextField();
	    name=new JTextField();
	    port = new JTextField();
	    tx=new JTextArea();
	    connect=new JButton("Conectar");
	    JButton bt=new JButton("Enviar");
	    lst=new JList();        
	    main.setLayout(new BorderLayout(5,5));         
	    top.setLayout(new GridLayout(1,0,5,5));   
	    cn.setLayout(new BorderLayout(5,5));
	    bottom.setLayout(new BorderLayout(5,5));
	    top.add(new JLabel("Nome: "));top.add(name);   	    
	    top.add(new JLabel("IP do servidor: "));top.add(ip);
	    top.add(new JLabel("Porta do servidor: "));top.add(port);
	    top.add(connect);
	    cn.add(new JScrollPane(tx), BorderLayout.CENTER);        
	    cn.add(lst, BorderLayout.EAST);    
	    bottom.add(tf, BorderLayout.CENTER);    
	    bottom.add(bt, BorderLayout.EAST);
	    main.add(top, BorderLayout.NORTH);
	    main.add(cn, BorderLayout.CENTER);
	    main.add(bottom, BorderLayout.SOUTH);
	    main.setBorder(new EmptyBorder(10, 10, 10, 10) );
	    //Events
	    connect.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      { 
	    	  doConnect();   
	      }  
	    });
	    
	    bt.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      { 
	    	  sendText();   
	      }  
	    });
	    
	    tf.addActionListener(new ActionListener()
	    {
	      public void actionPerformed(ActionEvent e)
	      { 
	    	  sendText();   
	      }  
	    });
	    
	    frame.setContentPane(main);
	    frame.setSize(800,600);
	    frame.setVisible(true);  
	  }
  
	  JTextArea tx;
	  JTextField tf,ip, name, port;
	  JButton connect;
	  JList lst;
	  JFrame frame;
}