package src;

import module.logic.streams.ConsoleInputManager;
import module.logic.streams.ConsoleOutputManager;
import module.logic.streams.InputManager;
import module.logic.streams.OutputManager;
import src.commands.Invoker;
import src.logic.connection.Connection;

public class Client {

    private final static String SERVER_HOST = "localhost";
    private final static int SERVER_PORT = 50689;

    private final Invoker invoker;

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
        invoker = new Invoker(connection);
    }

    public void runClient() {

//        out.print("Hello, Welcome to\n");
//        out.print(logo);
//        out.print("Type \"help\" to get the information about all commands\n");
        String line;
        while (true) {
            try {
                out.print(invite + " ");
                line = in.readLine();
                System.out.println(invoker.parseCommand(line));
            } catch (IllegalArgumentException iae) {
                out.print(iae.getMessage() + "\n");
            }
        }
    }
}
