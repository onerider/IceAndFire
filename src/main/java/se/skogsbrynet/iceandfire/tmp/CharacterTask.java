package se.skogsbrynet.iceandfire.tmp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import se.skogsbrynet.iceandfire.model.Character;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class CharacterTask implements Callable<List<Character>> {

    private final int page;
    private final String nameToFind;

    public CharacterTask(int page, String nameToFind) {
        this.page = page;
        this.nameToFind = nameToFind;
    }


    @Override
    public List<Character> call() {
        HttpEntity entity = HttpEntityFactory.getDefaultHttpEntity();
        ResponseEntity<Character[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange("https://anapioficeandfire.com/api/characters?page=" + page + "&pageSize=50", HttpMethod.GET, entity, Character[].class);
        Character[] characters = responseEntity.getBody();

        List<Character> charactersResult = new ArrayList<Character>();

        for (Character character : characters) {
            if (characterIsFound(character)) {
                charactersResult.add(character);
            }
        }
        return charactersResult;
    }

    private boolean characterIsFound(Character character) {
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