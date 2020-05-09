import java.util.Stack;
/**
 * Clase Player perteneciente al juego World of Zuul.
 * 
 * @author Roberto Álvarez 
 * @version x09-05-20x
 */
public class Player
{
    private Room currentRoom;
    private Stack<Room> room;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room start) {
        currentRoom = start;
        room = new Stack<>();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            room.push(currentRoom);
            currentRoom = nextRoom;
            look();
        }
    }

    /**
     * Muestra la sala actual en la que te encuentras
     */
    public void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Comes
     */
    public void eat() {
        System.out.println("acabas de comer y ya no tienes hambre");
    }

    /**
     * Regresas por donde has venido.
     */
    public void back() {
        if(!room.isEmpty()) {
            currentRoom = room.pop();
            look();
        }
        else {
            System.out.println("No se puede retroceder más.");
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }
}