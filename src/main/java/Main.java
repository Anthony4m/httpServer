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
       clientSocket = serverSocket.accept(); // Wait for connection from client.
       clientSocket.getKeepAlive();
       System.out.println("accepted new connection" + clientSocket);
       handleHttpRequest(clientSocket);
       serverSocket.close();
     } catch (IOException e) {
       System.out.println("IOException: " + e.getMessage());
     }
  }
    private static void handleHttpRequest(Socket clientSocket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        String requestLine = reader.readLine(); // Read the request line

        if (requestLine != null) {
            System.out.println("Request Line: " + requestLine);

            // Split the request line into parts
            String[] parts = requestLine.split("\\s+");

            if (parts.length >= 2) {
                String method = parts[0];
                String path = parts[1];
                System.out.println("Method: " + method);
                System.out.println("Path: " + path);

                // Send HTTP/1.1 200 OK response
                sendHttpResponse(clientSocket, path);
            }
        }
    }

    private static void sendHttpResponse(Socket clientSocket, String path) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        // Prepare HTTP response
        String httpResponse = "";
        String[] render = path.trim().split("/");
        if (render.length > 2) {
            String toRender = render[2];
            httpResponse = "HTTP/1.1 200 OK\r\n\r\n\r\n" + toRender;
        }else{
            httpResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
        }


        // Send HTTP response
        writer.println(httpResponse);
        writer.flush();
    }
}
