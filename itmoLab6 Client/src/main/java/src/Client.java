package src;

import src.commands.CommandsHandler;
import src.commands.Invoker;
import src.logic.connection.Connection;
import src.logic.data.Receiver;
import src.logic.exceptions.InvalidResponseException;
import src.logic.streams.ConsoleInputManager;
import src.logic.streams.ConsoleOutputManager;
import src.logic.streams.InputManager;
import src.logic.streams.OutputManager;

public class Client {

    private final static String SERVER_HOST = "localhost";
    private final static int SERVER_PORT = 50689;

    private final CommandsHandler commands;

    private final Connection connection;

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

    public Client() {
        connection = new Connection(SERVER_HOST, SERVER_PORT);
        commands = new CommandsHandler(connection);
    }

    public void runClient() {
        try {
            commands.initializeCommands();
        } catch (InvalidResponseException e) {
            throw new RuntimeException(e);
        }

//        out.print("Hello, Welcome to\n");
//        out.print(logo);
//        out.print("Type \"help\" to get the information about all commands\n");
        String line;
        while (true) {
            try {
                out.print(invite + " ");
                line = in.readLine();
            } catch (IllegalArgumentException iae) {
                out.print(iae.getMessage() + "\n");
            }
        }
    }
}
