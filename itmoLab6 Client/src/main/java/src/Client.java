package src;

import src.commands.Invoker;
import src.logic.connection.Connection;
import src.logic.data.Receiver;
import src.logic.streams.ConsoleInputManager;
import src.logic.streams.ConsoleOutputManager;
import src.logic.streams.InputManager;
import src.logic.streams.OutputManager;
import src.utils.requestModule.RequestFactory;
import src.utils.requestModule.TypeOfRequest;

public class Client {

    public final static String invite = ">>>";

    private final static String logo = """
            ╔╗   ╔═══╗╔══╗  ╔═══╗     ╔══╗╔════╗╔═╗╔═╗╔═══╗
            ║║   ║╔═╗║║╔╗║  ║╔══╝     ╚╣─╝║╔╗╔╗║║║╚╝║║║╔═╗║
            ║║   ║║ ║║║╚╝╚╗ ║╚══╗      ║║ ╚╝║║╚╝║╔╗╔╗║║║ ║║  ╔╗       ╔╗
            ║║ ╔╗║╚═╝║║╔═╗║ ║╔═╗║      ║║   ║║  ║║║║║║║║ ║║  ║╚╗╔╦╗╔╗ ║╠╗╔═╗
            ║╚═╝║║╔═╗║║╚═╝║ ║╚═╝║     ╔╣─╗  ║║  ║║║║║║║╚═╝║  ║╬║║║║║╚╗║═╣║╬║
            ╚═══╝╚╝ ╚╝╚═══╝ ╚═══╝     ╚══╝  ╚╝  ╚╝╚╝╚╝╚═══╝  ╚═╝╚═╝╚═╝╚╩╝╚═╝
            """;

    public static final OutputManager out = new ConsoleOutputManager();
    public static final InputManager in = new ConsoleInputManager();

    public Client(String[] args) {
        
    }

    public void runClient() {
        Connection connection = new Connection("localhost", 8449);
        out.print("Hello, Welcome to\n");
        out.print(logo);
        out.print("Type \"help\" to get the information about all commands\n");
        String line;
        while (true) {
            try {
                out.print(invite + " ");
                line = in.readLine();
                connection.sendRequest(RequestFactory.createRequest(line, "", TypeOfRequest.COMMAND));
                out.print(connection.catchResponse().getResponseCommandExecution());        
            } catch (IllegalArgumentException iae) {
                out.print(iae.getMessage() + "\n");
            }
        }
    }
}
