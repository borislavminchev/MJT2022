public class WeatherForecaster {
    public static int[] getsWarmerIn(int[] temperatures) {
        int[] results = new int[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            for (int j = 1; j < temperatures.length-i; j++) {
                if(temperatures[i] < temperatures[i+j]) {
                    results[i] = j;
                    break;
                } else if (temperatures[i] > temperatures[i+j] && j == temperatures.length-1) {
                    results[i] = 0;
                }
            }
        }

        return results;
    }
}
