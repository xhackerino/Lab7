package studyGroup;

import commands.base.User;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Главный элемент коллекции, описывает одну запись об учебной группе.
 */
public class StudyGroup implements Comparable<StudyGroup>, Serializable {

    private static final long serialVersionUID = -4885580750134398770L;

    private Long id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String owner;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private Long expelledStudents; //Значение поля должно быть больше 0, Поле может быть null
    private FormOfEducation formOfEducation; //Поле может быть null
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не мо

    /**
     * @param id               ID. Не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
     * @param owner            Владелец объекта
     * @param name             Name. Не может быть null, Строка не может быть пустой
     * @param coordinates      Coordinates. Не может быть null
     * @param creationDate     Creation Date. Не может быть null, Значение этого поля должно генерироваться автоматически
     * @param studentsCount    Students Count. Значение поля должно быть больше 0
     * @param expelledStudents Expelled Students. Значение поля должно быть больше 0, Поле может быть null
     * @param formOfEducation  Form of education. Поле может быть null
     * @param semesterEnum     Number of semesters. Поле не может быть null
     * @param groupAdmin       Admin of group. Поле не может быть null
     */
    public StudyGroup(Long id, String owner, String name, ZonedDateTime creationDate, long studentsCount, Long expelledStudents,
                      FormOfEducation formOfEducation, Semester semesterEnum, Person groupAdmin, Coordinates coordinates) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.formOfEducation = formOfEducation;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id Argument id must be greater than 0
     */
    public void setId(Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Argument id must be greater than 0");
        } else {
            this.id = id;
        }
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Name mustn't be null or empty
     */

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name mustn't be null or empty");
        } else {
            this.name = name;
        }
    }

    /**
     * @return coordinates
     */

    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * @param coordinates Coordinates can't be null...
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Coordinates can't be null...");
        } else {
            this.coordinates = coordinates;
        }
    }

    /**
     * @return creationDate
     */
    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    /**
     * @param creationDate Date of creation can't be null
     */
    public void setCreationDate(ZonedDateTime creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Date of creation can't be null");
        } else {
            this.creationDate = ZonedDateTime.now();
        }
    }

    /**
     * @return studentsCount
     */
    public long getStudentsCount() {
        return studentsCount;
    }

    /**
     * @param studentsCount Number of students must be greater than 0
     */
    public void setStudentsCount(long studentsCount) {
        if (studentsCount <= 0) {
            throw new IllegalArgumentException("Number of students must be greater than 0");
        } else {
            this.studentsCount = studentsCount;
        }
    }

    /**
     * @return expelledStudents
     */
    public Long getExpelledStudents() {
        return expelledStudents;
    }

    /**
     * @param expelledStudents Number of kicked students must be greater than 0 or not null
     */
    public void setExpelledStudents(Long expelledStudents) {
        if (expelledStudents <= 0 || expelledStudents == null) {
            throw new IllegalArgumentException("Number of kicked students must be greater than 0 or not null");
        } else {
            this.expelledStudents = expelledStudents;
        }
    }

    /**
     * @return formOfEducation
     */
    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    /**
     * @param formOfEducation Form of students education can't be null
     */
    public void setFormOfEducation(FormOfEducation formOfEducation) {
        if (formOfEducation == null) {
            throw new IllegalArgumentException("Form of students education can't be null");
        } else {
            this.formOfEducation = formOfEducation;
        }
    }

    /**
     * @return semesterEnum
     */
    public Semester getSemesterEnum() {
        return semesterEnum;
    }

    /**
     * @param semesterEnum Number of semesters can not be null
     */
    public void setSemesterEnum(Semester semesterEnum) {
        if (semesterEnum == null) {
            throw new IllegalArgumentException("Number of semesters can not be null");
        } else {
            this.semesterEnum = semesterEnum;
        }
    }

    /**
     * @return groupAdmin
     */
    public Person getGroupAdmin() {
        return groupAdmin;
    }

    /**
     * @param groupAdmin There should be group admin
     */
    public void setGroupAdmin(Person groupAdmin) {
        if (groupAdmin == null) {
            throw new IllegalArgumentException("There should be group admin");
        } else {
            this.groupAdmin = groupAdmin;
        }
    }

    @Override
    public String toString() {
        return "Study group {" +
                "id=" + id +
                ", owner='" + owner + '\'' +
                ", name ='" + name + '\'' +
                ", Coordinates = " + coordinates +
                ", date of creation = " + creationDate +
                ", number of students = " + studentsCount +
                ", number of expelled students = " + expelledStudents +
                ", form of education = " + formOfEducation +
                ", semester = " + semesterEnum +
                ", admin of group = " + groupAdmin +
                '}';
    }

    @Override
    public int compareTo(StudyGroup o) {
        return this.creationDate.compareTo(o.getCreationDate());
    }
}