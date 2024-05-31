import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClosableTest {

    public static void main(String[] args) throws IOException {
        
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            br.lines().forEach(line -> {
                
                if (line.equals("EX")){
                    throw new RuntimeException("Exception thrown!");
                }
                System.out.println(line); 
            });
            
        }
        
    }
    
    private static BufferedReader getAutoclosable() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))){
            return br; 
        }
    }
    
}
