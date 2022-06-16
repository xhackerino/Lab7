package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'exit'. Завершает выполнение программы.
 */
public class Exit implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param cm менеджер коллекции.
     */
    public Exit(CollectionManager cm) {
        this.collectionManager = cm;
    }
    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getDescription() {
        return "end of program";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return false;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        return collectionManager.exit();
    }
}
