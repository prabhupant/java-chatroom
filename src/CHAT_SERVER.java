import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

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
                
                CHAT_SERVER_RETURN CHAT = new CHAT_SERVER_RETURN(SOCK);
                Thread X = new Thread(CHAT);
                X.start(); 
                
            }
        }catch(Exception X)
        {
            System.out.print(X);
        }
    }
        
        public static void AddUserName(Socket X) throws IOException
        {
            Scanner INPUT = new Scanner(X.getInputStream());
            String UserName = INPUT.nextLine();
            CurrentUsers.add(UserName);
            
            for(int i = 1; i <= CHAT_SERVER.ConnectionArray.size(); i++)
            {
                Socket TEMP_SOCK = (Socket) CHAT_SERVER.ConnectionArray.get(i-1);
                PrintWriter OUT = new PrintWriter(TEMP_SOCK.getOutputStream());
                OUT.println("#?!" + CurrentUsers);
                OUT.flush();
                
            }
        }
 }
