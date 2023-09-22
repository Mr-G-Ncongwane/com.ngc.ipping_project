package com.ngc.main;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class Ping_project {
static String locaString="C:\\ip_addresses.txt";
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(locaString));
           
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new PingTask(), 0, 100000); // Ping every 1 minute
    }

    private static void pingIPAddress(String label, String ipAddress) {
        try {
            InetAddress inet = InetAddress.getByName(ipAddress);

            if (inet.isReachable(1000)) { // Timeout in milliseconds
            	System.out.println(ipAddress + " ("+label.toUpperCase()+") is reachable");               
            
            } else {
            	LocalDateTime timestamp = LocalDateTime.now(); // Capture the timestamp
                System.err.println(ipAddress + " (" + label.toUpperCase() + ") is not reachable: " + timestamp.toString());
               
            }
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + ipAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
    static class PingTask extends TimerTask {
        public void run() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(locaString ));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String label = parts[0];
                    String ipAddress = parts[1];
                    pingIPAddress(label, ipAddress);
                    System.out.println("*=================================================================================*");
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
