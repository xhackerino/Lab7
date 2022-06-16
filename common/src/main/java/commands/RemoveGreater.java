package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;


/**
 * Команда 'remove_greater'. Удаляет из коллекции все элементы, превышающие заданный.
 */
public class RemoveGreater implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public RemoveGreater(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "remove_greater";
    }

    @Override
    public String getDescription() {
        return "{element} : removes all elements greater than {element} from the collection";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        int index;
        try {
            if (args[0].trim().equals("")) throw new Exception();
            index = Integer.parseInt(args[0].trim());
        } catch (NumberFormatException e) {
            throw new CommandException("ID must be an integer");
        } catch (Exception e) {
            throw new CommandException("ID must be non-empty");
        }
        if (index < 0) {
            throw new CommandException("ID must be non-negative");
        }
        try {
        return collectionManager.removeAllGreater(user.getName(), index);
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
