/**
 * Clase que se encarga de crear los items para las salas.
 */
public class Item
{
    private String itemDescription;
    private int itemWeight;
    private String id;

    /**
     * Constructor clase item.
     */
        public Item(String itemDescription, int itemWeight, String id)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
                this.id = id;
    }

    /**
     * Crea la descripción del item.
     */
    public String description(){
        return itemDescription + " y su peso es de " + itemWeight + "." + 
        " Su ID es: " + id + "." + " || ";
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getWeight() {
        return itemWeight;
    }

    public String getId() {
        return id;
    }
}