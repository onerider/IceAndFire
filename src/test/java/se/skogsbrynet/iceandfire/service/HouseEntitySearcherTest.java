package se.skogsbrynet.iceandfire.service;

import org.junit.Test;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.List;

import static org.junit.Assert.*;

@SuppressWarnings("SpellCheckingInspection")
public class HouseEntitySearcherTest {
    @Test
    public void search() throws Exception {
        EntitySearcher searcher = new HouseEntitySearcher();
        List<Entity> result = searcher.performSearch("House Algood");

        assertEquals("House Algood", result.get(0).getName());

    }

}