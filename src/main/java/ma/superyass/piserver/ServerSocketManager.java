package ma.superyass.piserver;

/**
 * @author SuperYass
 */
public class ServerSocketManager {

    private SocketServer serverRunner;
    private static final int PORT = 10000;
    FXMLController controller;
    
    public ServerSocketManager(FXMLController controller) {
        this.controller = controller;
    }

    public boolean startServer() {

        if (serverRunner == null || (serverRunner!= null && !serverRunner.isAlive())) {
            serverRunner = new SocketServer(this, PORT);
            serverRunner.start();
            return true;
        }else{
            System.out.println("1111");
            return false;
        }
    }

    public void fireEvent(String message){
        System.out.println("event fired");
        controller.processMessage(message);
    }
}
