package src.logic.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import src.utils.requestModule.Request;

public class RequestCatcher {
    private DatagramSocket socket;
    private boolean running = true;
    private byte[] buf = new byte[524];
    
    public RequestCatcher () {
        try {
            socket = new DatagramSocket(8448);
            System.out.println("Server is ready!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws ClassNotFoundException {
        try {
            while (running) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                ByteArrayInputStream byteOS = new ByteArrayInputStream(packet.getData());
                ObjectInputStream objIS = new ObjectInputStream(byteOS);
                Request incomeRequest = (Request) objIS.readObject();
                System.out.println(incomeRequest.toString());
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
