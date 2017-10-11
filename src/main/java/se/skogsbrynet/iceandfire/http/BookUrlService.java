package se.skogsbrynet.iceandfire.http;

public class BookUrlService extends UrlService {

    @Override
    String getUrl() {
        return "https://anapioficeandfire.com/api/books?page=1&pageSize=50";
    }
}
