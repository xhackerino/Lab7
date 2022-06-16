package commands;

import commands.base.Command;
import commands.base.CommandResult;
import commands.base.User;
import exception.CommandException;
import manager.CollectionManager;


/**
 * Команда 'print_field_ascending_students_count'. Выводит значения поля studentsCount всех элементов коллекции в порядке возрастания.
 */
public class PrintFieldAscendingStudentsCount implements Command {
    CollectionManager collectionManager;

    /**
     * Конструктор команды.
     * @param collectionManager менеджер коллекции.
     */
    public PrintFieldAscendingStudentsCount(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    @Override
    public String getName() {
        return "print_field_ascending_students_count";
    }

    @Override
    public String getDescription() {
        return "prints the values of the studentsCount field of all elements in ascending order";
    }

    @Override
    public boolean isAuthorizationNeeded() {
        return true;
    }

    @Override
    public CommandResult execute(String[] args, User user, String... additionalInput) throws CommandException {
        try {
            return collectionManager.printFieldAscendingStudentsCount();
        } catch (java.sql.SQLException throwables) {
            return new CommandResult(throwables.getMessage());
        }
    }
}
