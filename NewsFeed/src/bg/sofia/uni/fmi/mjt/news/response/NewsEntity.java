package bg.sofia.uni.fmi.mjt.news.response;

import java.net.URL;

public class NewsEntity {
    private final Source source;
    private final String author;
    private final String title;
    private final String description;
    private final URL url;
    private final URL urlToImage;
    private final String publishedAt;
    private final String content;

    public NewsEntity(Source source, String author, String title, String description,
                      URL url, URL urlToImage, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public Source getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public URL getUrl() {
        return url;
    }

    public URL getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }
}
