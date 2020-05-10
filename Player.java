import java.util.Stack;
import java.util.ArrayList;
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
    private ArrayList<Item> mochila;
    private int pesoItemsMochila;
    private int pesoMaxMochila;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room start, int pesoMaxMochila) {
        currentRoom = start;
        room = new Stack<>();
        mochila = new ArrayList<>();
        pesoItemsMochila = 0;
        this.pesoMaxMochila = pesoMaxMochila;
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

    /**
     * Comando take. Coge un objeto de la sala y lo guarda en la mochila.
     */
    public void take(Command command) {
        if(currentRoom.getItems().size() != 0) {
            int cont = 0;
            ArrayList<Item> itemsSala = currentRoom.getItems();
            boolean itemCogido = false;
            while(!itemCogido && cont < itemsSala.size()) {
                if(itemsSala.size() == 0) {
                    System.out.println("No hay objetos en la sala");
                }
                else if(itemsSala.get(cont).getId().equals(command.getSecondWord()) && itemsSala.get(cont).getItemPuedeLlevar()
                && pesoItemsMochila + itemsSala.get(cont).getWeight() <= pesoMaxMochila) {
                    mochila.add(itemsSala.get(cont));
                    itemCogido = true;
                    pesoItemsMochila = pesoItemsMochila + itemsSala.get(cont).getWeight();
                    System.out.println("Coges " + itemsSala.get(cont).getId() + " y lo metes en la mochila");
                    currentRoom.eliminarItems(command.getSecondWord());
                }
                else if(!itemsSala.get(cont).getItemPuedeLlevar() && itemsSala.get(cont).getId().equals(command.getSecondWord())) {
                    System.out.println("No tienes permiso para llevar este objeto.");
                    itemCogido = true;
                }
                else if(!itemCogido && pesoItemsMochila + itemsSala.get(cont).getWeight() > pesoMaxMochila) {
                    System.out.println("No tienes tanta fuerza muchacho, no cargues con más.");
                    itemCogido = true;
                }
                cont ++;
            }
        }
        else{
            System.out.println("No se encuentra el objeto.");
        }
    }

    /**
     * Comando drop. Suelta el objeto que tengas en la mochila y lo deja en la sala en la que estés.
     */
    public void drop(Command command) {
        int cont = 0;
        boolean itemTirado = false;
        while(!itemTirado && cont < mochila.size()) {
            if(mochila.get(cont).getId().equals(command.getSecondWord())) {
                currentRoom.addItem(mochila.get(cont).getItemDescription(), mochila.get(cont).getWeight(), mochila.get(cont).getId(), mochila.get(cont).getItemPuedeLlevar());
                System.out.println("Has tirado " + mochila.get(cont).getId() + " de la mochila.");
                pesoItemsMochila -= mochila.get(cont).getWeight();
                mochila.remove(cont);
                itemTirado = true;
            }
            cont ++;
        }
        if(!itemTirado) {
            System.out.println("No tienes " + command.getSecondWord() + " y por tanto no lo puedes tirar.");
        }
    }

    /**
     * Comando snif. Coge un objeto de la sala y lo guarda en la mochila.
     */
    public void snif(Command command) {
        int cont = 0;
        boolean polvitosTomados = false;
        while(!polvitosTomados && cont < mochila.size()) {
            if(mochila.get(cont).getId().equals(command.getSecondWord()) && mochila.get(cont).getId().equals("polvitos")) {
                polvitosTomados = true;
                pesoItemsMochila = pesoItemsMochila - mochila.get(cont).getWeight();
                mochila.remove(cont);
                pesoMaxMochila = pesoMaxMochila + 2;
                System.out.println("Mientras estabas grogui tomandote los polvos, te han cosido un par de bolsillos y te dan un bonus de +2. Ahora tu mochila puede llevar en total: " 
                    + pesoMaxMochila);
            }
            cont ++;
        }
        if(!polvitosTomados) {
            System.out.println("No tienes " + command.getSecondWord() + " y por tanto no te los puedes tomar.");
        }
    }

    /**
     * Funcionalidad items
     */
    public void items() {
        if(mochila.size() != 0) {
            int pesoTotal = 0;
            for (Item item : mochila) {
                System.out.println(item.description());
                pesoTotal = pesoTotal + item.getWeight();
            }
            System.out.println("Llevas un peso total en la mochila de: " + pesoTotal);
        }
        else {
            System.out.println("La mochila está vacía.");
        }
    }

    /**
     * ----------------------------
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
}