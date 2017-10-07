package se.skogsbrynet.iceandfire.model;

/**
 * Class for holding Character-attributes, mapping to
 * the IceAndFire JSON-object Character
 */
class Character {

    private String name;
    private String url;

    String getName() {
        return name;
    }

    @SuppressWarnings("unused")
    void setName(String name) {
        this.name = name;
    }

    String getUrl() {
        return url;
    }

    @SuppressWarnings("unused")
    void setUrl(String url) {
        this.url = url;
    }

}