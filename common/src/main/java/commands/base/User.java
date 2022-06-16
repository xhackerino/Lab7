package commands.base;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = -707103404349L;

    private final String name;
    private final String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}