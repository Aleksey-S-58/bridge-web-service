package web.service.config;

import java.util.Objects;

public class User {
    private String name;
    private String password;
    private String role;

    public User() {

    }

    public User(String name, String password, String role) {
        super();
        this.name = name;
        this.password = password;
        this.role = role;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, password, role);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        return Objects.equals(name, other.name) && Objects.equals(password, other.password)
                && Objects.equals(role, other.role);
    }
    @Override
    public String toString() {
        return "User [name=" + name + ", password=" + password + ", role=" + role + "]";
    }

}
