package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {

    public static void main(String[] args) throws IOException {
        // Here, we create a Socket instance named socket
//        try {
//            ServerSocket serverSocket = new ServerSocket(5001);
//            System.out.println("Listening for clients...");
//            Socket clientSocket = serverSocket.accept();
//            String clientSocketIP = clientSocket.getInetAddress().toString();
//            int clientSocketPort = clientSocket.getPort();
//            System.out.println("[IP: " + clientSocketIP + " ,Port: " + clientSocketPort + "]  " + "Client Connection Successful!");
//
//            DataInputStream dataIn = new DataInputStream(clientSocket.getInputStream());
//            DataOutputStream dataOut = new DataOutputStream(clientSocket.getOutputStream());
//
//            String clientMessage = dataIn.readUTF();
//            System.out.println(clientMessage);
//
//            String serverMessage = "Hi this is coming from Server!";
//            dataOut.writeUTF(serverMessage);
//
//            dataIn.close();
//            dataOut.close();
//            serverSocket.close();
//            clientSocket.close();
//        } catch (EOFException e) {
//            System.out.println("Client disconnected abruptly.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

        try {
            ServerSocket serverSocket = new ServerSocket(5001);
            System.out.println("Listening for clients...");
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

            inputStream.close();
            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
