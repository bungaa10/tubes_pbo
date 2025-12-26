package api;

import java.net.URI;
import java.util.function.Consumer;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

public class AntrianWebSocketClient extends WebSocketClient {

    private final Consumer<String> onMessage;

    public AntrianWebSocketClient(URI serverUri, Consumer<String> onMessage) {
        super(serverUri);
        this.onMessage = onMessage;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("WS connected");
    }

    @Override
    public void onMessage(String message) {
        onMessage.accept(message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("WS closed");
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }
    
}
