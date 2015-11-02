package hangmangame;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleServer {

    public static void main(String[] args) throws IOException {
        int port = 2222, poolSize = 5;
        ServerSocket sSocket = null;
        try {
            if (args.length > 1) {
                poolSize = Integer.parseInt(args[1]);
            }
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            }
        } catch (NumberFormatException ex) {
            System.out.println("usage: Java hangman game: " + port + " " + poolSize);
            System.exit(1);
        }

        try {
            sSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        ExecutorService fixedPool = Executors.newFixedThreadPool(poolSize);

        while (true) {
            Socket socket = sSocket.accept();
            fixedPool.execute(new SlowConnectionHandler(socket));
        }
    }
}
