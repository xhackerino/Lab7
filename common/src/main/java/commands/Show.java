package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;


/**
 * Команда 'show'. Выводит на экран все элементы коллекции.
 */
public class Show implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды 'show'.
     * @param cm объект класса CollectionManager.
     */
    public Show(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "prints all elements of the collection in string representation";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.show();
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}