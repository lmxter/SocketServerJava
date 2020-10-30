
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author luis.quimbayo
 */
public class main {
    
    public static void main(String[] args){
        final main start = new main();
        System.out.println(start.getCurrentTime() +" Socket Server Luis Start");
        start.buildSocketServer();
    }
    
    public void buildSocketServer(){
        try {
            final ServerSocket servSocket = new ServerSocket(9999);
            Socket newClient;
            do{
                newClient = servSocket.accept();
                newClient.setSoTimeout(10000);
                runThread(newClient);                
            }
            while(!servSocket.isClosed());
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void runThread(final Socket newSocketClient){
        final Runnable newClient = new Client(newSocketClient);
        final Thread client = new Thread(newClient);
        client.start();        
    }
    
    private String getCurrentTime() {
        final Time sqlTime = new Time(new Date().getTime());
        return sqlTime.toString();
    }
}
