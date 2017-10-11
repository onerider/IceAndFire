package se.skogsbrynet.iceandfire.service;


import java.util.List;
import java.util.concurrent.Callable;
import se.skogsbrynet.iceandfire.model.Entity;

public abstract class SearchTask implements Callable<List<Entity>> {
    final int page;
    final String nameToFind;

    SearchTask(int page, String nameToFind) {
        this.page = page;
        this.nameToFind = nameToFind;
    }

    @Override
    public abstract List<Entity> call();

    abstract boolean isFound(Entity entity);
}
