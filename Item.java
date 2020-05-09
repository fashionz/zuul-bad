/**
 * Clase que se encarga de crear los items para las salas.
 */

public class Item
{
    private String itemDescription;
    private int itemWeight;
    private String id;
    private boolean itemPuedeLlevar;

    /**
     * Constructor clase item.
     */
    public Item(String itemDescription, int itemWeight, String id, boolean itemPuedeLlevar)
    {
        this.itemDescription = itemDescription;
        this.itemWeight = itemWeight;
        this.id = id;
        this.itemPuedeLlevar = itemPuedeLlevar;
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
    
    public boolean getItemPuedeLlevar() {
        return itemPuedeLlevar;
    }
}