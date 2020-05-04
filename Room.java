import java.util.HashMap;
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
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    private String description;
    private HashMap<String, Room> hmap;

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
        Room devuelveSalida = null;
        if(salida.equals("north")) {
            devuelveSalida = hmap.get("north");
        }
        if(salida.equals("east")) {
            devuelveSalida = hmap.get("east");
        }
        if(salida.equals("south")) {
            devuelveSalida = hmap.get("south");
        }
        if(salida.equals("west")) {
            devuelveSalida = hmap.get("west");
        }
        if(salida.equals("southEast")) {
            devuelveSalida = hmap.get("southEast");
        }
        return devuelveSalida;
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west" o "Exits: south" 
     * o "Exits: " si no hay salidas disponibles
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString() {
        String devuelveSalida = "Exits: ";
        if(hmap.get("north") != null)
            devuelveSalida += "north ";
        if(hmap.get("east") != null)
            devuelveSalida += "east ";
        if(hmap.get("south") != null)
            devuelveSalida += "south ";
        if(hmap.get("west") != null)
            devuelveSalida += "west ";
        if(hmap.get("southEast") != null)
            devuelveSalida += "southEast";
        return devuelveSalida;
    }

    /**
     * Define una salida para esta sala
     * 
     * @param direccion La direccion de la salida (por ejemplo "north" o "southEast")
     * @param sala La sala que se encuentra en la direccion indicada
     */
    public void setExit(String direccion, Room sala) {
        if (direccion.equals("north")) {
            hmap.put("north", sala);
        }
        if (direccion.equals("east")) {
            hmap.put("east", sala);
        }
        if (direccion.equals("south")) {
            hmap.put("south", sala);
        }
        if (direccion.equals("west")) {
            hmap.put("west", sala);
        }
        if (direccion.equals("southEast")) {
            hmap.put("southEast", sala);
        }

    }
}