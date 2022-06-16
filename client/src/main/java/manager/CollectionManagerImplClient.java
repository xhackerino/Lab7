package manager;

import commands.base.CommandResult;
import org.apache.commons.codec.digest.DigestUtils;
import studyGroup.StudyGroup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class CollectionManagerImplClient implements CollectionManager {
    private Stack<StudyGroup> studyGroup;
    private final ConsoleManager consoleManager;

    public CollectionManagerImplClient(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    @Override
    public Stack<StudyGroup> getStudyGroup() {
        return studyGroup;
    }

    /**
     * входящие ограничения на ввод данных
     */

    @Override
    public CommandResult getInfo() throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult show() throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult add(String owner, String... additionalInput) throws SQLException {
        StudyGroup studyGroup = consoleManager.askGroup(this.nextId(), owner);
        ArrayList<String> additional = new ArrayList<>();
        additional.add(studyGroup.getId().toString());
        additional.add(owner);
        additional.add(studyGroup.getName());
        additional.add(String.valueOf(studyGroup.getStudentsCount()));
        additional.add(String.valueOf(studyGroup.getExpelledStudents()));
        additional.add(studyGroup.getFormOfEducation().toString());
        additional.add(studyGroup.getSemesterEnum().toString());
        additional.add(studyGroup.getGroupAdmin().getName());
        additional.add(studyGroup.getGroupAdmin().getPassportID());
        additional.add(studyGroup.getGroupAdmin().getEyeColor().toString());
        additional.add(studyGroup.getGroupAdmin().getNationality().toString());
        additional.add(studyGroup.getCoordinates().getX().toString());
        additional.add(String.valueOf(studyGroup.getCoordinates().getY()));
        String[] to = new String[additional.size()];
        additional.toArray(to);
        return new CommandResult("", to);
    }

    @Override
    public long nextId() {
        return 1;
    }

    @Override
    public boolean checkId(long Id) {
        return true;
    }

    @Override
    public CommandResult updateElement(String owner, String... additionalInput) throws SQLException {
        try {
            StudyGroup studyGroup = consoleManager.askGroupForUpdate(this.nextId(), owner);
            ArrayList<String> additional = new ArrayList<>();
            additional.add(studyGroup.getId().toString());
            additional.add(studyGroup.getOwner());
            additional.add(studyGroup.getName());
            additional.add(String.valueOf(studyGroup.getStudentsCount()));
            additional.add(String.valueOf(studyGroup.getExpelledStudents()));
            additional.add(studyGroup.getFormOfEducation().toString());
            additional.add(studyGroup.getSemesterEnum().toString());
            additional.add(studyGroup.getGroupAdmin().getName());
            additional.add(studyGroup.getGroupAdmin().getPassportID());
            additional.add(studyGroup.getGroupAdmin().getEyeColor().toString());
            additional.add(studyGroup.getGroupAdmin().getNationality().toString());
            additional.add(studyGroup.getCoordinates().getX().toString());
            additional.add(String.valueOf(studyGroup.getCoordinates().getY()));
            String[] to = new String[additional.size()];
            additional.toArray(to);
            return new CommandResult("", to);
        } catch (NullPointerException e) {
            System.err.println("What are you doing? Stop that! Don't Ctrl+D my program, you are not good..." + "\n" + "Restart the program and be polite.");
            System.exit(0);
            return new CommandResult("");
        }
    }

    @Override
    public CommandResult removeElement(String owner, Long id) throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult clear() throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult exit() {
        System.out.println("Ending of program. Bye!");
        System.exit(0);
        return new CommandResult("");
    }

    @Override
    public CommandResult removeByIndex(String owner, int index) throws SQLException, IllegalArgumentException {
        return new CommandResult("");
    }

    @Override
    public CommandResult removeLast(String owner) throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult removeAllGreater(String owner, int index) throws SQLException, IllegalArgumentException {
        return new CommandResult("");
    }

    @Override
    public CommandResult sumOfExpelledStudents() throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult filterStartsWithName(String name) throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult printFieldAscendingStudentsCount() throws SQLException {
        return new CommandResult("");
    }

    @Override
    public CommandResult login(String... additionalInput) throws SQLException, NullPointerException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("==> Enter your username:");
            String name = null, pass = null;
            if (scanner.hasNextLine())
                name = scanner.nextLine();
            System.out.println("==> Enter your password:");
            if (scanner.hasNextLine())
                pass = scanner.nextLine();
            return new CommandResult("", name, DigestUtils.sha512Hex(pass));
        } catch (NullPointerException e) {
            System.err.println("What are you doing? Stop that! Don't Ctrl+D my program, you are not good..." + "\n" + "Restart the program and be polite.");
            System.exit(0);
            return new CommandResult("");
        }
    }

    @Override
    public CommandResult register(String... additionalInput) throws
            IllegalArgumentException, SQLException, NullPointerException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("==> Enter your username:");
            String name = null, pass = null;
            if (scanner.hasNextLine())
                name = scanner.nextLine();
            System.out.println("==> Enter your password:");
            if (scanner.hasNextLine())
                pass = scanner.nextLine();
            return new CommandResult("", name, DigestUtils.sha512Hex(pass));
        } catch (NullPointerException e) {
            System.err.println("What are you doing? Stop that! Don't Ctrl+D my program, you are not good..." + "\n" + "Restart the program and be polite.");
            System.exit(0);
            return new CommandResult("");
        }
    }
}
