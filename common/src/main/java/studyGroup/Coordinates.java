package studyGroup;

import java.io.Serializable;

/**
 * Координаты
 */
public class Coordinates implements Serializable {

    private static final long serialVersionUID = 7895807501343981770L;

    private final Long x; //Поле не может быть null
    private final double y; //Значение поля должно быть больше -352

    /**
     * Конструктор класса Coordinates
     * @param x X координата. Не может быть null
     * @param y Y координата. Должна быть больше -352
     */
    public Coordinates(Long x, double y) {
        this.x = x;
        this.y = y;
    }
    /**
     * @return X координата. Не может быть null
     * @throws IllegalArgumentException если поле не может быть null
     */
    public Long getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
