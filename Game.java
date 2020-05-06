/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room salaPrincipal, salaTigre, salaAlien, salaChucky, salaTaberna, salaPantera, salaPub, salaPozo, salaGhetto, salaFinal;

        // create the rooms
        salaPrincipal = new Room("Entrada de la Escape Room!", "", 0);
        salaTigre = new Room("Cuidado con el tigre, huye mientras puedas!", "", 0);
        salaAlien = new Room("Cuidado con el ET de las narices, tienes una pistola de rayos y no dudará en disparar, huye!!!", "", 0);
        salaChucky = new Room("HUYE, te has encontrado con Chucky!", "", 0);
        salaTaberna = new Room("Pasa y tómate una buena birra. Debes estar cansado después de huir del tigre y del alien.", "cerveza", 1);
        salaPantera = new Room("Miau, una pantera anda suelta.", "", 0);
        salaPub = new Room("Quieto quieto, traaaanquilooo. Quédate un ratito y échate unos bailes antes de seguir.", "cubata", 1);
        salaPozo = new Room("Si caes en el pozo vuelves a la salaPrincipal y empiezas de nuevo!", "", 0);
        salaGhetto = new Room("Vete si no quieres caer en el mundo de la droga.", "", 0);
        salaFinal = new Room("Salida de la Escape Room!", "premio", 1);

        // initialise room exits
        //arriba[N], derecha[E], abajo[S], izquierda[W], abajo-derecha[SE]

        //salaPrincipal
        salaPrincipal.setExit("north",salaAlien);
        salaPrincipal.setExit("east",salaTigre);
        salaPrincipal.setExit("west",salaTaberna);
        salaPrincipal.setExit("northEast",salaChucky);

        //salaTigre
        salaTigre.setExit("west",salaPrincipal);

        //salaAlien
        salaAlien.setExit("south",salaPrincipal);

        //salaChucky
        salaChucky.setExit("south",salaPrincipal);

        //salaTaberna
        salaTaberna.setExit("north",salaPub);
        salaTaberna.setExit("east",salaPrincipal);
        salaTaberna.setExit("northWest",salaPantera);

        //salaPantera
        salaPantera.setExit("south",salaTaberna);

        //salaPub
        salaPub.setExit("north",salaGhetto);
        salaPub.setExit("east",salaFinal);
        salaPub.setExit("south",salaTaberna);
        salaPub.setExit("southEast",salaPozo);

        //salaPozo
        salaPozo.setExit("west",salaPrincipal);

        //salaGhetto
        salaPub.setExit("south",salaPub);

        //salaFinal
        salaPub.setExit("west",salaPub);

        currentRoom = salaPrincipal;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat(); 
        }

        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
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
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }

    /**
     * Imprime la descripción de la localización actual.
     */
    private void printLocationInfo(){
        System.out.println(currentRoom.getLongDescription());
        System.out.println();
    }

    /**
     * Muestra la sala actual en la que te encuentras
     */
    private void look() {
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Muestra la sala actual en la que te encuentras
     */
    private void eat() {
        System.out.println("acabas de comer y ya no tienes hambre");
    }
}
