package dev.dgomes.footballNews.domain;

public class NewsData {

    private String title;
    private String text;
    private String image;
    private String url;

    public String getNewsTitle() {
        return title;
    }

    public void setNewsTitle(String newsTitle) {
        this.title = newsTitle;
    }

    public String getNewsText() {
        return text;
    }

    public void setNewsText(String newsText) {
        this.text = newsText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
