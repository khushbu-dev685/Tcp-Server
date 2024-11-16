package org.example;

import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
            ServerSocket serverSocket = new ServerSocket(5020);
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
                    System.out.println("Data in Hex:" + convertStringToHex(clientData));

                    //convert data to acknowledge data
                    String ackMessage = convertDataToAckFormat(clientData);

                    // Send acknowledgment (ACK) back to client
                    sendAck(ackMessage, clientSocket);

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


    public static String convertDataToAckFormat(String data) {
        // Validate and process the string if it matches the pattern
        if (data != null && data.startsWith("@NTCCH*>S:")) {
            // Replace *> with < and remove everything after the colon
            return data.replace(">", "<").split(":")[0];
        }
        // Return the original string if it doesn't match the expected format
        return data;
    }

    public static void sendAck(String ackMessage, Socket clientSocket) {
        // Send an acknowledgment message (ACK) to the client
        try (OutputStream outputStream = clientSocket.getOutputStream()) {
            outputStream.write(ackMessage.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent ACK: " + ackMessage);
        } catch (IOException e) {
            System.out.println("Error sending ACK to client: " + e.getMessage());
        }
    }
}
