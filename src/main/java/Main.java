import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    System.out.println("Logs from your program will appear here!");
     ServerSocket serverSocket = null;
     Socket clientSocket = null;

     try {
       serverSocket = new ServerSocket(4221);
       serverSocket.setReuseAddress(true);
         while (true) {
             clientSocket = serverSocket.accept(); // Wait for a client connection
             System.out.println("Accepted new connection from " + clientSocket.getInetAddress());

             // Create a new thread to handle the client connection
             ClientHandler clientHandler = new ClientHandler(clientSocket);
             new Thread(clientHandler).start();
         }
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
