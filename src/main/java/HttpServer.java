import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class HttpServer {
    public static void handleHttpRequest(Socket clientSocket) throws IOException {
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
                // Read the headers
                String headerLine;
                String userAgent = null;
                while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
                    System.out.println("Header: " + headerLine);
                    if (headerLine.startsWith("User-Agent:")) {
                        userAgent = headerLine.substring(12).trim();
                        System.out.println("User-Agent: " + userAgent);
                    }
                }

                // Send HTTP/1.1 200 OK response
                sendHttpResponse(clientSocket, path, userAgent);
            }
        }
    }

    public static void sendHttpResponse(Socket clientSocket, String path,String userAgent) throws IOException {
        OutputStream outputStream = clientSocket.getOutputStream();
        PrintWriter writer = new PrintWriter(outputStream, true);
        // Prepare HTTP response
        String httpResponse = "";
        String[] render = path.trim().split("/");
        if (path.equals("/")){
            httpResponse = "HTTP/1.1 200 OK\r\n\r\n";
        }else {
            if (render.length > 2) {
                if (render[1].equals("files")){
                    File file = new File(render[2]);
                    Scanner scanner = new Scanner(file);
                    String text = "";
                    while (scanner.hasNextLine()){
                        text += scanner.nextLine();
                        System.out.println(text);
                    }
                    httpResponse = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: application/octet-stream\r\n" +
                            "Content-Length: " + render[1].length() + "\r\n" +
                            "\r\n" +
                            text;
                }else {
                    String toRender = render[2].trim();
                    httpResponse = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length: " + toRender.length() + "\r\n" +
                            "\r\n" +
                            toRender;
                }
            }else if ((path.equals("/user-agent"))) {
                httpResponse = "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: text/plain\r\n" +
                        "Content-Length: " + userAgent.length() + "\r\n" +
                        "\r\n" +
                        userAgent;
            } else {
                httpResponse = "HTTP/1.1 404 Not Found\r\n\r\n";
            }
        }
        // Send HTTP response
        writer.println(httpResponse);
        writer.flush();
    }
}
