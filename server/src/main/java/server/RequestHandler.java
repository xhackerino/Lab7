package server;

import commands.base.Command;
import commands.base.CommandRequestContainer;
import commands.base.CommandResponseContainer;
import commands.base.CommandResult;
import exception.CommandException;

import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.HashMap;

public final class RequestHandler implements Runnable {

    private final CommandRequestContainer inputContainer;
    private final DatagramChannel datagramChannel;
    private final HashMap<String, Command> commandHashMap;
    private final SocketAddress from;

    public RequestHandler(CommandRequestContainer inputContainer, DatagramChannel datagramChannel, HashMap<String, Command> commandHashMap, SocketAddress from) {
        this.inputContainer = inputContainer;
        this.datagramChannel = datagramChannel;
        this.commandHashMap = commandHashMap;
        this.from = from;
    }

    @Override
    public void run() {

        String command = inputContainer.getCommandName();
        try {
            if (commandHashMap.containsKey(command)) {
                Command c = commandHashMap.get(command);
                CommandResult result;
                if(c.isAuthorizationNeeded()) {
                    if(inputContainer.getUser() != null) {
                        result = c.execute(inputContainer.getArgs(), inputContainer.getUser(), inputContainer.getInput());
                        System.out.print(from + " - ");
                        System.out.println(result.getResult());
                    } else {
                        throw new CommandException("Authorization required!");
                    }
                } else {
                    result = c.execute(inputContainer.getArgs(), inputContainer.getUser(), inputContainer.getInput());
                    if (!c.getName().equals("login")) {
                        System.out.print(from + " - ");
                        System.out.println(result.getResult());
                    } else {
                        System.out.print(from + " - required login");
                    }
                }

                CommandResponseContainer commandResponseContainer = new CommandResponseContainer(result.getResult());
                new Thread(new RequestWriter(commandResponseContainer, datagramChannel, from)).start();
            } else {
                CommandResponseContainer commandResponseContainer = new CommandResponseContainer("Command not found");
                new Thread(new RequestWriter(commandResponseContainer, datagramChannel, from)).start();
            }
        } catch (CommandException e) {
            CommandResponseContainer commandResponseContainer = new CommandResponseContainer(e.getMessage());
            new Thread(new RequestWriter(commandResponseContainer, datagramChannel, from)).start();
        }
    }
}
