package ma.superyass.piserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SuperYass
 */
public class SocketServer extends Thread{

    ServerSocket serverSocket;
    Socket clientSock;
    PrintWriter pw;
    BufferedReader brin;

    ServerSocketManager serverSocketManager;

    private static int PORT;

    public SocketServer(ServerSocketManager manager, int port) {
        serverSocketManager = manager;
        this.PORT = port;
    }

    //accept only one client
    @Override
    public void run() {
        try {
            //Listen on port
            serverSocket = new ServerSocket(PORT);
            do {
                //Get connection
                System.out.println("Waiting for the client...");
                clientSock = serverSocket.accept();
                System.out.println("Client conneted");

                brin = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));

                String response;
                while ((response = brin.readLine()) != null) {
                    System.out.println(response);
                    serverSocketManager.fireEvent(response);
                }

                clientSock.close();
                System.out.println("thread");

            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private void sendData(String toSend) throws IOException {
//        pw = new PrintWriter(clientSock.getOutputStream());
//        pw.write(toSend);
//        pw.write('\n');
//        pw.flush();
//
//    }

    public Socket getClientSock() {
        return clientSock;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

}
