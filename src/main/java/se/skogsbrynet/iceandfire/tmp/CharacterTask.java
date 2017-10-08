package se.skogsbrynet.iceandfire.tmp;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import se.skogsbrynet.iceandfire.model.Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CharacterTask implements Runnable {
    private static final List<Character> charactersResult = Collections.synchronizedList(new ArrayList<Character>());
    private final int page;
    private final String nameToFind;

    public CharacterTask(int page, String nameToFind) {
        this.page = page;
        this.nameToFind = nameToFind;
    }

    public static List<Character> getCharactersResult() {
        return charactersResult;
    }

    @Override
    public void run() {
        HttpEntity entity = SpringRestFactory.getHttpEntity();
        ResponseEntity<Character[]> responseEntity = SpringRestFactory.getRestTemplate().exchange("https://anapioficeandfire.com/api/characters?page=" + page + "&pageSize=50", HttpMethod.GET, entity, Character[].class);
        Character[] characters = responseEntity.getBody();
        for (Character character : characters) {
            if (characterIsFound(character)) {
                addCharacterToResultList(character);
            }
        }
    }

    private void addCharacterToResultList(Character character) {
        synchronized (charactersResult) {
            charactersResult.add(character);
        }
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