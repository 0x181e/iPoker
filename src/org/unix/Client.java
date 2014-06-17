package org.unix;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

    public String user;
    public String pass;

    public int chips;

    public Socket connection;

    public Client(String User, String Pass, int Chips, Socket Connection) {

        this.user = User;
        this.pass = Pass;
        this.chips = Chips;
        this.connection = Connection;
    }

    public void sendMessage(String msg) {
        try {
            OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream()), "US-ASCII");

            osw.write("[msg] - " + user + " - " + msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
