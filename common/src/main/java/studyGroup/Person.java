package studyGroup;

import java.io.Serializable;

/**
 * Класс Person
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 7895807501343981770L;

    private final String name; //Поле не может быть null, Строка не может быть пустой
    private final String passportID; //Поле не может быть null
    private final Color eyeColor; //Поле не может быть null
    private final Country nationality; //Поле может быть null

    /**
     * Конструктор класса Person.
     *
     * @param name        имя. Не может быть null, строка не пустая
     * @param passportID  Номер паспорта. Не может быть null
     * @param eyeColor    Цвет глаз. Не может быть null
     * @param nationality Национальность. Не может быть null
     */
    public Person(String name, String passportID, Color eyeColor, Country nationality) {
        this.name = name;
        this.passportID = passportID;
        this.eyeColor = eyeColor;
        this.nationality = nationality;
    }
    public String getName() {
        return name;
    }


    public String getPassportID() {
        return passportID;
    }

    public Color getEyeColor() {
        return eyeColor;
    }


    public Country getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Person {" +
                "name ='" + name + '\'' +
                ", passport ID ='" + passportID + '\'' +
                ", eye color = " + eyeColor +
                ", nationality = " + nationality +
                '}';
    }
}
