package org.kotelisk.games.wordgame.servers;


import java.util.logging.Level;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

@ServerEndpoint(value = "/wordgame")
public class WordgameServerEndpoint {

    private Logger LOGGER = Logger.getLogger(WordgameServerEndpoint.class.getName());

    @OnOpen
    public void onOpen(Session session) {
        String log = "New connection:" + System.lineSeparator() +
                "Id: " + session.getId() + System.lineSeparator();
        LOGGER.info(log);
        System.out.println("LOG: " + log);
    }

    @OnMessage
    public void onStringMessage(Session session, String message){
        try {
            switch (message) {
                case "quit":
                    try {
                        session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE,
                                    "Game ended"));
                    } catch (IOException ioe) {
                        throw new RuntimeException(ioe);
                    }
                    break;
            }
            if (session.isOpen()) {
                System.out.println("LOG: " + message);
                session.getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "OnMessage error: ", e);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable){
        String log = "Error on connection: " + session.getId();
        LOGGER.log(Level.ALL, "error", throwable);
        System.out.println("LOG: " + log);
        throwable.printStackTrace(System.out);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason){
        String log = "Closing connection: " + session.getId();
        LOGGER.info("Closing connection: " + session.getId());
        System.out.println("LOG: " + log);
    }
}
