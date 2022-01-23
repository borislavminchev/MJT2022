package bg.sofia.uni.fmi.mjt.news;

import bg.sofia.uni.fmi.mjt.news.response.NewsEntity;
import bg.sofia.uni.fmi.mjt.news.retriever.StandardNewsRetriever;
import bg.sofia.uni.fmi.mjt.news.uribuilder.CountryCode;
import bg.sofia.uni.fmi.mjt.news.uribuilder.UriBuilder;

import java.util.Collection;

public class Main {
    private static final int MAGIC = 2;
    private static final int MAGIC2 = 38;

    public static void main(String[] args) throws Exception {
        final int l = 10;
        Collection<NewsEntity> entities = StandardNewsRetriever.createDefault().where()
                .keywords("trump", null, "biden")
                .country(CountryCode.US)
                .retrieve()
                .then()
                .retrieveTopN(l, "file.txt");
        System.out.println(entities.size());
    }
}

