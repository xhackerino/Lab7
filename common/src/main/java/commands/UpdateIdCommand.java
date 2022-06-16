package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'update id'. Обновляет значение элемента коллекции, id которого равен заданному.
 */
public class UpdateIdCommand implements Command {
    CollectionManager collectionManager;

    public UpdateIdCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getDescription() {
        return "{id of element} : update the value of the collection element whose id is equal to the given one";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
//        int id;
//        try {
//            if (args[0].equals("")) throw new Exception();
//            id = Integer.parseInt(args[0]);
//        } catch (NumberFormatException e) {
//            throw new CommandException("id must be an integer");
//        } catch (Exception e) {
//            throw new CommandException("id mustn't be empty");
//        }
//        if (id < 0) {
//            throw new CommandException("ID must be non-negative");
//        }
        try {
            return collectionManager.updateElement(user.getName(), additionalInput);
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}