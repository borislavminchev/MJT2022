package bg.sofia.uni.fmi.mjt.news.retriever;

import bg.sofia.uni.fmi.mjt.news.response.NewsEntity;
import bg.sofia.uni.fmi.mjt.news.uribuilder.UriBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public interface NewsRetriever {
    UriBuilder where();
    NewsRetriever then() throws IOException, InterruptedException, URISyntaxException;
    Collection<NewsEntity> retrieveTopN(int n);
    Collection<NewsEntity> retrieveTopN(int n, String filePathToSave);
}
