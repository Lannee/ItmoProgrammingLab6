package src;

import module.connection.ChannelConnection;
import module.connection.IConnection;
import module.logic.streams.ConsoleInputManager;
import module.logic.streams.ConsoleOutputManager;
import module.logic.streams.InputManager;
import module.logic.streams.OutputManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.commands.Invoker;

public class Client {

    private final static String SERVER_HOST = "localhost";
    private final static int SERVER_PORT = 50689;

    private final Invoker invoker;

    private final IConnection connection;

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

    private static final Logger logger = LoggerFactory.getLogger(Client.class);
//    Command for spectate of working logger
//    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();

    public Client() {
        connection = new ChannelConnection(SERVER_PORT);
        invoker = new Invoker(connection);
    }

    public void runClient() {
        logger.info("Client started.");
        out.print("Hello, Welcome to\n");
        out.print(logo);
        out.print("Type \"help\" to get the information about all commands\n");
        String line;
        logger.info("Client is ready to take commands and send them on server.");
        while (true) {
            try {
                System.out.print(invite + " ");
                line = in.readLine();
                logger.info("User typed: '{}'", line.trim());
                out.print(invoker.parseCommand(line));
            } catch (IllegalArgumentException iae) {
                out.print(iae.getMessage() + "\n");
                logger.error(iae.getMessage());
            }
        }
    }
}
