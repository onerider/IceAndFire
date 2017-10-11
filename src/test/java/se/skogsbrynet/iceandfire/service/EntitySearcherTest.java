package se.skogsbrynet.iceandfire.service;

import org.junit.Test;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpellCheckingInspection")
public class EntitySearcherTest {
    @Test
    public void testPerformSearch() throws Exception {
        EntitySearcher searcher = new EntitySearcher();
        List<Entity> result = searcher.performSearch("Petyr Baelish");

        assertEquals("Success. Found one record.", "Petyr Baelish", result.get(0).getName());
    }

}