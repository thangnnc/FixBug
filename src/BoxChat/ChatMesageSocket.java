/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BoxChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JEditorPane;

/**
 *
 * @author Dell
 */
public class ChatMesageSocket {
    Socket socket;
    JEditorPane ed;
    PrintWriter dataOut;
    BufferedReader dataIn;

    public ChatMesageSocket(Socket socket, String name, JEditorPane ed) {
            this.socket = socket;
            this.ed = ed;
            
        try {
            dataOut = new PrintWriter(socket.getOutputStream());
            dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            receive(name).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public Thread receive(String name){
        return new Thread(){
            public void run(){
                while(true){
                    try {
                        String line = dataIn.readLine();
                        if(line != null){
                            ed.setText(ed.getText()+"\n"+name+": "+line);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        };
    }
    
    public void send(String name, String msg){
        String text = ed.getText();
        ed.setText(text+"\n"+name+": "+msg);
        dataOut.println(msg);
        dataOut.flush();
    }
    
    public void close(){
        try {
            dataIn.close();
            dataOut.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
