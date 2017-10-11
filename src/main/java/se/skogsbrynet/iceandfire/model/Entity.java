package se.skogsbrynet.iceandfire.model;

import se.skogsbrynet.iceandfire.service.UrlService;

/**
 * An entity is an object which will persist.
 */
 public abstract class Entity {
    // assume name is unique identifier for an entity
    private String name;
    private UrlService urlService;

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return name != null ? name.equals(entity.name) : entity.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrlService(UrlService urlService) {
        this.urlService = urlService;
    }

    public String getName() {
        return name;
    }

    public UrlService getUrlService() {
        return urlService;
    }
}
