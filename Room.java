import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> hmap;
    private ArrayList<Item> listaItem;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        hmap = new HashMap<>();
        listaItem = new ArrayList<>();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Devuelve la sala vecina a la actual que esta ubicada en la direccion indicada como parametro.
     *
     * @param salida Un String indicando la direccion por la que saldriamos de la sala actual
     * @return La sala ubicada en la direccion especificada o null si no hay ninguna salida en esa direccion
     */
    public Room getExit(String salida) {
        return hmap.get(salida);
    }

    /**
     * Devuelve la informaci�n de las salidas existentes
     * Por ejemplo: "Exits: north east west" o "Exits: south" 
     * o "Exits: " si no hay salidas disponibles
     *
     * @return Una descripci�n de las salidas existentes.
     */
    public String getExitString() {
        Set <String> puntosCardinales = hmap.keySet();
        String devuelveSalida = "Exits: ";
        for (String direction : puntosCardinales) {
            devuelveSalida = devuelveSalida + direction + " ";
        }
        return devuelveSalida;
    }

    /**
     * Define una salida para esta sala
     * 
     * @param direccion La direccion de la salida (por ejemplo "north" o "southEast")
     * @param sala La sala que se encuentra en la direccion indicada
     */
    public void setExit(String direccion, Room sala) {
        hmap.put(direccion, sala);
    }

    /**
     * Devuelve un texto con la descripcion completa de la habitacion, que 
     * incluye la descripcion corta de la sala y las salidas de la misma. Por ejemplo:
     *     You are in the lab
     *     Exits: north west southwest
     * @return Una descripcion completa de la habitacion incluyendo sus salidas
     */
    public String getLongDescription() {
        String descHab = "Est�s en " + getDescription() + "\n" + getExitString();
        if(listaItem.size() > 0) {
            descHab = descHab + "Esta sala tiene los siguientes objetos: ";
            for (Item itemDescription : listaItem) {
                descHab = descHab + itemDescription.description() + " ";
            }
        }
        return descHab;
    }

    /**
     * Agrega un objeto con el nombre y su respectivo peso.
     */
    public void addItem(String itemDescription, int itemWeight) {
        Item item = new Item(itemDescription, itemWeight);
        listaItem.add(item);
    }
}