import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by I076097 on 8/18/2016.
 */
public class PlayGroundS1 {
    public  static void main(String [] args){
        Person p1 = new Person("xbd", "def", 10);
        Person p2 = new Person("df", "ads", 20);

        List<Person> persons = new ArrayList<>();
        persons.add(p1);
        persons.add(p2);

        Collections.sort(persons, new Comparator<Person>() {
            public int compare(Person o1, Person o2) {
                if (o1.getFirstName().compareTo(o2.getFirstName()) > 0)
                    return 1;
                else if (o1.getFirstName().compareTo(o2.getFirstName()) < 0)
                    return -1;
                else
                    return 0;
            }
        });

        for (Person p : persons){
            System.out.println(p.getFirstName());
        }


        final Person youngest = min(persons, new Comparator<Person>(){

            @Override
            public int compare(Person o1, Person o2) {
                if(o1.getAge() < o2.getAge())
                    return -1;
                else if (o1.getAge() > o2.getAge())
                    return 1;
                else return 0;
            }
        });


        System.out.println(youngest.getFirstName());
    }

    public static <T> T min(List<T> persons, Comparator<T> comparator){
        T youngPerson = persons.get(0);
        for (int i = 1; i < persons.size(); i++){
            if (comparator.compare(persons.get(i), youngPerson) < 0)
                youngPerson = persons.get(i);
        }
        return youngPerson;
    }

}
