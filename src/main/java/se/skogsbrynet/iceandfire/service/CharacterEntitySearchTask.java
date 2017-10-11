package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import se.skogsbrynet.iceandfire.http.CharacterUrlService;
import se.skogsbrynet.iceandfire.http.HttpEntityFactory;
import se.skogsbrynet.iceandfire.http.RestTemplateFactory;
import se.skogsbrynet.iceandfire.model.Character;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.ArrayList;
import java.util.List;


/**
 * Creates a task for each page that is to be searched.
 */
class CharacterEntitySearchTask extends EntitySearchTask {

     CharacterEntitySearchTask(int page, String nameToFind) {
        super(page, nameToFind);
    }


    @Override
    public List<Entity> call() {
        HttpEntity entity = HttpEntityFactory.getDefaultHttpEntity();
        ResponseEntity<Character[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange(new CharacterUrlService().getUrl() + page + "&pageSize=50", HttpMethod.GET, entity, Character[].class);
        Entity[] characters = responseEntity.getBody();

        List<Entity> charactersResult = new ArrayList<>();

        for (Entity character : characters) {
            if (isFound(character)) {
                charactersResult.add(character);
            }
        }

        return charactersResult;
    }
}