package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Handler extends Thread {
    Socket socket;
    BufferedWriter writer;
    BufferedReader input;

    BufferedReader scanner;

    static List<Handler> list = new LinkedList<>();
    public Handler(Socket socket) {
        this.socket = socket;

    }

    public Socket getSocket() {
        return this.socket;
    }

    public void init() {
        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        list.add(this);

        while (!Thread.interrupted()) {
            try {

                //클라이언트들이 말한걸 서버에 뿌려줌
                    String read = input.readLine().trim();
                    System.out.println("server로 들어오는 msg >> " + read);

                    //서버로 들어온 클라이언트에게 뿌려주는 메세지



                    broadcast(read);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //각 클라이언트들에게 뿌려줌
    public void broadcast(String line) throws IOException {

        for (Handler handler : list) {
            writer = new BufferedWriter(new OutputStreamWriter(handler.getSocket().getOutputStream()));

            writer.write(line);
            writer.newLine();
            writer.flush();
        }

    }

}
