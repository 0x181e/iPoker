package org.unix.login.accounts;

import org.Server;
import org.unix.Client;
import org.unix.login.misc.ReturnCodes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;

public class Account {

    public static int checkLogin(String name, String password, Socket connection) {
        try {
            BufferedReader br = new BufferedReader(new FileReader("Z:\\programming\\iPoker\\data\\accounts\\" + name + ".char"));

            String line;
            String[] split;

            while ((line = br.readLine()) != null) {
                split = line.split(" = ");
                if (split[0].equalsIgnoreCase("password")) {
                    if (split[1].equals(password)) {
                        System.out.println("Successful Login | user: " + name);
                        registerAccount(name, connection);
                        return ReturnCodes.SUCCESS.getCode();
                    } else {
                        System.out.println("Refused Login | user: " + name);
                        return ReturnCodes.INCORRECT_PASSWORD.getCode();
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Attempted to load: " + name + " | Account Missing");
            return ReturnCodes.INVALID_ACCOUNT.getCode();
        } catch (IOException e) {
            System.out.println("IO Exception Caught | Account : loadAccount");
        }
        return ReturnCodes.INVALID_ACCOUNT.getCode();
    }

    public static void registerAccount(String name, Socket connection) {
        String user = name;
        String pass = "";
        int chips = 0;

        try {
            BufferedReader br = new BufferedReader(new FileReader(name + ".char"));

            String line;
            String[] split;

            while ((line = br.readLine()) != null) {
                split = line.split(" = ");
                if (split[0].equalsIgnoreCase("password")) {
                    pass = split[1];
                } else if (split[0].equalsIgnoreCase("chips")) {
                    chips = Integer.parseInt(split[1]);
                }
            }

            Server.connected.add(new Client(user, pass, chips, new Socket(connection.getInetAddress(), 8595)));

        } catch (FileNotFoundException e) {} catch (IOException e) {}
    }

}
