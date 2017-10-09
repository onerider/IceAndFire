package se.skogsbrynet.iceandfire.service;

import org.junit.Test;
import se.skogsbrynet.iceandfire.model.Character;

import java.util.List;

import static org.junit.Assert.*;

public class CharacterSearcherTest {
    @Test
    public void testPerformSearch() throws Exception {
        CharacterSearcher searcher = new CharacterSearcher();
        List<Character> result = searcher.performSearch("Petyr Baelish");

        assertEquals("Success. Found one record.", "Petyr Baelish", result.get(0).getName());
    }

}