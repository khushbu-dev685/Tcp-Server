package org.example;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {

    public static void main(String[] args) {
        // Run the server in a background thread
        Thread serverThread = new Thread(new Runnable() {
            @Override
            public void run() {
                startServer();
            }
        });
        serverThread.start();
    }

    public static void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(5001);
            System.out.println("Listening for clients...");

            // Run the server to keep accepting clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                // Read the raw bytes from the device
                InputStream inputStream = clientSocket.getInputStream();
                byte[] buffer = new byte[1024]; // Adjust buffer size as needed
                int bytesRead = inputStream.read(buffer);

                if (bytesRead != -1) {
                    String clientData = new String(buffer, 0, bytesRead); // Convert bytes to string
                    System.out.println("Received data: " + clientData);
                } else {
                    System.out.println("No data received from client.");
                }
                if (bytesRead != -1) {
                    String clientData = new String(buffer, 0, bytesRead); // Convert bytes to string
                    System.out.println("Received data: " + clientData);
                    System.out.println("Data in Hex:" + convertStringToHex(clientData));


                } else {
                    System.out.println("No data received from client.");
                }

                inputStream.close();
                clientSocket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String convertStringToHex(String str) {

        // display in uppercase
        //char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8), false);

        // display in lowercase, default
        char[] chars = Hex.encodeHex(str.getBytes(StandardCharsets.UTF_8));

        return String.valueOf(chars);
    }
}
