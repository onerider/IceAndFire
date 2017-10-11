package se.skogsbrynet.iceandfire.http;

public class CharacterUrlService extends UrlService {

    @Override
    String getUrl() {
        return "https://anapioficeandfire.com/api/characters?page=1&pageSize=50";
    }
}
