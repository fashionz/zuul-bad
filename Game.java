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
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player(createRooms());
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms() {
        Room salaPrincipal, salaTigre, salaAlien, salaChucky, salaTaberna, salaPantera, salaPub, salaPozo, salaGhetto, salaFinal;

        // CREACIÓN SALAS
        salaPrincipal = new Room("La entrada de la Escape Room!");
        salaTigre = new Room("La sala del tigre. Cuidado con él, huye mientras puedas!");
        salaAlien = new Room("La sala del alien. Cuidado con el ET de las narices, tienes una pistola de rayos y no dudará en disparar, huye!!!");
        salaChucky = new Room("La sala de chucky, HUYE!");
        salaTaberna = new Room("La taberna. Pasa y tómate una buena birra. Debes estar cansado después de huir del tigre y del alien.");
        salaPantera = new Room("La sala de la pantera. Miau, cuidado con el lindo gatito.");
        salaPub = new Room("El pub. Quieto quieto, traaaanquilooo. Quédate un ratito y échate unos bailes antes de seguir.");
        salaPozo = new Room("El pozo, has caido y vuelves a la salaPrincipal y empiezas de nuevo!");
        salaGhetto = new Room("El ghetto. Vete si no quieres caer en el mundo de la droga.");
        salaFinal = new Room("La salida de la Escape Room!");

        // AÑADIDO DE OBJETOS - ("objeto", peso)
        salaAlien.addItem("Arma poderosa del Alien", 4, "raygun", false);
        salaTaberna.addItem("Estrella Galicia", 2, "cerveza", true);
        salaTaberna.addItem("Zumo de Naranja", 3, "zumo", true);
        salaPub.addItem("Ron-Cola", 1, "cubata", true);
        salaPub.addItem("Daiquiri", 1, "cocktail", false);
        salaFinal.addItem("Trofeo que se da al finalizar el Escape Room", 5, "trofeo", true);

        // SALIDAS SALAS
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

        return salaPrincipal;  // start game outside
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
        player.look();
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
            player.goRoom(command);
        }
        else if(commandWord.equals("back")) {
            player.back();
        }
        else if (commandWord.equals("look")) {
            player.look();
        }
        else if (commandWord.equals("eat")) {
            player.eat(); 
        }
        else if (commandWord.equals("take")) {
            player.take(command);
        }
        else if (commandWord.equals("drop")) {
            player.drop(command);
        }
        else if (commandWord.equals("items")) {
            player.items();
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
}