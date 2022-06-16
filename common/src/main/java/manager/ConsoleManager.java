package manager;


import studyGroup.*;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Менеджер консоли
 */
public class ConsoleManager {
    private Scanner scanner;

    /**
     * Ввод данных
     * @param scanner Сканер для ввода
     */
    public ConsoleManager(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Вывод на экран
     * @param message Сообщение
     */
    public static void print(String message) {
        System.out.println(message);
    }

    /**
     * Вывод на экран
     * @param message Сообщение об ошибке
     */
    public static void printError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Аскер для нового элемента
     * @param Id идентификатор
     * @return Новый элемент
     */
    public StudyGroup askGroup(Long Id, String owner) {
        String name = parseString("Enter group name:");
        java.time.ZonedDateTime creationDate = ZonedDateTime.now();
        long stdCnt;
        while (true) {
            stdCnt = parseLong("Enter number of students in group:");
            if (stdCnt < 0) {
                printError("Number of students must be non-negative");
            } else {
                break;
            }
        }
        long xpdStd;
        while (true) {
            xpdStd = parseLong("Enter number of expelled students:");
            if (xpdStd < 0) {
                printError("Number of expelled students must be non-negative");
            } else {
                break;
            }
        }
        FormOfEducation foe = parseFormOfEducation("Enter form of education");
        Semester sem = parseSemester("Enter semester");
        String grAdm = parseString("Enter group admin's name:");
        String passId = parseString("Enter passport ID:");
        Color color = parseColor("Enter color:");
        Country nazi = parseCountry("Enter nationality");
        Long x = parseLong("Enter X coordinate:");
        double y;
        while (true) {
            y = parseDouble("Enter Y coordinate:");
            if (y <= -352) {
                print("Error: y must be greater than -352");
            } else {
                break;
            }
        }
        return new StudyGroup(Id,owner, name, creationDate, stdCnt,
                xpdStd, foe, sem, new Person(grAdm, passId, color, nazi), new Coordinates(x, y));
    }
    public StudyGroup askGroupForUpdate(Long Id, String owner) {
        Long id = parseLong("Enter ID of element you want to update: ");
        String name = parseString("Enter group name:");
        java.time.ZonedDateTime creationDate = ZonedDateTime.now();
        long stdCnt;
        while (true) {
            stdCnt = parseLong("Enter number of students in group:");
            if (stdCnt < 0) {
                printError("Number of students must be non-negative");
            } else {
                break;
            }
        }
        long xpdStd;
        while (true) {
            xpdStd = parseLong("Enter number of expelled students:");
            if (xpdStd < 0) {
                printError("Number of expelled students must be non-negative");
            } else {
                break;
            }
        }
        FormOfEducation foe = parseFormOfEducation("Enter form of education");
        Semester sem = parseSemester("Enter semester");
        String grAdm = parseString("Enter group admin's name:");
        String passId = parseString("Enter passport ID:");
        Color color = parseColor("Enter color:");
        Country nazi = parseCountry("Enter nationality");
        Long x = parseLong("Enter X coordinate:");
        double y;
        while (true) {
            y = parseDouble("Enter Y coordinate:");
            if (y <= -352) {
                print("Error: y must be greater than -352");
            } else {
                break;
            }
        }
        return new StudyGroup(id, owner, name, creationDate, stdCnt,
                xpdStd, foe, sem, new Person(grAdm, passId, color, nazi), new Coordinates(x, y));
    }
    public String parseString(String message) {
        String str = null;
        while (str == null) {
            try {
                print(message);
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new Exception();
                if (message2 == null)
                    throw new Exception();
                str = message2;
            } catch (Exception e) {
                printError("It's an empty string");
            }
        }
        return str;
    }
    public Long parseLong(String message) {
        Long out = null;
        while (out == null) {
            try {
                print(message);
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new Exception();
                if (message2 == null)
                    throw new Exception();
                if (Long.parseLong(message2) <= 0) {
                    throw new NumberFormatException();
                }
                out = Long.parseLong(message2);
            } catch (NumberFormatException e) {
                printError("Incorrect number. It must be non-negative and not zero integer");
            } catch (Exception e) {
                printError("It's an empty string");
            }
        }
        return out;
    }
    public Double parseDouble(String message) {
        Double outta = null;
        while (outta == null) {
            try {
                print(message);
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new Exception();
                if (message2 == null)
                    throw new Exception();
                outta = Double.parseDouble(message2);
            } catch (NumberFormatException e) {
                printError("It's not a number");
            } catch (Exception e) {
                printError("It's an empty string");
            }
        }
        return outta;
    }
    public Semester parseSemester(String message) {
        Semester out = null;
        while (out == null) {
            try {
                print(message);
                print("List of semesters:\n" + Arrays.toString(Semester.values()));
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new Exception();
                if (message2 == null)
                    throw new Exception();
                out = Semester.valueOf(message2);
            } catch (IllegalArgumentException e) {
                printError("That's not a semester, please, use list of semesters");
            } catch (Exception e) {
                printError("It's an empty string");
            }
        }
        return out;
    }
    public FormOfEducation parseFormOfEducation(String message) {
        FormOfEducation out = null;
        while (out == null) {
            try {
                print(message);
                print("Forms of education:\n" + Arrays.toString(FormOfEducation.values()));
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new Exception();
                if (message2 == null)
                    throw new Exception();
                out = FormOfEducation.valueOf(message2);
            } catch (IllegalArgumentException e) {
                printError("Not a form of education, please, use list of forms of education");
            } catch (Exception e) {
                printError("It's an empty string");
            }
        }
        return out;
    }
    public Color parseColor(String message) {
        Color out = null;
        while (out == null) {
            try {
                print(message);
                print("Colors:\n" + Arrays.toString(Color.values()));
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new Exception();
                if (message2 == null)
                    throw new Exception();
                out = Color.valueOf(message2);
            } catch (IllegalArgumentException e) {
                printError("That's not a color, please, use list of colors");
            } catch (Exception e) {
                printError("It's an empty string");
            }
        }
        return out;
    }
    public Country parseCountry(String message) {
        Country out = null;
        while (out == null) {
            try {
                print(message);
                print("Nationality\n" + Arrays.toString(Country.values()));
                String message2 = scanner.nextLine().trim();
                if (message2.equals(""))
                    throw new Exception();
                if (message2 == null)
                    throw new Exception();
                out = Country.valueOf(message2);
            } catch (IllegalArgumentException e) {
                printError("That's not a country, please, use list of countries");
            } catch (Exception e) {
                printError("It's an empty string");
            }
        }
        return out;
    }

    public void changeScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
