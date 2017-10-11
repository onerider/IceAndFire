package se.skogsbrynet.iceandfire.http;

public class BookUrlService extends UrlService {

    @Override
    String getUrlToFirstPage() {
        return "https://anapioficeandfire.com/api/books?page=1&pageSize=50";
    }

    @Override
    public String getUrl() {
        return "https://anapioficeandfire.com/api/books?page=";
    }
}
