package org.example;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {


    BufferedReader br = null;
    BufferedReader inmsg = null;

    public Client() {
        br = new BufferedReader(new InputStreamReader(System.in));

        connect();
    }

    public void connect() {
        try {
            Socket socket = new Socket("127.0.0.1", 1234);
            System.out.println("연결완료 ");

            //들은걸 표시

            new Thread(() -> {
                try {
                    BufferedReader inbr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String inline;
                    while((inline = inbr.readLine()) != null) {

                        System.out.println("상대방 >> " + inline);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();


            String outline;
            //클라이언트가 말한걸 서버 소켓에 뿌려줌
            while ((outline = br.readLine()) != null) {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


                out.write(outline);
                out.newLine();
                out.flush();


            }
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client();
    }

}
