import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    System.out.println("Logs from your program will appear here!");
     ServerSocket serverSocket = null;
     Socket clientSocket = null;

     try {
       serverSocket = new ServerSocket(4221);
       serverSocket.setReuseAddress(true);
       clientSocket = serverSocket.accept(); // Wait for connection from client.
       clientSocket.getKeepAlive();
       System.out.println("accepted new connection" + clientSocket.getInetAddress());
       sendHttpResponse(clientSocket);
       serverSocket.close();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
    private static void sendHttpResponse(Socket clientSocket) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);

        // Prepare HTTP response
        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n";

        // Send HTTP response
        writer.println(httpResponse);
        writer.flush();
    }
}
