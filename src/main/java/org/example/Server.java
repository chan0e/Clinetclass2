package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

//    List<Handler> list;
    public Server(){
        open();
    }

    public void open(){

        try(ServerSocket serverSocket = new ServerSocket(1234)){


            while(!Thread.interrupted()) {
                Socket socket = serverSocket.accept();
                Handler handler = new Handler(socket);
                handler.init();
                handler.start();
            }

        }   catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args){
        new Server();
    }


}
