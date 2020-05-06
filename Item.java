/**
 * Clase que se encarga de crear los items para las salas.
 */
public class Item
{
    private String itemDescription;
    private int itemWeight;

    /**
     * Constructor clase item.
     */
    public Item(String itemDescription, int itemWeight)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
    }

    /**
     * Crea la descripción del item.
     */
    public String description(){
        return "En esta sala hay " + itemDescription + " y su peso es de " + itemWeight;
    }
}