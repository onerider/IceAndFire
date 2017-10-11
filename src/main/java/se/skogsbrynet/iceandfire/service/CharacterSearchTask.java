package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import se.skogsbrynet.iceandfire.model.Character;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.ArrayList;
import java.util.List;


/**
 * Creates a task for each page that is to be searched.
 */
class CharacterSearchTask extends SearchTask {

     CharacterSearchTask(int page, String nameToFind) {
        super(page, nameToFind);
    }


    //@Override
    public List<Entity> call() {
        HttpEntity entity = HttpEntityFactory.getDefaultHttpEntity();
        ResponseEntity<Character[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange("https://anapioficeandfire.com/api/characters?page=" + page + "&pageSize=50", HttpMethod.GET, entity, Character[].class);
        Character[] characters = responseEntity.getBody();

        List<Entity> charactersResult = new ArrayList<>();

        for (Character character : characters) {
            if (isFound(character)) {
                charactersResult.add(character);
            }
        }

        return charactersResult;
    }

    boolean isFound(Entity character) {
        String[] namesToFind = nameToFind.split(" ");
        String[] namesOfCharacter = character.getName().split(" ");

        if(namesToFind.length > namesOfCharacter.length) {
            return false;
        }

        for(int i=0; i < namesToFind.length; i++) {
            if(!namesToFind[i].equals(namesOfCharacter[i])) {
                return false;
            }
        }

        return true;
    }
}