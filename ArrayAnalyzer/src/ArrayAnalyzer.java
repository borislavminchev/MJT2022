import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayAnalyzer {

    public static boolean isMountainArray(int[] array) {
        boolean incline = true;

        if(array.length < 3) {
            return false;
        }

        for (int i = 0; i < array.length - 1; i++) {
            if (incline && array[i + 1] < array[i]){
                incline = false;
            }else if (!incline && array[i] <= array[i + 1]){
                return false;
            } else if (array[i] == array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(Stream.of(1,2,3,4,5,6).reduce(Collectors.toList());
    }
}
