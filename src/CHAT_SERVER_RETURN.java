import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CHAT_SERVER_RETURN implements Runnable
{
    Socket SOCK;
    private Scanner INPUT;
    private PrintWriter OUT;
    String MESSAGE = "";
    
    public CHAT_SERVER_RETURN(Socket X)
    {
        this.SOCK = X;
    }
    
    public void CheckConnection() throws IOException
    {
        if(!SOCK.isConnected())
        {
            for(int i = 1; i <= CHAT_SERVER.ConnectionArray.size(); i++)
            {
                if(CHAT_SERVER.ConnectionArray.get(i) == SOCK)
                {
                    CHAT_SERVER.ConnectionArray.remove(i);
                }
            }
            for (int i = 1; i <= CHAT_SERVER.ConnectionArray.size(); i++)
            {
                Socket TEMP_SOCK = (Socket) CHAT_SERVER.ConnectionArray.get(i-1);
                PrintWriter TEMP_OUT = new PrintWriter (TEMP_SOCK.getOutputStream());
                TEMP_OUT.println(TEMP_SOCK.getLocalAddress().getHostName() + " disconnected!");
                TEMP_OUT.flush();
                
                System.out.println(TEMP_SOCK.getLocalAddress().getHostName() + " disconnected!");
                
            }
        }
    }
    
    public void run()
    {
        try
        {
            try
            {
                INPUT = new Scanner(SOCK.getInputStream());
                OUT = new PrintWriter(SOCK.getOutputStream());
                
                while(true)
                {
                    CheckConnection();
                    
                    if(!INPUT.hasNext())
                    {
                        return;
                    }
                    
                    MESSAGE = INPUT.nextLine();
                    
                    System.out.println("Client said: " + MESSAGE);
                    
                    for(int i = 1; i <= CHAT_SERVER.ConnectionArray.size(); i++)
                    {
                        Socket TEMP_SOCK = (Socket) CHAT_SERVER.ConnectionArray.get(i-1);
                        PrintWriter TEMP_OUT = new PrintWriter (TEMP_SOCK.getOutputStream());
                        TEMP_OUT.println(MESSAGE);
                        TEMP_OUT.flush();
                        System.out.println("Sent to: " + TEMP_SOCK.getLocalAddress().getHostName());
                        
                    }
                }
            }
            finally
            {
                SOCK.close();
            }
              
        }
        catch(Exception X)
        {
            System.out.println(X);
        }

    }
    
}