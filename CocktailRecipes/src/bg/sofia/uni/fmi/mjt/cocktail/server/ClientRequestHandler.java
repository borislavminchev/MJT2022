package bg.sofia.uni.fmi.mjt.cocktail.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientRequestHandler implements Runnable {
    private Socket socket;
    private RequestExecutor executor;
    public ClientRequestHandler(Socket socket, RequestExecutor executor) {
        this.socket = socket;
        this.executor = executor;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // autoflush on
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) { // read the message from the client
                System.out.println("Message received from client: " + inputLine);
                ResponseObject response = executor.execute(inputLine);
                out.println(response.toString()); // send response back to the client
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
