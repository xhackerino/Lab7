package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'remove_last'. Удаляет последний элемент из коллекции.
 */
public class RemoveLast implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public RemoveLast(CollectionManager cm){
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "remove_last";
    }

    @Override
    public String getDescription() {
        return "removes the last element of the collection";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.removeLast(user.getName());
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
