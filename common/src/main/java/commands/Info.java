package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

import java.time.ZonedDateTime;

/**
 * Команда 'info'. Выводит информацию о коллекции.
 */
public class Info implements Command {
    private final CollectionManager collectionManager;
    private final ZonedDateTime currTime;

    /**
     * Конструктор команды.
     * @param collectionManager менеджер коллекции.
     */
    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.currTime = ZonedDateTime.now();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "prints information about the collection (type, initialization date, number of elements, etc.)";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.getInfo();
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
