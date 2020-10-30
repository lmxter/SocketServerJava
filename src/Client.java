
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
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
public class Client implements Runnable{

    private transient Socket newClient;
    private int idThread = 0;
    private int idClient = 0;
    private transient long time;
    
    public Client(final Socket newClient){
        this.newClient = newClient;
    }
    
    @Override
    public void run() {
        checkId();
        String frame;
        try {
            final BufferedReader reader = new BufferedReader
                    (new InputStreamReader(newClient.getInputStream()));
            final StringBuilder stringBuilder = new StringBuilder();
            while(true){
                int character = reader.read();
                stringBuilder.append((char) character);
                if (character == 126 || character == 13
                        || character == -1)
                    break;
            }
                frame = stringBuilder.toString();
                execFrame(frame.trim());
                newClient.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void execFrame(final String frame){
        String getIp = newClient.getInetAddress().getHostAddress();
        String sendToClient = "";
        this.time = System.currentTimeMillis();
        try {
        System.out.println(this.idClient + " " + frame + " " + getIp);
        if(frame.startsWith("808,001,"))
            sendToClient = "It is a student";
        else if(frame.startsWith("808,002,"))
            sendToClient = "It is not a student";
        else{
            sendToClient = "Unauthorized";
        }
        sendFrameToClient(sendToClient);
            newClient.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendFrameToClient(final String frame){
        try {
            final PrintWriter out = new PrintWriter
                    (newClient.getOutputStream(),true);
            out.println(frame);
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void checkId(){
        synchronized(this){
            if(this.idThread == 9999){
                this.idThread = 0;
            }
            idThread++;
            idClient = idThread;
        }
    }
}
