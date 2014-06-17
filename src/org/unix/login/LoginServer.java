package org.unix.login;

import org.Server;
import org.unix.login.accounts.*;

import java.net.*;
import java.io.*;

public class LoginServer implements Runnable {

    private Socket connection;
    private int ID;

    public static boolean running = true;

    public static void initialize() {
        int port = 8594;
        int count = 0;
        try {
            ServerSocket socket1 = new ServerSocket(port);
            System.out.println("Login Server Initialized!");
            while (running) {
                Socket connection = socket1.accept();
                Runnable runnable = new LoginServer(connection, ++count);
                Thread thread = new Thread(runnable);
                thread.start();
            }
            socket1.close();
            Server.running = false;
            System.out.println("Login Server Shutdown!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private LoginServer(Socket s, int i) {
        this.connection = s;
        this.ID = i;
    }

    @Override
    public void run() {
        try {
            String returnCode;
            InputStreamReader isr = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));
            OutputStreamWriter osw = new OutputStreamWriter(new BufferedOutputStream(connection.getOutputStream()), "US-ASCII");
            int character;
            StringBuffer process = new StringBuffer();
            while ((character = isr.read()) != 13) {
                process.append((char) character);
            }

            if (process.toString().equals("shutdown")) {
                running = false;
                System.out.println("Server Shutdown command recieved from client!");
                return;
            }

            String[] split = process.toString().split(" - ");
            System.out.println("");
            String user = split[0].split(":")[1];
            String pass = split[1].split(":")[1];

            returnCode = "" + Account.checkLogin(user, pass, connection);

            osw.write(returnCode + (char)13);
            osw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
