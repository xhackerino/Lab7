package commands.base;

import java.io.Serializable;

public final class CommandRequestContainer implements Serializable {

    private static final long serialVersionUID = -7071028630270434499L;
    private final String commandName;
    private final String[] args;
    private final String[] input;
    private final User user;

    public CommandRequestContainer(String commandName, String[] args, User user, String ... input) {
        this.commandName = commandName;
        this.args = args;
        this.input = input;
        this.user = user;
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getArgs() {
        return args;
    }

    public String[] getInput() {
        return input;
    }

    public User getUser() {
        return user;
    }
}
