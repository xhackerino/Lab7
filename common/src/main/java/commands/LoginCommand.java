package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

public class LoginCommand implements Command {

    private final CollectionManager collectionManager;

    public LoginCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String getDescription() {
        return "login user";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return false;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.login(additionalInput);
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
