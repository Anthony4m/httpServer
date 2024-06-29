import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    System.out.println("Listening for connections");
     ServerSocket serverSocket = null;
     Socket clientSocket = null;

     try {
       serverSocket = new ServerSocket(4221);
       serverSocket.setReuseAddress(true);
         while (true) {
             clientSocket = serverSocket.accept();
             System.out.println("Accepted new connection from " + clientSocket.getInetAddress());

             ClientHandler clientHandler = new ClientHandler(clientSocket);
             new Thread(clientHandler).start();
         }
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
}
