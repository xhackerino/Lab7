package manager;

import commands.base.CommandResult;
import studyGroup.StudyGroup;

import java.sql.SQLException;
import java.util.Stack;

public interface CollectionManager {
    Stack<StudyGroup> getStudyGroup();

    CommandResult getInfo() throws SQLException;

    CommandResult show() throws SQLException;

    CommandResult add(String owner, String ... additionalInput) throws SQLException;

    long nextId();

    boolean checkId(long Id);

    CommandResult updateElement(String owner, String ... additionalInput) throws SQLException;

    CommandResult removeElement(String owner, Long id) throws SQLException;

//    CommandResult save(); //work

    CommandResult clear() throws SQLException;

    CommandResult exit();

    CommandResult removeByIndex(String owner, int index) throws SQLException, IllegalArgumentException;

    CommandResult removeLast(String owner) throws SQLException;

    CommandResult removeAllGreater(String owner, int index) throws SQLException, IllegalArgumentException;

    CommandResult sumOfExpelledStudents() throws SQLException;

    CommandResult filterStartsWithName(String name) throws SQLException;

    CommandResult printFieldAscendingStudentsCount() throws SQLException;

    CommandResult login(String... additionalInput) throws SQLException;

    CommandResult register(String... additionalInput) throws IllegalArgumentException, SQLException ;
}
