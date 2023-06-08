package model;

import controller.ClientFormController;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private String clientName;

    public ClientHandler(Socket socket) {

        try {

            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            clientName = new ClientFormController().lblUserName.getText();
            clientHandlers.add(this);

            broadcastMessage("Server : " + clientName + " has entered the chat..!");

        } catch (Exception exception) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {

            try {

                messageFromClient = dataInputStream.readUTF();
                broadcastMessage(messageFromClient);

            } catch (Exception exception) {
                closeEverything(socket, dataInputStream, dataOutputStream);
                break;
            }

        }
    }

    public void broadcastMessage(String messageToSend) {
        for (ClientHandler clientHandler : clientHandlers) {

            try {

                if (!clientHandler.clientName.equals(clientName)) {
                    clientHandler.dataOutputStream.writeUTF(messageToSend);
                    clientHandler.dataOutputStream.flush();
                }

            } catch (Exception exception) {
                closeEverything(socket, dataInputStream, dataOutputStream);
            }
        }
    }

    public void removeClientHandler() {
        clientHandlers.remove(this);
        broadcastMessage("Server : " + clientName + " has left the chat..!");
    }

    public void closeEverything(Socket socket, DataInputStream bufferedReader, DataOutputStream bufferedWriter) {
        removeClientHandler();
        try {

            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (Exception exception) {
            System.out.println(exception);
        }
    }
}
