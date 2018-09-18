package ltd.vblago.prealpha.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;

import ltd.vblago.prealpha.network.TCPConnection;
import ltd.vblago.prealpha.network.TCPConnectionListener;

/**
 * Server file of server
 * @author blackwell
 */
public class Server implements TCPConnectionListener {
    public static void main(String[] args) {
        InetAddress ip;
        try {
            ip = InetAddress.getLocalHost();
            System.out.println("Server ip address: " + ip.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        new Server();
    }

    private Server(){
        System.out.println("Server is running...");
        try (ServerSocket serverSocket = new ServerSocket(PORT)){

            // server main loop
            while (true){
                try {
                    new TCPConnection(this, serverSocket.accept());
                }catch (IOException ex){
                    System.out.println("TCPConnection exception: ");
                    ex.printStackTrace();
                }
            }

        } catch (IOException ex) {
            System.out.println("Server stopped");
            ex.printStackTrace();
        }
    }


    @Override
    public void onConnectionReady(TCPConnection tcpConnection) {

    }

    @Override
    public void onReceiveString(TCPConnection tcpConnection, String value) {

    }

    @Override
    public void onDisconnect(TCPConnection tcpConnection) {

    }

    @Override
    public void onException(TCPConnection tcpConnection, Exception ex) {

    }
}
