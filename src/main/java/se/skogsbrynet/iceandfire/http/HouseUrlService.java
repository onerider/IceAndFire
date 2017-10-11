package se.skogsbrynet.iceandfire.http;

public class HouseUrlService extends UrlService {

    @Override
    String getUrlToFirstPage() {
        return "https://anapioficeandfire.com/api/houses?page=1&pageSize=50";
    }

    public String getUrl() {
        return "https://anapioficeandfire.com/api/houses?page=";
    }
}
