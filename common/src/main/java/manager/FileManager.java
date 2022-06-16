package manager;

import studyGroup.*;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Stack;

import static manager.ConsoleManager.print;
import static manager.ConsoleManager.printError;

/**
 * Класс для работы с файлами
 */
public class FileManager {
    private final String fileName;
    private static ArrayList<String> fields;
    private static ArrayList<String> keys;

    /**
     * Метод для получения имени файла
     *
     * @param fileName имя файла
     */
    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    private static void MakeFieldsArray(String line) {
        StringBuilder word = new StringBuilder();
        fields = new ArrayList<>();
        keys = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ';') {
                word.append(line.charAt(i));
            } else {
                fields.add(word.toString());
                word = new StringBuilder();
            }
        }
        fields.add(word.toString());
    }

    /**
     * Метод для записи коллекции в файл
     *
     * @param stack коллекция
     */
    public void WriteCollection(Stack<StudyGroup> stack) {
        if (fileName == null) {
            printError("Can't be saved");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            StringBuilder stek = new StringBuilder("ID;OWNER;NAME;CREATIONDATE;STUDENTSCOUNT;EXPELLEDSTUDENTS;FORMOFEDUCATION;" +
                    "SEMESTERENUM;PERSONNAME;PASSPORTID;EYECOLOR;NATIONALITY;X;Y\n");
            for (StudyGroup StudyGroup : stack) {
                stek.append(StudyGroup.getId().toString()).append(";").append(StudyGroup.getOwner()).append(";").append(StudyGroup.getName()).append(";").append(StudyGroup.getCreationDate().toString()).append(";").append(StudyGroup.getStudentsCount()).append(";").append(StudyGroup.getExpelledStudents().toString()).append(";").append(StudyGroup.getFormOfEducation().toString()).append(";").append(StudyGroup.getSemesterEnum()).append(";").append(StudyGroup.getGroupAdmin().getName()).append(";").append(StudyGroup.getGroupAdmin().getPassportID()).append(";").append(StudyGroup.getGroupAdmin().getEyeColor().toString()).append(";").append(StudyGroup.getGroupAdmin().getNationality().toString()).append(";").append(StudyGroup.getCoordinates().getX().toString()).append(";").append(StudyGroup.getCoordinates().getY()).append("\n");
            }
            fos.write(stek.toString().getBytes());
        } catch (IOException e) {
            printError("File not found");
        }
    }

    /**
     * Метод для считывания коллекции из файла
     *
     * @return коллекция
     */
    public Stack<StudyGroup> ReadCollection() {
        if (fileName == null) {
            return new Stack<>();
        }
        try {
            Stack<StudyGroup> stack = new Stack<>();
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            MakeFieldsArray(reader.readLine());
            while (reader.ready()) {
                stack.add(studyGroupFromCSV(reader.readLine()));
            }
            reader.close();
//            print("Collection was loaded");
            return stack;
        } catch (FileNotFoundException e) {
            printError("Collection not found");
        } catch (NoSuchElementException e) {
            printError("File is empty");
        } catch (IOException e) {
            printError("Error with access to file");
        }
        return new Stack<>();
    }

    private StudyGroup studyGroupFromCSV(String line) {
        StringBuilder word = new StringBuilder();
        Long id = null;
        String owner = null;
        String name = null;
        java.time.ZonedDateTime creationDate = null;
        Long coordinatesX = null;
        Double coordinatesY = null;
        Long studentsCount = null;
        Long expelledStudents = null;
        FormOfEducation formOfEducation = null;
        Semester semesterEnum = null;
        Person groupAdmin;
        StudyGroup studyGroup;
        Coordinates coordinates;
        String personName = null;
        String personPassportId = null;
        Color eyeColor = null;
        Country nationality = null;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) != ';') {
                word.append(line.charAt(i));
            } else {
                keys.add(word.toString());
                word = new StringBuilder();
            }

        }
        keys.add(word.toString());
//        System.out.println(keys);
        for (int i = 0; i < keys.size(); i++) {
            switch (Chooser(i)) {
                case 2:
                    owner = keys.get(i);
                    break;
                case 3:
                    name = keys.get(i);
                    break;
                case 4:
                    creationDate = ZonedDateTime.parse(keys.get(i));
                    break;
                case 5:
                    studentsCount = Long.parseLong(keys.get(i));
                    break;
                case 6:
                    if (keys.get(i).equals("null")) {
                        expelledStudents = null;
                    } else {
                        expelledStudents = Long.parseLong(keys.get(i));
                    }
                    break;
                case 7:
                    if (keys.get(i).equals("null")) {
                        formOfEducation = null;
                    } else {
                        formOfEducation = FormOfEducation.valueOf(keys.get(i));
                    }
                    break;
                case 8:
                    semesterEnum = Semester.valueOf(keys.get(i));
                    break;
                case 9:
                    personName = keys.get(i);
                    break;
                case 10:
                    personPassportId = keys.get(i);
                    break;
                case 11:
                    eyeColor = Color.valueOf(keys.get(i));
                    break;
                case 12:
                    nationality = Country.valueOf(keys.get(i));
                    break;
                case 13:
                    coordinatesX = Long.parseLong(keys.get(i));
                    break;
                case 14:
                    coordinatesY = Double.parseDouble(keys.get(i));
                    break;
            }
        }
        groupAdmin = new Person(personName, personPassportId, eyeColor, nationality);
        coordinates = new Coordinates(coordinatesX, coordinatesY);
        studyGroup = new StudyGroup(id, owner, name, creationDate, studentsCount, expelledStudents,
                formOfEducation, semesterEnum, groupAdmin, coordinates);
        keys.clear();
        return studyGroup;
    }

    /**
     * Метод для выбора поля из коллекции
     *
     * @param i номер поля
     * @return номер поля
     */
    private static int Chooser(int i) {
        switch (fields.get(i)) {
            case "ID":
                return 1;
            case "OWNER":
                return 2;
            case "NAME":
                return 3;
            case "CREATIONDATE":
                return 4;
            case "STUDENTSCOUNT":
                return 5;
            case "EXPELLEDSTUDENTS":
                return 6;
            case "FORMOFEDUCATION":
                return 7;
            case "SEMESTERENUM":
                return 8;
            case "PERSONNAME":
                return 9;
            case "PASSPORTID":
                return 10;
            case "EYECOLOR":
                return 11;
            case "NATIONALITY":
                return 12;
            case "X":
                return 13;
            case "Y":
                return 14;
            default:
                return 0;
        }
    }
}