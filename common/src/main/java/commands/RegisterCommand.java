package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

public class RegisterCommand implements Command {
    //register login password
    private final CollectionManager collectionManager;
    public RegisterCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "register new user";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return false;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.register(additionalInput);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}