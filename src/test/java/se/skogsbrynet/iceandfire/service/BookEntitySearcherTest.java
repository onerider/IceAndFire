package se.skogsbrynet.iceandfire.service;

import org.junit.Test;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.List;

import static org.junit.Assert.*;

public class BookEntitySearcherTest {
    @Test
    public void search() throws Exception {
        EntitySearcher searcher = new BookEntitySearcher();
        List<Entity> result = searcher.performSearch("A Game of Thrones");

        assertEquals("A Game of Thrones", result.get(0).getName());
    }
}