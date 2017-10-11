package se.skogsbrynet.iceandfire.service;

import java.util.List;
import java.util.concurrent.Callable;
import se.skogsbrynet.iceandfire.model.Entity;

public abstract class EntitySearchTask implements Callable<List<Entity>> {
    final int page;
    final String nameToFind;

    EntitySearchTask(int page, String nameToFind) {
        this.page = page;
        this.nameToFind = nameToFind;
    }


    abstract public List<Entity> call();

    boolean isFound(Entity entity) {
        String[] namesToFind = nameToFind.split(" ");
        String[] namesOfEntity = entity.getName().split(" ");

        if(namesToFind.length > namesOfEntity.length) {
            return false;
        }

        for(int i=0; i < namesToFind.length; i++) {
            if(!namesToFind[i].equals(namesOfEntity[i])) {
                return false;
            }
        }

        return true;
    }

}
