package bg.sofia.uni.fmi.mjt.boardgames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Main {
    public static final int LIMIT = 3;

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>(List.of("a", "b", "c", "d", "e"));
        List<String> list2 = new ArrayList<>(List.of("a", "b", "c"));
        List<String>  common = list1.stream().filter(list2::contains).collect(Collectors.toList());
        List<String>  common2 = list2.stream().filter(list1::contains).collect(Collectors.toList());

        System.out.println(common);
        System.out.println(common2);
    }
}
