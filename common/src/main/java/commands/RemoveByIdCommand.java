package commands;


import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'remove_by_id'. Удаляет элемент из коллекции по его id.
 */
public class RemoveByIdCommand implements Command {
    CollectionManager collectionManager;

    public RemoveByIdCommand(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "remove_by_id";
    }

    @Override
    public String getDescription() {
        return "{id} : removes element by its id";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        if (args.length != 1) throw new CommandException("Enter correct id (non-negative integer)");
        long id;
        try {
            if (args[0].equals("")) throw new Exception();
            id = Long.parseLong(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("id must be an integer");
        } catch (Exception e) {
            throw new CommandException("id mustn't be empty");
        }
        if (id < 0) {
            throw new CommandException("id must be non-negative");
        } else {
            try {
                return collectionManager.removeElement(user.getName(), id);
            } catch (java.sql.SQLException throwables) {
                return new CommandResult(throwables.getMessage());
            }
        }
    }
}

