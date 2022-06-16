package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;

/**
 * Команда 'filter_starts_with_name'. Выводит элементы коллекции, значение поля name которых начинается с заданной подстроки.
 */
public class FilterStartsWithName implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор класса
     * @param collectionManager объект CollectionManager
     */

    public FilterStartsWithName(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "filter_starts_with_name";
    }

    @Override
    public String getDescription() {
        return "name : print elements, which 'name' field value starts with the given substring";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.filterStartsWithName(args[0]);
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
