package controller;

import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import model.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;

public class ServerFormController {
    public TextArea txtView;
    public AnchorPane pane;

    public ServerSocket serverSocket;

    public void initialize() {

        new Thread(() -> {

            try {

                serverSocket = new ServerSocket(5000);
                startServer();

            } catch (Exception exception) {
                System.out.println(exception);
            }

        }).start();
    }

    public void startServer() {

        try {

            while (!serverSocket.isClosed()) {

                Socket localSocket = serverSocket.accept();
                txtView.appendText("A new Client has Connected..!\n");

                ClientHandler clientHandler = new ClientHandler(localSocket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

}
