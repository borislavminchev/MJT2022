package bg.sofia.uni.fmi.mjt.news;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private static final String API_KEY = "8d1153d6a324436e8690e4ed521d3716";

    public static void main(String[] args) throws Exception {
//        HttpClient client = HttpClient.newBuilder().build();
//        URI uri = new URI("https", "newsapi.org", "/v2/top-headlines",
//                "country=us&apiKey=" + API_KEY, null);
//        HttpResponse<String> str = client.send(HttpRequest.newBuilder(uri).build(), HttpResponse.BodyHandlers.ofString());
//
//        Gson gson = new Gson();
//        Response map = gson.fromJson(str.body(), Response.class);
//        System.out.println(map.getArticles().get(1));

        String s = "ae,ar,at,au,be,bg,br,ca,ch,cn,co,cu,cz,de,eg,fr,gb,gr,hk,hu,id,ie,il,in,it,jp,kr,lt,lv,ma,mx,my,ng,nl,no,nz,ph,pl,pt,ro,rs,ru,sa,se,sg,si,sk,th,tr,tw,ua,us,ve,za";
        String res = Stream.of(s.split(",")).map(i -> i.toUpperCase() + "(\"" + i + "\")").collect(Collectors.joining(","));
        System.out.println(res);
    }
}

