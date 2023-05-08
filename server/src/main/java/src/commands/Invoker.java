package src.commands;

import module.commands.CommandArgument;
import module.commands.CommandDescription;
import module.connection.requestModule.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.Server;
import src.logic.data.Receiver;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Invoker {

    private final Map<String, Command> declaredCommands = new TreeMap<>();

    private final Receiver receiver;

    private final Map<String, Integer> files = new HashMap<>();

    private Integer recursionDepth = 1;

    private static final Pattern ARG_PAT = Pattern.compile("\"[^\"]+\"|\\S+");

    private static final Logger logger = LoggerFactory.getLogger(Server.class);

    public Invoker(Receiver receiver) {
        this.receiver = receiver;
        declaredCommands.put("help", new Help(this));
        declaredCommands.put("info", new Info(receiver));
        declaredCommands.put("update", new Update(receiver));
        declaredCommands.put("execute_script", new ExecuteScript(this));
        declaredCommands.put("add", new Add(receiver));
        declaredCommands.put("clear", new Clear(receiver));
        declaredCommands.put("exit", new Exit());
        declaredCommands.put("save", new Save(receiver));
        declaredCommands.put("show", new Show(receiver));
        declaredCommands.put("remove_first", new RemoveFirst(receiver));
        declaredCommands.put("remove_head", new RemoveHead(receiver));
        declaredCommands.put("remove_by_id", new RemoveById(receiver));
        declaredCommands.put("print_ascending", new PrintAscending(receiver));
        declaredCommands.put("remove_greater", new RemoveGreater(receiver));
        declaredCommands.put("count_greater_than_weight", new CountGreaterThanWeight(receiver));
        declaredCommands.put("group_counting_by_id", new GroupCountingById(receiver));

        logger.info("Invoker initialized.");
    }

    public int getRecursionSize() {
        return files.size();
    }

    public void clearRecursion() {
        files.clear();
    }

    // Needed to be fixed. It's incorrect to access the input stream from server's Reciever
    // Change construction Client.out to Response.create.send
    public void execute_script(String file) {
        if(!new File(file).exists()) {
            // Client.out.print("File \"" + file + "\" does not exist\n");
            return;
        }
        if(files.containsKey(file)) {
            Integer value = files.get(file);
            if(value >= recursionDepth) {
                // Client.out.print("Recursion was cached. After executing file " + file + " " + recursionDepth + " times\n");
                files.clear();
                return;
            }
            files.put(file, ++value);
        } else {
            files.put(file, 1);
            if(files.size() == 1) {
                int input = 0;
                do {
                    try {
                        // Client.out.print("Please enter recursion depth (1, 50) : ");
                        // input = Integer.parseInt(Client.in.readLine());
                    } catch (NumberFormatException ignored) {}
                } while (input < 1 || input > 50);
                recursionDepth = input;
            }
        }

        try(InputStream fileInputStream = new FileInputStream(file);
            Reader decoder = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader lineReader = new BufferedReader(decoder)) {

            List<String> lines = new LinkedList<>();
            String line;
            while((line = lineReader.readLine()) != null) {
                lines.add(line);
            }
            // InputManager inputManager = Client.in;
            ListIterator<String> iterator = lines.listIterator(lines.size());
            while(iterator.hasPrevious()) {
                // Client.in.write(iterator.previous());
            }

        } catch (IOException e) {
            // Client.out.print("Command cannot be executed: file " + file + " does not exist.\n");
        }
    }


    // needed to be moved on client
    public String commandsInfo() {
        StringBuilder out = new StringBuilder();
        declaredCommands.forEach((key, value) -> {
            out.append(key);
            if(value.args().length > 0) {
                String enteredByUserArguments = String.join(
                        ", ",
                        Arrays.stream(value.args()).
                            filter(CommandArgument::isCreatingObject).
                            map(Object::toString).toArray(String[]::new)
                );

                String notEnteredByUserArguments = String.join(
                        ", ",
                        Arrays.stream(value.args()).
                                filter(e -> !e.isCreatingObject()).
                                map(Object::toString).toArray(String[]::new)
                );

                if(!enteredByUserArguments.equals(""))
                    out.append(" ").append(enteredByUserArguments);
                if(!notEnteredByUserArguments.equals(""))
                    out.append(" {").append(notEnteredByUserArguments).append("}");
            }

            out.append(" : ").append(value.getDescription()).append("\n");
        });

        return out.toString();
    }

    public String parseCommand(String line) {
        line = line.trim();
        if(line.equals("")) return "";

        String[] words = line.split(" ", 1);

        String command = words[0].toLowerCase();
        String[] args;
        if(words.length == 1)
            args = new String[0];
        else
            args = parseArgs(words[1]);
        logger.info("Command was parsed.");
        return executeCommand(command, args);
    }

    public String parseRequest(Request request) {
        return executeCommand(request.getCommandName(), request.getArgumentsToCommand());
    }

    public String executeCommand(String command, String[] args) {
        if (declaredCommands.containsKey(command)) {
            logger.info("Command executing.");
            try {
                return declaredCommands.get(command).execute(args);
            } catch (IllegalArgumentException e) {
                logger.error("Command {} require different count of arguments." + e.getMessage(), command);
                return "Invalid count of arguments";
            }
        } else {
            logger.error("Unknown command.");
            return "Unknown command " + command + ". Type help to get information about all commands.\n";
        }
    }
    
    private String[] parseArgs(String line) {
        return ARG_PAT.matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .map(e -> e.replaceAll("\"", ""))
                    .toArray(String[]::new);
    }

    public List<CommandDescription> getCommandsDescriptions() {
        List<CommandDescription> commandDescriptions = new ArrayList<>(declaredCommands.size());
        declaredCommands.forEach((u, v) -> {
            commandDescriptions.add(
                    new CommandDescription(u, v.args(), v.isCreatingObject()));
        });
        return commandDescriptions;
    }
}
