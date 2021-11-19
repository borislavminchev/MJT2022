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
        System.out.println(isMountainArray(new int[]{3,3,3,3}));
    }
}