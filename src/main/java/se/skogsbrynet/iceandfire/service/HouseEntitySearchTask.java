package se.skogsbrynet.iceandfire.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import se.skogsbrynet.iceandfire.http.HouseUrlService;
import se.skogsbrynet.iceandfire.http.HttpEntityFactory;
import se.skogsbrynet.iceandfire.http.RestTemplateFactory;
import se.skogsbrynet.iceandfire.model.House;
import se.skogsbrynet.iceandfire.model.Entity;

import java.util.ArrayList;
import java.util.List;


/**
 * Creates a task for each page that is to be searched.
 */
class HouseEntitySearchTask extends EntitySearchTask {

     HouseEntitySearchTask(int page, String nameToFind) {
        super(page, nameToFind);
    }


    @Override
    public List<Entity> call() {
        HttpEntity entity = HttpEntityFactory.getDefaultHttpEntity();
        ResponseEntity<House[]> responseEntity = RestTemplateFactory.getRestTemplate().exchange(new HouseUrlService().getUrl() + page + "&pageSize=50", HttpMethod.GET, entity, House[].class);
        Entity[] houses = responseEntity.getBody();

        List<Entity> housesResult = new ArrayList<>();

        for (Entity house : houses) {
            if (isFound(house)) {
                housesResult.add(house);
            }
        }

        return housesResult;
    }
}