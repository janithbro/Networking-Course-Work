package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class ClientFormController {
    public Label lblUserName;
    public TextArea txtView;
    public TextField txtMessageField;
    public AnchorPane pane;

    private Socket socket;
//    private BufferedWriter bufferedWriter;
//    private BufferedReader bufferedReader;
    private DataOutputStream dataOutputStream;
    private DataInputStream dataInputStream;
    public static String userName;

    public void initialize() {

        new Thread(() -> {

            try {

                lblUserName.setText(userName);


                socket = new Socket("localhost", 5000);
//                this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//                this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());

                listenForMessage();

            } catch (Exception exception) {
                System.out.println(exception);
            }

        }).start();

    }

    public void imgSendOnMousedClicked(MouseEvent mouseEvent) {
        try {
            while (socket.isConnected()) {
                String messageToSend = txtMessageField.getText().trim();
                dataOutputStream.writeUTF(messageToSend);
                txtView.appendText("Me : " + messageToSend + "\n");
                dataOutputStream.flush();
            }

        } catch (Exception exception) {
            closeEverything(socket, dataInputStream, dataOutputStream);
        }
    }

    public void txtMessageFieldOnAction(ActionEvent actionEvent) {

    }

//    public void sendMessage() {
//        try {
//            while (socket.isConnected()) {
//                String messageToSend = txtMessageField.getText().trim();
//                dataOutputStream.writeUTF(messageToSend);
//                txtView.appendText("Me : " + messageToSend + "\n");
//                dataOutputStream.flush();
//            }
//
//        } catch (Exception exception) {
//            closeEverything(socket, dataInputStream, dataOutputStream);
//        }
//    }

    public void listenForMessage() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                String msgFromGroupChat;

                while (socket.isConnected()) {

                    try {

                        msgFromGroupChat = dataInputStream.readUTF();
                        txtView.appendText(msgFromGroupChat + "\n");

                    } catch (Exception exception) {
                        closeEverything(socket, dataInputStream, dataOutputStream);
                    }
                }
            }
        }).start();

    }

    public void closeEverything(Socket socket, DataInputStream bufferedReader, DataOutputStream bufferedWriter) {
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
