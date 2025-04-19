package Model;

import java.util.List;

public abstract class Person {
    protected String name;
    protected String username;
    protected String email;
    protected String password;
    protected String phone;
    protected String address;

    public Person(){
    }

    public Person(String name, String username, String email, String password, String phone, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public abstract void signup(String username, String email, String password);
    public abstract boolean signIn(String username, String password);
    public abstract void logout();
    public abstract List<Product> getProducts();
    public abstract Product getProductById(int id);

}
