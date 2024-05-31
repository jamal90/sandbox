/**
 * Created by I076097 on 8/18/2016.
 */
public class Person {
    private final String firstName;
    private final String lastname;
    private final int age;

    public Person(String firstName, String lastname, int age) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public int getAge() {
        return age;
    }
}
