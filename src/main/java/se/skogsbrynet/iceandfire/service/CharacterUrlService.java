package se.skogsbrynet.iceandfire.service;

class CharacterUrlService extends UrlService {

    @Override
    String getUrl() {
        return "https://anapioficeandfire.com/api/characters?page=1&pageSize=50";
    }

    @Override
    Class<Character[]> getResponseType() {
        return Character[].class;
    }

}
