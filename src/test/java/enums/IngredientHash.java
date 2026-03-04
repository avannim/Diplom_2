package enums;

public enum IngredientHash {

    //Fillings
    BEEF_MEAT("61c0c5a71d1f82001bdaaa70"),
    PROTOSTOMIA_MEAT("61c0c5a71d1f82001bdaaa6f"),
    MINI_SALAD("61c0c5a71d1f82001bdaaa79"),
    CHEESE("61c0c5a71d1f82001bdaaa7a"),
    MARCIAN_ALFASAHARID("61c0c5a71d1f82001bdaaa78"),
    MINERAL_CIRCLES("61c0c5a71d1f82001bdaaa76"),
    FALLENIAL_FRUIT("61c0c5a71d1f82001bdaaa77"),
    MAGNOLIA_MEAT("61c0c5a71d1f82001bdaaa71"),
    TETRADONTIMFORM_MEAT("61c0c5a71d1f82001bdaaa6e"),

    //Sauces
    SPACE_SAUCE("61c0c5a71d1f82001bdaaa73"),
    GREEK_SAUCE("61c0c5a71d1f82001bdaaa74"),
    ANTARION_SAUCE("61c0c5a71d1f82001bdaaa75"),
    SPICY_SAUCE("61c0c5a71d1f82001bdaaa72"),

    //Buns
    FLUORECENT_BUN("61c0c5a71d1f82001bdaaa6c"),
    CRATER_BUN("61c0c5a71d1f82001bdaaa6d");

    private final String ingredient;

    IngredientHash(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }
}
