import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            HttpServer.handleHttpRequest(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                    System.out.println("Connection with client closed.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}