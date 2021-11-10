public class PathUtils {

    public static String getCanonicalPath(String path) {

        if (path.isEmpty()) {
            return "/";
        }

        String[] dirs = path.split("[/]+");
        if (dirs.length == 0) {
            return "/";
        }

        String[] canonicalPath = new String[dirs.length];
        //canonicalPath[0] = "/";
        int currentDirIndex = 0;

        if (!dirs[0].equals(".") && !dirs[0].isEmpty()) {
            return "";
        }

        for (int i = 1; i < dirs.length; i++) {
            if (dirs[i].length() == 2 && dirs[i].equals("..")) {
                if (currentDirIndex != 0) {
                    //canonicalPath[currentDirIndex] = "";
                    currentDirIndex--;
                }
            } else if (!dirs[i].equals(".")) {
                currentDirIndex++;
                canonicalPath[currentDirIndex] = dirs[i];
            }
        }

        String result = "/";
        for (int i = 1; i < currentDirIndex; i++) {
            result += canonicalPath[i] + "/";
        }
        if (currentDirIndex != 0) {
            result += canonicalPath[currentDirIndex];
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(getCanonicalPath("//"));
    }
}
