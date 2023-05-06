package src.logic.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.net.SocketException;

import module.connection.requestModule.TypeOfRequest;
import module.connection.responseModule.Response;
import module.connection.requestModule.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Server;

public class Connection {

    private final static int PACKAGE_SIZE = 2048;
    private SocketAddress clientSocketAddress;
    private byte[] buf = new byte[PACKAGE_SIZE];
    private DatagramSocket socket;

    private static final Logger logger = LoggerFactory.getLogger(Connection.class);


    public Connection (int port) {
        try {
            socket = new DatagramSocket(port);
            // socket.setReuseAddress(true); // needed for IP multicasting
            logger.info("Connection initialized.");
        } catch (SocketException e) {
            logger.error(e.getMessage());
        }
    }

    public Request catchRequest() {
        Request incomeRequest;
        try {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            clientSocketAddress = packet.getSocketAddress();
            logger.info("Client with address " + clientSocketAddress.toString() + " sent request.");
            ByteArrayInputStream byteOS = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objIS = new ObjectInputStream(byteOS);
            incomeRequest = (Request) objIS.readObject();
            if (incomeRequest.getTypeOfRequest().equals(TypeOfRequest.COMMAND)){
                logger.info("Command-name in request: '{}', arguments: '{}'.", incomeRequest.getCommandName(), incomeRequest.getArgumentsToCommand());
            } else {
                logger.info("Client requested Commands Map & specs.");
            }
        } catch (IOException | ClassNotFoundException e) {
            incomeRequest = new Request("null", null, null);
            logger.error("Connection error" + e.getMessage());
        }
        return incomeRequest;
    }

    public void sendResponse(Response response) {
        try {
            byte[] dataToSend;
            ByteArrayOutputStream byteOS = new ByteArrayOutputStream();
            ObjectOutputStream objOS = new ObjectOutputStream(byteOS);
            objOS.writeObject(response);
            dataToSend = byteOS.toByteArray();
            DatagramPacket packet = new DatagramPacket(dataToSend, dataToSend.length, clientSocketAddress);
            socket.send(packet);
            logger.info("Response sent to client with address " + clientSocketAddress.toString() + ".");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
