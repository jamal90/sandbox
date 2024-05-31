import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class IteratorUtil {

    public static <T> List<T> stripToEmtpy(List<T> inputCollection) {
        return inputCollection == null ? Collections.emptyList() : inputCollection;
    }
    
    public static <T> Set<T> stripToEmtpy(Set<T> set) {
        return set == null ? Collections.emptySet() : set; 
    }
    
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c");
        Set<String> set = new HashSet<>();
        
        set.add("a");
        set.add("b");
        set.add("c");
        set.add("d");

        System.out.println("LIST");
        for (String s: stripToEmtpy(list)){
            System.out.println(s);
        }

        System.out.println("SET");
        for (String s: stripToEmtpy(set)){
            System.out.println(s);
        }
    }
    
    
    
    
}
