package manager;

import commands.base.CommandResult;
import commands.base.DatabaseManager;
import studyGroup.StudyGroup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.Stack;


/**
 * Класс, объект которого хранит в себе коллекцию и управляет ей.
 */
public class CollectionManagerImplServer implements CollectionManager {
    private final Stack<StudyGroup> studyGroup;
    private final FileManager fileManager;
    private final java.time.ZonedDateTime currTime;

    /**
     * Конструктор класса CollectionManager
     *
     * @param fm объект класса FileManager
     */

    public CollectionManagerImplServer(FileManager fm) {
        this.fileManager = fm;
        studyGroup = fileManager.ReadCollection();
        currTime = java.time.ZonedDateTime.now();
    }

    @Override
    public Stack<StudyGroup> getStudyGroup() {
        return studyGroup;
    }

    /**
     * Метод для получения информации о коллекции.
     *
     * @return возвращает информацию о коллекции
     */
    @Override
    public CommandResult getInfo() throws SQLException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement stmt = con.prepareStatement("SELECT * FROM studygroups");
        PreparedStatement stmt2 = con.prepareStatement("SELECT count(*) FROM studygroups");
        ResultSet rs = stmt.executeQuery();
        ResultSet rs2 = stmt2.executeQuery();
        StringBuilder sb = new StringBuilder();
        if (rs.next()) {
            sb.append("==> Collection of StudyGroup elements, ");
            sb.append("date of initialization: ").append(rs.getString("date")).append(", ");
            if (rs2.next()) {
                sb.append("number of elements in the collection: ").append(rs2.getInt(1)).append(".");
            }
        } else {
            return new CommandResult("==> Collection of StudyGroup elements, unfortunately it is is empty. You should add some elements.");
        }
        con.close();
        return new CommandResult(sb.toString());
    }

    /**
     * Метод для вывода коллекции в консоль.
     *
     * @return возвращает коллекцию в консоль в виде строк
     */

    @Override
    public CommandResult show() throws SQLException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement ps = con.prepareStatement("SELECT * FROM studygroups");
        ResultSet rs = ps.executeQuery();
        StringBuilder sb = new StringBuilder();
        while (rs.next()) {
            sb.append("ID: ").append(rs.getInt("id")).append(", ");
            sb.append("owner: ").append(rs.getString("owner")).append(", ");
            sb.append("group's name: ").append(rs.getString("name")).append(", ");
            sb.append("X coords: ").append(rs.getInt("x")).append(", ");
            sb.append("Y coords: ").append(rs.getDouble("y")).append(", ");
            sb.append("date of creation: ").append(rs.getString("date")).append(", ");
            sb.append("number of students: ").append(rs.getInt("studentscount")).append(", ");
            sb.append("number of expelled students: ").append(rs.getInt("expelledstudents")).append(", ");
            sb.append("form of education: ").append(rs.getString("formofeducation")).append(", ");
            sb.append("number of semester: ").append(rs.getString("semester")).append(", ");
            sb.append("admin name: ").append(rs.getString("adminname")).append(", ");
            sb.append("admin passport id: ").append(rs.getString("adminpassid")).append(", ");
            sb.append("admin skin color: ").append(rs.getString("admineyecolor")).append(", ");
            sb.append("admin hometown: ").append(rs.getString("adminnationality")).append("\n");
        }
        con.close();
        return new CommandResult("==> This is your collection:" + "\n" + sb);
    }

    /**
     * Метод для добавления элемента в коллекцию.
     */

    @Override
    public CommandResult add(String owner, String... additionalInput) throws SQLException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement st = con.prepareStatement("INSERT INTO studygroups (owner, name, x, y, date, studentscount, expelledstudents, formofeducation, semester, adminname, adminpassid, admineyecolor, adminnationality) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);");
        st.setString(1, additionalInput[1]);
        st.setString(2, additionalInput[2]);
        st.setInt(3, Integer.parseInt(additionalInput[11]));
        st.setDouble(4, Double.parseDouble(additionalInput[12]));
        st.setString(5, ZonedDateTime.now().toString());
        st.setInt(6, Integer.parseInt(additionalInput[3]));
        st.setInt(7, Integer.parseInt(additionalInput[4]));
        st.setString(8, additionalInput[5]);
        st.setString(9, additionalInput[6]);
        st.setString(10, additionalInput[7]);
        st.setString(11, additionalInput[8]);
        st.setString(12, additionalInput[9]);
        st.setString(13, additionalInput[10]);
        st.executeUpdate();
        con.close();
        return new CommandResult("==> Element has been added to collection <==");
    }

    /**
     * Метод для генерации следующего id для нового элемента.
     *
     * @return возвращает следующий идентификатор
     */

    @Override
    public long nextId() {
        if (studyGroup.isEmpty())
            return 1;
        else
            return studyGroup.lastElement().getId() + 1;
    }

    /**
     * Метод для проверки соответствия ID.
     *
     * @param Id идентификатор
     * @return возвращает true/false
     */
    @Override
    public boolean checkId(long Id) {
        for (StudyGroup group : studyGroup) {
            if (group.getId() == Id)
                return true;
        }
        return false;
    }

    /**
     * Метод для обновления значений элемента коллекции.
     */
    @Override
    public CommandResult updateElement(String owner, String... additionalInput) throws SQLException {
        Connection con = DatabaseManager.getConnection();

        PreparedStatement st = con.prepareStatement("SELECT * FROM studygroups WHERE id = ?;");
        st.setLong(1, Long.parseLong(additionalInput[0]));

        ResultSet rs = st.executeQuery();
        if(rs.next()) {

            String remoteOwner = rs.getString(2);
            if(remoteOwner.equals(owner)) {
                st = con.prepareStatement("UPDATE studygroups SET owner = ?, name = ?, x = ?, y = ?, date = ?, studentscount = ?, expelledstudents = ?, formofeducation = ?, semester = ?, adminname = ?, adminpassid = ?, admineyecolor = ?, adminnationality = ? WHERE id = ?");
                st.setString(1, additionalInput[1]);
                st.setString(2, additionalInput[2]);
                st.setInt(3, Integer.parseInt(additionalInput[11]));
                st.setDouble(4, Double.parseDouble(additionalInput[12]));
                st.setString(5, ZonedDateTime.now().toString());
                st.setInt(6, Integer.parseInt(additionalInput[3]));
                st.setInt(7, Integer.parseInt(additionalInput[4]));
                st.setString(8, additionalInput[5]);
                st.setString(9, additionalInput[6]);
                st.setString(10, additionalInput[7]);
                st.setString(11, additionalInput[8]);
                st.setString(12, additionalInput[9]);
                st.setString(13, additionalInput[10]);
                st.setLong(14, Long.parseLong(additionalInput[0]));
                st.executeUpdate();
                con.close();
                return new CommandResult("==> Element has just been updated. <==");
            } else {
                return new CommandResult("==> You don't have permissions <==");
            }
        } else {
            return new CommandResult("==> ID not found <==");
        }
    }

    /**
     * Метод для удаления элемента по ID.
     *
     * @param owner
     * @param id идентификатор
     */

    @Override
    public CommandResult removeElement(String owner, Long id) throws SQLException {
        Connection con = DatabaseManager.getConnection();

        PreparedStatement st = con.prepareStatement("SELECT * FROM studygroups WHERE id = ?;");
        st.setLong(1, id);

        ResultSet rs = st.executeQuery();
        if(rs.next()) {

            String remoteOwner = rs.getString(2);
            if(remoteOwner.equals(owner)) {
                st = con.prepareStatement("DELETE FROM studygroups WHERE id = ?;");
                st.setLong(1, id);
                st.executeUpdate();
                con.close();
                return new CommandResult("==> Element has just been removed by its ID <==");
            } else {
                return new CommandResult("==> You don't have permissions <==");
            }


        } else {
            return new CommandResult("==> ID not found <==");
        }
    }

    /**
     * Метод очищающий коллекцию.
     */

    @Override
    public CommandResult clear() throws SQLException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement st = con.prepareStatement("DELETE FROM studygroups;");
        st.executeUpdate();
        con.close();
        return new CommandResult("==> Collection has just been cleared <==");
    }

    /**
     * Метод выхода из программы.
     */
    @Override
    public CommandResult exit() {
        return new CommandResult("");
    }

    /**
     * Метод удаления элемента по индексу.
     *
     * @param owner
     * @param index индекс
     */
    @Override
    public CommandResult removeByIndex(String owner, int index) throws IllegalArgumentException {

//        if (index > this.getStudyGroup().size()) {
//            throw new IllegalArgumentException("Index is out of range");
//        }
//        int indeks = 0;
//        for (StudyGroup group : studyGroup) {
//            if (indeks == index) {
//                studyGroup.remove(group);
//                return new CommandResult("Element has been removed by its index");
//            } else {
//                indeks++;
//            }
//        }
        return new CommandResult("");
    }

    /**
     * Метод удаления последнего элемента коллекции.
     * @param owner имя владельца
     */
    @Override
    public CommandResult removeLast(String owner) throws SQLException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement st = con.prepareStatement("SELECT * FROM studygroups;");
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            String remoteOwner = rs.getString(2);
            if(remoteOwner.equals(owner)) {
                st = con.prepareStatement("DELETE FROM studygroups WHERE id = (SELECT MAX(id) FROM studygroups);");
                st.executeUpdate();
                con.close();
                return new CommandResult("==> Last element has just been removed <==");
            } else {
                return new CommandResult("==> You don't have permissions <==");
            }
        } else {
            return new CommandResult("==> Collection is already empty <==");
        }
    }

    /**
     * Метод удалить все элементы больше заданного
     */

    @Override
    public CommandResult removeAllGreater(String owner, int index) throws SQLException, IllegalArgumentException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement st = con.prepareStatement("SELECT * FROM studygroups;");
        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            String remoteOwner = rs.getString(2);
            if(remoteOwner.equals(owner)) {
                st = con.prepareStatement("DELETE FROM studygroups WHERE id > ?;");
                st.setInt(1, index);
                st.executeUpdate();
                con.close();
                return new CommandResult("==> All elements greater than " + index + " have just been removed <==");
            } else {
                return new CommandResult("==> You don't have permissions <==");
            }
        } else {
            return new CommandResult("==> Collection is already empty <==");
        }
    }

    /**
     * Метод подсчета суммы отчисленных студентов.
     */
    @Override
    public CommandResult sumOfExpelledStudents() throws SQLException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement st = con.prepareStatement("SELECT SUM(expelledStudents) FROM studygroups;");
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            long sum = rs.getLong(1);
            con.close();
            if (sum == 0) {
                return new CommandResult("==> Collection is empty <==");
            } else {
                return new CommandResult("==> Sum of expelled students is " + sum + " <==");
            }
        } else {
            con.close();
            return new CommandResult("");
        }
    }

    /**
     * Метод вывода элементов, значение поля name которых начинаются с заданной подстроки
     *
     * @param name имя в поле name
     */

    @Override
    public CommandResult filterStartsWithName(String name) throws SQLException {
        //filter by name and print all elements that start with this name
        Connection con = DatabaseManager.getConnection();
        PreparedStatement st = con.prepareStatement("SELECT * FROM studygroups WHERE name LIKE ?;");
        st.setString(1, name + "%");
        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            String result = "id: " + rs.getString(1) + ", owner of element: " + rs.getString(2);
            con.close();
            return new CommandResult("==> Elements that start with '" + name + "' are:" + "\n" + result + " <==");
        } else {
            con.close();
            return new CommandResult("==> There are no elements starting with that... <==");
        }

    }

    /**
     * Метод вывода значений поля StudentsCount всех элементов в порядке возрастания
     */

    @Override
    public CommandResult printFieldAscendingStudentsCount() throws SQLException {
        Connection con = DatabaseManager.getConnection();
        PreparedStatement st = con.prepareStatement("SELECT * FROM studygroups ORDER BY studentsCount ASC;");
        ResultSet rs = st.executeQuery();
        StringBuilder result = new StringBuilder();
        if (rs.next()) {
            //todo missing 3rd
            while (rs.next()) {
                result.append("ID: ").append(rs.getInt("id")).append(", ");
                result.append("owner: ").append(rs.getString("owner")).append(", ");
                result.append("group's name: ").append(rs.getString("name")).append(", ");
                result.append("X coords: ").append(rs.getInt("x")).append(", ");
                result.append("Y coords: ").append(rs.getDouble("y")).append(", ");
                result.append("date of creation: ").append(rs.getString("date")).append(", ");
                result.append("number of students: ").append(rs.getInt("studentscount")).append(", ");
                result.append("number of expelled students: ").append(rs.getInt("expelledstudents")).append(", ");
                result.append("form of education: ").append(rs.getString("formofeducation")).append(", ");
                result.append("number of semester: ").append(rs.getString("semester")).append(", ");
                result.append("admin name: ").append(rs.getString("adminname")).append(", ");
                result.append("admin passport id: ").append(rs.getString("adminpassid")).append(", ");
                result.append("admin skin color: ").append(rs.getString("admineyecolor")).append(", ");
                result.append("admin hometown: ").append(rs.getString("adminnationality")).append("\n");
            }
        } else {
            con.close();
            return new CommandResult("==> Collection is empty <==");
        }
        con.close();
        return new CommandResult("==> Elements in ascending order by students count are:" + "\n" + result + " <==");
    }

    /**
     * Логин в базу данных
     *
     * @param additionalInput логин и пароль
     * @return логин и пароль
     * @throws SQLException исключение при ошибке в базе данных
     */

    @Override
    public CommandResult login(String... additionalInput) throws SQLException {
        if (additionalInput.length == 2) {

            Connection con = DatabaseManager.getConnection();
            PreparedStatement st = con.prepareStatement("SELECT * FROM USERS WHERE login = ?;");
            st.setString(1, additionalInput[0]);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                if (additionalInput[1].equals(rs.getString(2))) {
                    con.close();
                    return new CommandResult(additionalInput[0] + ":" + additionalInput[1]);
                }
            } else {
                con.close();
                return new CommandResult("");
            }
            con.close();
            return new CommandResult("");
        } else {
            return new CommandResult("");
        }
    }

    /**
     * Регистрация пользователя в базе данных
     *
     * @param additionalInput логин и пароль
     * @return логин и пароль
     * @throws IllegalArgumentException исключение при ошибке в базе данных
     * @throws SQLException исключение при ошибке в базе данных
     */

    @Override
    public CommandResult register(String... additionalInput) throws IllegalArgumentException, SQLException {
        if (additionalInput[0] == null || additionalInput[1] == null) {
            throw new IllegalArgumentException("=>> Login and password can't be empty <<=");
        } else if (additionalInput[0].equals("") || additionalInput[1].equals("")) {
            throw new IllegalArgumentException("=>> Login and password can't be empty <<=");
        } else {
            Connection con = DatabaseManager.getConnection();
            PreparedStatement st = con.prepareStatement("SELECT * FROM USERS WHERE login = ?;");
            st.setString(1, additionalInput[0]);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                con.close();
                return new CommandResult("=>> User with this login already exists <<=");
            } else {
                st = con.prepareStatement("INSERT INTO users (login, pass) VALUES ( ? , ? );");
                st.setString(1, additionalInput[0]);
                st.setString(2, additionalInput[1]);
                st.executeUpdate();
                con.close();
                return new CommandResult("=>> User '" + additionalInput[0] + "' has been registered <<=");
            }
        }
    }
}