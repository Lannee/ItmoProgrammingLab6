package src.logic.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RequestCatcher {
    private DatagramSocket socket;
    private boolean running = true;
    private byte[] buf = new byte[5];
    
    public RequestCatcher () {
        try {
            socket = new DatagramSocket(8448);
            System.out.println("Server is ready!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (running) {
                System.out.println("running");
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                System.out.println(packet.getSocketAddress());
                System.out.println(new String(packet.getData()));
                if (new String(packet.getData()).equals("exit")) {
                    running = false;
                    continue;
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        socket.close();
    }
}
