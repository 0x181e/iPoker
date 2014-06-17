package org;

import org.unix.Client;
import org.unix.bdn.MultipleSocketServer;
import org.unix.login.LoginServer;

import java.util.ArrayList;

/**
 * Created by unix on 15/06/14.
 */
public class Server {

    public static ArrayList<Client> connected = new ArrayList<>();

    public static boolean running = true;

    private static long lastUpdate = System.currentTimeMillis();

    public static void main(String[] args) {
        LoginServer.initialize();

        System.out.println("Reached!");
        while (running) {
            System.out.println("Still Running!");
            if (System.currentTimeMillis() >= (lastUpdate + 10000)) {
                lastUpdate = System.currentTimeMillis();
                System.out.println("current players connected: " + connected.size());
                int count = 0;
                for (Client c : connected) {
                    count += c.chips;
                }
                System.out.println("total chips ingame: " + count);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("reached!");
    }
}
