package src;

import src.commands.Invoker;
import src.logic.data.Receiver;
import src.logic.streams.ConsoleInputManager;
import src.logic.streams.ConsoleOutputManager;
import src.logic.streams.InputManager;
import src.logic.streams.OutputManager;

public class Client {

    private final Invoker invoker;
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
        if(args.length == 0) {
            out.print("Incorrect number of arguments\n");
            System.exit(2);
        }
        String fileName = args[0];
//        String fileName = "FileJ";
        String filePath = System.getenv().get(fileName);
        if(filePath == null) {
            out.print("Environment variable \"" + fileName + "\" does not exist\n");
            System.exit(1);
        }

        invoker = new Invoker(
                new Receiver(filePath)
            );
    }

    public void runClient() {
        out.print("Hello, Welcome to\n");
        out.print(logo);
        out.print("Type \"help\" to get the information about all commands\n");
        String line;
        while (true) {
            try {
                out.print(invite + " ");
                line = in.readLine();
                invoker.parseCommand(line);
            } catch (IllegalArgumentException iae) {
                out.print(iae.getMessage() + "\n");
            }
        }
    }
}
