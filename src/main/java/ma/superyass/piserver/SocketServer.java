package ma.superyass.piserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketImpl;
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

    boolean flag;
    private int port;

    public SocketServer(ServerSocketManager manager, int port) {
        serverSocketManager = manager;
        this.port = port;
        flag = true;
    }

    //accept only one client
    @Override
    public void run() {
        try {
            //Listen on port
            serverSocket = new ServerSocket(port);
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

            } while (flag);

        } catch (IOException ex) {
            Logger.getLogger(SocketServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendData(String toSend) throws IOException {
        pw = new PrintWriter(clientSock.getOutputStream());
        pw.write(toSend);
        pw.write('\n');
        pw.flush();

    }

    public Socket getClientSock() {
        return clientSock;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

}
