package src.commands;

import src.Client;
import src.logic.data.Receiver;
import src.logic.streams.InputManager;
import src.utils.requestModule.Request;

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
    }

    public int getRecursionSize() {
        return files.size();
    }

    public void clearRecursion() {
        files.clear();
    }

    // Needed to be fixed. It's incorrect to access the input stream from server's Reciever
    public void execute_script(String file) {
        if(!new File(file).exists()) {
            Client.out.print("File \"" + file + "\" does not exist\n");
            return;
        }
        if(files.containsKey(file)) {
            Integer value = files.get(file);
            if(value >= recursionDepth) {
                Client.out.print("Recursion was cached. After executing file " + file + " " + recursionDepth + " times\n");
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
                        Client.out.print("Please enter recursion depth (1, 50) : ");
                        input = Integer.parseInt(Client.in.readLine());
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
            InputManager inputManager = Client.in;
            ListIterator<String> iterator = lines.listIterator(lines.size());
            while(iterator.hasPrevious()) {
                Client.in.write(iterator.previous());
            }

        } catch (IOException e) {
            Client.out.print("Command cannot be executed: file " + file + " does not exist.\n");
        }
    }

    public String commandsInfo() {
        StringBuilder out = new StringBuilder();
        declaredCommands.forEach((key, value) -> {
            out.append(key +
                    (value.args().length == 0 ?
                            "" :
                            " [" + String.join(", ", value.args()) + "]") +
                    " (" + value.getDescription() + ")\n");
        });
        return out.toString();
    }

    public String parseCommand(String line) {
        line = line.trim();
        if(line.equals("")) return "";

        String[] words = line.split(" ");

        String command = words[0];
        String[] args;
        if(words.length == 1) { args = new String[0]; }
        else { args = Arrays.copyOfRange(words, 1, words.length); }

        if(declaredCommands.containsKey(command)) {
            return declaredCommands.get(command).execute(args);
        } else {
            return "Unknown command " + command + ". Type help to get information about all commands.\n";
        }
    }

    public String parseRequestCommand(Request request) {
        return parseCommand(request.getCommandName() + " " + request.getArgumentsToCommand());
    }
    
    private String[] parseArgs(String line) {
        return ARG_PAT.matcher(line)
                    .results()
                    .map(MatchResult::group)
                    .map(e -> e.replaceAll("\"", ""))
                    .toArray(String[]::new);
    }
}
