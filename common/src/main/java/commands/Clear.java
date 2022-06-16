package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'clear'. Очищает коллекцию.
 */
public class Clear implements Command {
    private final CollectionManager collectionManager;

    /**
     * Конструктор команды.
     *
     * @param cm менеджер коллекции.
     */
    public Clear(CollectionManager cm) {
        this.collectionManager = cm;
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "clears the collection";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.clear();
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
