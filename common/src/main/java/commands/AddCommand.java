package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'add'. Добавляет новый элемент в коллекцию.
 */
public class AddCommand implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param collectionManager менеджер коллекции.
     */
    public AddCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return "{element} : add new element to collection";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.add(user.getName(), additionalInput);
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
