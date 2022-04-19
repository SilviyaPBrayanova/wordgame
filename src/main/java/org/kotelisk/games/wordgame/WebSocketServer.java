package org.kotelisk.games.wordgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;
import org.kotelisk.games.wordgame.servers.WordgameServerEndpoint;

public class WebSocketServer {

    public static void main(String[] args) {
        runServer();
    }

    private static void runServer() {
        Server server = new Server("localhost",
                8025,
                "/websockets",
                WordgameServerEndpoint.class);

        try {
            server.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Please press a key to stop the server.");
            reader.readLine();
        } catch (DeploymentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            server.stop();
        }
    }
}
