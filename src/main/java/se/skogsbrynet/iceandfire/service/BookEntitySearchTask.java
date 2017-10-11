package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import se.skogsbrynet.iceandfire.http.BookUrlService;
import se.skogsbrynet.iceandfire.http.HttpEntityFactory;
import se.skogsbrynet.iceandfire.http.RestTemplateFactory;
import se.skogsbrynet.iceandfire.model.Book;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.ArrayList;
import java.util.List;


/**
 * Creates a task for each page that is to be searched.
 */
class BookEntitySearchTask extends EntitySearchTask {

     BookEntitySearchTask(int page, String nameToFind) {
        super(page, nameToFind);
    }


    @Override
    public List<Entity> call() {
        HttpEntity entity = HttpEntityFactory.getDefaultHttpEntity();
        ResponseEntity<Book[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange(new BookUrlService().getUrl() + page + "&pageSize=50", HttpMethod.GET, entity, Book[].class);
        Entity[] books = responseEntity.getBody();

        List<Entity> booksResult = new ArrayList<>();

        for (Entity book : books) {
            if (isFound(book)) {
                booksResult.add(book);
            }
        }

        return booksResult;
    }
}