import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.swing.JOptionPane;

public class CHAT_SERVER
{
    
    public static ArrayList<Socket> ConnectionArray = new ArrayList<Socket>();
    public static ArrayList<String> CurrentUsers = new ArrayList<String>();
    
    public static void main(String args[]) throws IOException
    {
        try
        {
            final int PORT = 444;
            ServerSocket SERVER = new ServerSocket(PORT);
            System.out.println("Waiting for the client to join..");
            
            while(true)
            {
                Socket SOCK = SERVER.accept();
                ConnectionArray.add(SOCK);
                
                System.out.println("Client connected from " + SOCK.getLocalAddress().getHostName());
                
                AddUserName(SOCK);
                
                A_Chat_Server_Return CHAT = new A_Chat_Server_Return(SOCK);
                Thread X = new THREAD(CHAT);
                X.start(); 
                
                
            }
        }
        
        public static void AddUSerName(Socket X) throws IOException
        {
            Scanner INPUT = new Scacnner(X.getInputSteam());
            String UserName = INPUT.nextLine();
            CurrentUsers.add(UserName);
            
            for(int i = 1; i <= A_Chat_Server.ConnectionArray.size(); i++)
            {
                Socket TEMP_SOCK = (Socket) A_Chat_Server.ConnectionArray.get(i-1);
                PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputSteam());
                OUT.println("#?!" + CurrentUsers);
                OUT.flush();
                
            }
        }
    }
