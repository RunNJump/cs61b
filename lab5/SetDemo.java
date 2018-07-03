import java.util.HashSet;
import java.util.Set;

/**
 * @author Hong Seok Jang
 */

public class SetDemo {
    public static void main(String[] args) {
        Set<String> s = new HashSet<String>();
        s.add("papa");
        s.add("bear");
        s.add("mama");
        s.add("bear");
        s.add("baby");
        s.add("bear");
        for (String temp :s) {
            System.out.println(temp);
        }
    }
}
