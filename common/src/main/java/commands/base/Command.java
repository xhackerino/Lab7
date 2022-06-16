package commands.base;

import exception.CommandException;

/**
 * интерфейс с объявленными методами, присущие всем командам
 */
public interface Command {
    /**
     * имя команды
     * @return name имя команды
     */
    String getName();

    /**
     * Описание команды
     * @return Description описание команды
     */
    String getDescription();

    boolean isAuthorizationNeeded();

    /**
     * Execute команду
     * @return Завершена или не завершена
     * @throws CommandException в случае ошибки выполнения команды.
     */
    CommandResult execute(String[] args, User user, String ... additionalInput) throws CommandException;
}
