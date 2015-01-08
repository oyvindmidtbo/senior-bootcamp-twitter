package server;

import org.java_websocket.WebSocket;
import org.java_websocket.WebSocketImpl;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;

public class TwitterHub extends WebSocketServer {

    public TwitterHub(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        System.out.println( webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " opened connection!" );
    }

    @Override
    public void onClose(WebSocket webSocket, int i, java.lang.String s, boolean b) {
        System.out.println( webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " closed connection!" );
    }

    @Override
    public void onMessage(WebSocket webSocket, java.lang.String s) {
        System.out.println( webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " sent something: " + s );
    }

    @Override
    public void onError(WebSocket webSocket, java.lang.Exception e) {
        System.out.println( webSocket.getRemoteSocketAddress().getAddress().getHostAddress() + " got exception: " + e.getMessage());
    }

    public void sendToAll(String message) {
        Collection<WebSocket> con = connections();
        synchronized ( con ) {
            for( WebSocket c : con ) {
                c.send( message );
            }
        }
    }

    public static void main( String[] args ) throws InterruptedException , IOException {
        WebSocketImpl.DEBUG = true;
        int port = 8887; // 843 flash policy port
        try {
            port = Integer.parseInt( args[ 0 ] );
        } catch ( Exception ex ) {
        }
        TwitterHub hub = new TwitterHub( port );
        hub.start();
        System.out.println("TwitterHub started on port: " + hub.getPort());

        BufferedReader sysin = new BufferedReader( new InputStreamReader( System.in ) );
        while ( true ) {
            String in = sysin.readLine();
            if( in.equals( "exit" ) ) {
                hub.stop();
                break;
            } else if( in.equals( "restart" ) ) {
                hub.stop();
                hub.start();
                break;
            }
        }
    }
}
