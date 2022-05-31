package com.application.focus.console;

import com.focus.ExpCounter;
import com.focus.focusTimer.FocusTimer;
import com.focus.focusTimer.Timer;
import com.focus.userModel.State;
import com.focus.userModel.lists.WorkContainer;
import com.focus.userModel.lists.WorkItem;
import com.focus.userModel.lists.materials.Action;
import com.focus.userModel.lists.materials.Item;
import com.focus.userModel.lists.materials.Reading;
import com.focus.userModel.lists.materials.Video;
import com.focus.userModel.lists.workContainers.Epic;
import com.focus.userModel.lists.workContainers.List;
import com.focus.userModel.lists.workContainers.Project;
import com.focus.userModel.lists.workContainers.Task;
import com.focus.userModel.techTree.TechNode;
import exceptions.ExitThrown;
import exceptions.InvalidListNameException;
import exceptions.InvalidTechNameException;
import exceptions.invalidListTypeException;

import java.util.Locale;
import java.util.Scanner;

import static com.focus.ExpCounter.getExpCounterInstance;
import static java.util.Objects.isNull;


//TODO add closing messages to the methods
// something is wrong with the throw exit exception, i think we need to change it
// There is a lot of stuff left to implement.....
public class ConsoleApplication {
    private State state;
    private Scanner input;
    private FocusTimer focusTimer;
    private ExpCounter expCounter;
    //private SpotifyIntegration spotifyIntegration;
    private static final String message = "Done";

    public ConsoleApplication() {
        runConsoleApplication();
    }

    private void runConsoleApplication() {
        boolean forward = true;
        String command = null;

        System.out.println("Welcome to the Focus task tracker");

        try {
            init();
            System.out.println("Type help for a list of commands.");

            while (forward) {
                command = input.next();

                command = command.toLowerCase(Locale.ROOT);
                if (command.equals("quit")) {
                    System.exit(0);
                } else {
                    processCommands(command);
                }
            }

        } catch (Exception e) {
            System.exit(1);
        }

    }

    private void init() {
        this.state = new State();
        this.input = new Scanner(System.in);
        this.focusTimer = new FocusTimer();
        this.expCounter = getExpCounterInstance();
        //this.spotifyIntegration = new SpotifyIntegration();

        input.useDelimiter("\n");
    }

    //TODO implement all of these bois
    private void processCommands(String command) {
        switch (command) {
            case "help":
                displayMenu();
                break;
            case "createlist":
                createNewList();
                break;
            case "additem":
                addItemToList();
                break;
            case "dellist":
            case "remtitem":
                removeItemFromList();//Doubles as the delete
                break;
            case "complete":
                completeItem();
                break;
            case "displist":
                displayList();
                break;
//            case "edititem"://Just setters.... I dont want to right now
//                editListItem();
                //break;
            case"createreward":
                createReward();//Parent and child are both nullable
                break;
            case"remreward":
                removeReward();
                break;
            case"buyreward":
                buyReward();
                break;
            case"dispreward":
                displayReward();
                break;
//            case"editreward":
//                editReward();
                //break;
            case"getexp":
                getExpCurrentAmount();
                break;
//            case"dispitemdet":
//                displayItemDetails();
                //break;
            case"addFocusItem":
                addFocusItem();
                break;
            case"remfocusitem":
                removeFocusItem();
                break;
            case"settimegoal":
                setFocusTimerDailyGoal();
                break;
            case"settimerlength":
                setTimerLength();
                break;
            case"setbreaklength":
                setBreakLength();
                break;
//            case"setnumbreaks":
//                setNumberOfBreaks();
                //break;
//            case"gettimerlength":
//                getCurrentTimerLength();
                //break;
//            case"getcurrentfocustime":
//                getCurrentTotalFocusTime();
                //break;
            case"start":
                startTimer();
                break;
            case"dispfocuslist":
                displayFocusList();
                break;
//            case"setfocusexpmod":
//                setFocusTimerExpMultiplier();
                //break;
//            case"getnumfocses":
//                getNumberOfFocusSessionsCompleted();
                //break;
        }
    }

    private void displayFocusList() {
        System.out.println("Focus session list: \n");
        this.focusTimer.getFocusList().forEach(workItem -> workItem.printWorkItem());                                              ;
    }

    private void startTimer() {
        this.focusTimer.generateTimers_NotTimeStrict();
        this.focusTimer.startFocusTimer();
        System.out.println("Timer started");
        for (Timer timer : this.focusTimer.getTimers()) {
            timer.startTimer();
            this.focusTimer.setRunning(true);
            this.focusTimer.setCurrentTimer(timer);
            timer.run();
            while (!timer.isComplete()) {
                //wait and listen for pause etc
                String command = input.next();
                processTimerCommands(command);
                if (!this.focusTimer.isRunning() && !this.focusTimer.getCurrentTimer().getId().equals(Timer.TimerId.BREAK)) {
                    focusTimer.addTimeFocused();
                    this.focusTimer.getCurrentTimer().pauseTimer();
                    break;
                } else if (!this.focusTimer.isRunning()) {
                    this.focusTimer.getCurrentTimer().pauseTimer();
                    break;
                }
            }
            if (!this.focusTimer.getCurrentTimer().getId().equals(Timer.TimerId.BREAK)) {
                this.focusTimer.addTimeFocused();
            }
        }
        this.focusTimer.setRunning(false);
        this.focusTimer.setFocusTimerSessionsUsed(this.focusTimer.getFocusTimerSessionsUsed() + 1);
    }

    //TODO Add the complete focus timer methods in here
    private void processTimerCommands(String command) {
        switch (command.toLowerCase(Locale.ROOT)) {
            case "pause":
                this.focusTimer.pauseFocusTimer();
                break;
            case "completeitem":
            case "endbreak":
            default:
                processCommands(command);
        }
    }

    private void setBreakLength() {
        int breakLength = getMinutes();
        System.out.println("Your break length is now set to " + breakLength + " minutes.");
        breakLength = minutesToMiliseconds(breakLength);
        this.focusTimer.setFocusTimerBreakLength(breakLength);
    }

    //IDEAS this could create a problem since the action is not done before the output
    private void setTimerLength() {
        int timerLength = getMinutes();
        System.out.println("Your timer length is now " + timerLength + "Minutes");
        timerLength = minutesToMiliseconds(timerLength);
        this.focusTimer.setFocusTimerTotalLength(timerLength);
    }

    private void setFocusTimerDailyGoal() {
        int focusGoal = getMinutes();
        System.out.println("Your focus goal is now " + focusGoal + "Minutes");
        focusGoal = minutesToMiliseconds(focusGoal);
        this.focusTimer.setFocusTimerGoalDaily(focusGoal);
    }

    private int getMinutes() {
        System.out.println("Enter the number of minutes you would like to focus per day:");
        int focusGoal = input.nextInt();
        return focusGoal;
    }

    private int minutesToMiliseconds(int minutes) {
        return minutes * 60 * 1000;
    }

    //These two methods should have an if statement where you can strictly place one and not children or remove
    //EFFECTS: adds the searched for item and all its children (if applicable) to the focus timer
    private void addFocusItem() {
        WorkItem addition = findWorkItem();
        this.focusTimer.addGoal(addition);
        if (addition instanceof WorkContainer) {
            for (WorkItem child : ((WorkContainer) addition).getChildren()) {
                this.focusTimer.addGoal(child);
            }
        }
    }

    private void removeFocusItem() {
        WorkItem addition = findWorkItem();
        this.focusTimer.removeGoal(addition);
    }

    private void getExpCurrentAmount() {
        System.out.println("Current Exp: " + getExpCounterInstance().getExpCount());
    }

    private void displayReward() {
        TechNode node = getTechNodeBySearch();
        node.printReward();
    }

    private void buyReward() {
        TechNode node = getTechNodeBySearch();
        System.out.println("How much exp would you like to contribute to the reward:");
        int expAmount = input.nextInt();
        node.contribute(expAmount);
    }

    private void removeReward() {
        TechNode node = getTechNodeBySearch();
        this.state.getTechTree().removeNode(node);
    }

    private TechNode getTechNodeBySearch() {
        System.out.println("Please type the name of the reward that you would like to perform your action on:");
        String name = input.next();
        TechNode node = null;

        try {
            node = this.state.getTechTree().searchTechNode(name);
        } catch (InvalidTechNameException e) {
            System.out.println("That name was not valid, you might want to retry.");
        }
        return node;
    }

    private void createReward() {
        System.out.println("Please type the name of the reward you want to add:");
        String reward = input.next();

        System.out.println("Now the description of the reward, which you can leave blank if you'd like");
        String description = input.next();

        System.out.println("Please enter the amount of exp required to purchase the reward (integer only):");
        int expRequirement = input.nextInt();

        this.state.getTechTree().addNode(expRequirement,reward,description);
    }

    //TODO add some method that completes the thing if it's progress is at 100
    private void completeItem() {
        WorkItem toComplete = findWorkItem();
        toComplete.complete();
    }

    //TODO this probably needs to be implemented elsewhere so as to avoid extreme casting issues
    private WorkItem findWorkItem() {
        WorkItem addingList = null;
        while (isNull(addingList)) {

            System.out.println("Please type the Title of the list to which you would like to perform your action:");
            String listTitle = input.next();
            try {
                addingList = this.state.searchWorkItems(listTitle);
            } catch (InvalidListNameException e) {
                try {
                    System.out.println("That list does not exist, if you would like to display all the lists in the current state" + "\n" +
                            "type d, if you would like to try again, type t, if you would like to exit, type e " + "\n" +
                            "(if you type anything else, we'll assume you want to try again");
                    String nextAction = input.next();
                    if (nextAction.equals("d")) {
                        displayListOfLists();
                    } else if (nextAction.equals("e")) {
                        throw new ExitThrown();
                    } else {
                        //pass
                    }
                    System.out.println("Please try again, and this time correctly :)");
                } catch (ExitThrown exitThrown) {
                    displayMenu();
                    break;
                }
            }
        }
        return addingList;

    }

    private void removeItemFromList() {
        WorkItem toRemove = findWorkContainer();
        this.state.removeTaskList(toRemove);
    }

    private void addItemToList() {
        WorkContainer addingList = findWorkContainer();
        System.out.println("Please type the type of item you would like to add to the list (List or Material): \n" +
                "(Note that at this point, a list is any of List, Epic, Project, or Task");
        String workItemType = input.next().toLowerCase(Locale.ROOT);
        if (workItemType.equals("list")) {
            createNewListWithParent();
        } else {
            createNewMaterialWithParent();
        }
    }

    private void createNewMaterialWithParent() {
        String listName = getUserInputTitle();

        int expValue = getExpValue();

        String description = getDescription();

        String materialType = getMaterialTypeAsString();

        WorkContainer parent = findWorkContainer();

        materialCreator(listName, expValue, description, materialType, parent);
    }

    private void materialCreator(String listName, int expValue, String description, String materialType, WorkContainer parent) {
        switch (materialType) {
            case"action":
                this.state.addTaskList(new Action(listName,expValue,0,description,parent));
            case"item":
                this.state.addTaskList(new Item(listName,expValue,0,description,parent));
            case"reading":
                readingCreator(listName,expValue,description,parent);
            case"video":
                videoCreator(listName,expValue,description,parent);
        }
    }

    private void videoCreator(String name, int expValue, String description, WorkContainer parent) {
        System.out.println("Copy the file path to the video if you have one, if not leave this blank");
        String filePath = input.next();

        System.out.println("How many hours long is the video? (don't worry, input your minutes next");
        int hours = input.nextInt();

        System.out.println("How many minutes more (or if under an hour, how many minutes)");
        int minutes = input.nextInt();

        this.state.addTaskList(new Video(name,expValue,filePath,0,hours,minutes,description,parent));
    }

    private void readingCreator(String name, int expValue, String description,WorkContainer parent) {
        System.out.println("How many pages (estimate is okay, hek, use thi s for chapters if you want) is the reading:");
        int pages = input.nextInt();

        System.out.println("Copy the file path to the reading if you have one, if not leave this blank");
        String filePath = input.next();

        this.state.addTaskList(new Reading(name,expValue,0,description,parent,filePath,pages));

        completionOfTaskMessage();
    }

    private void completionOfTaskMessage() {
        System.out.println(message);
    }

    private String getMaterialTypeAsString() {
        System.out.println("Next we need the material type, which is one of Action, Item, Reading or Video" + "\n" +
                "====================================== Definitions =====================================================================" + "\n" +
                "Action ---> Something to do, like going for a walk or committing a crime etc." + "\n" +
                "Item -----> Your standard grocery list item, or something to find/get/buy etc." + "\n" +
                "Reading --> A passage to read from one of your many books (or a school assigned reading, ya know) (You can attach the reading)" + "\n" +
                "Video ----> A video to watch (You can attach the video file or put the link in the material description)" + "\n" +
                "** it should be noted that these definitions are free to user interpretation and that all of these 'materials' can contain \n" +
                "other types of materials at your whim **");
        String listType = input.next().toLowerCase(Locale.ROOT);
        return listType;
    }

    private WorkContainer findWorkContainer() {
        WorkContainer addingList = null;
        while (isNull(addingList)) {

            System.out.println("Please type the Title of the list to which you would like to perform your action:");
            String listTitle = input.next();
            try {
                addingList = (WorkContainer) this.state.searchWorkItems(listTitle); //This is a bad cast
            } catch (InvalidListNameException e) {
                try {
                    System.out.println("That list does not exist, if you would like to display all the lists in the current state" + "\n" +
                            "type d, if you would like to try again, type t, if you would like to exit, type e " + "\n" +
                            "(if you type anything else, we'll assume you want to try again");
                    String nextAction = input.next();
                    if (nextAction.equals("d")) {
                        displayListOfLists();
                    } else if (nextAction.equals("e")) {
                        throw new ExitThrown();
                    } else {
                        //pass
                    }
                    System.out.println("Please try again, and this time correctly :)");
                } catch (ExitThrown exitThrown) {
                    displayMenu();
                    break;
                }
            }
        }
        return addingList;

    }

    private void displayListOfLists() {
        for (WorkItem taskList : this.state.getTaskLists()) {
            if (taskList.getDepth() == 0) {
                taskList.printWorkItem();
            }
        }
    }

    private void displayList() {
        boolean proceed = false;
        WorkContainer workContainer = findWorkContainer();

        workContainer.printWorkItem();
    }

    private void createNewList() {
        String listName = getUserInputTitle();

        int expValue = getExpValue();

        String description = getDescription();


        String listType = getListTypeAsString();

        listCreator(listName, expValue, description, listType);
    }

    private void createNewListWithParent() {
        System.out.println("This is exactly the same as the creation wizard for a list without a parent, but we will ask" + "\n" +
                "for an extra list of some type which we will add your item to !");

        String listName = getUserInputTitle();

        int expValue = getExpValue();

        String description = getDescription();

        String listType = getListTypeAsString();

        WorkContainer parent = findWorkContainer();

        listCreator(listName, expValue, description, listType, parent);
    }

    private String getListTypeAsString() {
        System.out.println("Next we need the list type, which is one of List, Epic, Project or Task" + "\n" +
                "====================================== Definitions =====================================================================" + "\n" +
                "List -----> a collection of other items that need to be done, bought, etc. (maybe a grocery list, or a todo list)" + "\n" +
                "Epic -----> a large project, consisting of many sub-projects, such as building an app, a game, a document, etc." + "\n" +
                "Project --> an item that requires tasks to be completed or a larger element of work to be done" + "\n" +
                "Task -----> an item of work to be done" + "\n" +
                "** it should be noted that these definitions are free to user interpretation and that all of these lists can contain \n" +
                "other types of lists at your whim **");
        String listType = input.next().toLowerCase(Locale.ROOT);
        return listType;
    }

    private String getDescription() {
        System.out.println("Description of the list (or task) at hand:");
        String description = input.next();
        return description;
    }

    private int getExpValue() {
        System.out.println("Enter the exp reward for completing all elements of the list (integer values only):" + "\n" +
                "(Note that this does not count the exp reward of each item on the list" +
                "if left blank, then the default value is 100)");
        int expValue = input.nextInt();
        return expValue;
    }

    private String getUserInputTitle() {
        System.out.println("Please type your List's name:");
        String listName = input.next();
        return listName;
    }

    private void listCreator(String listName, int expValue, String description, String listType,WorkContainer parent) {
        boolean forward = true;
        while (forward) {
            if ("list".equals(listType)) {
                listConstructionWizard(listName, expValue, description,parent);
                forward = false;
            } else {
                try {
                    workContainerConstructionWizard(listName, expValue, description, listType,parent);
                    forward = false;
                } catch (invalidListTypeException e) {
                    System.out.println("sorry, you entered, please enter a valid type (List,Epic,Project,Task):");
                    listType = input.next();
                }
            }
        }
    }

    private void listCreator(String listName, int expValue, String description, String listType) {
        boolean forward = true;
        while (forward) {
            if ("list".equals(listType)) {
                listConstructionWizard(listName, expValue, description);
                forward = false;
            } else {
                try {
                    workContainerConstructionWizard(listName, expValue, description, listType);
                    forward = false;
                } catch (invalidListTypeException e) {
                    System.out.println("sorry, you entered, please enter a valid type (List,Epic,Project,Task):");
                    listType = input.next();
                }
            }
        }
    }

    private void workContainerConstructionWizard(String listName, int expValue, String description, String listType) throws invalidListTypeException {
        switch (listType) {
            case "epic":
                addEpic(listName,expValue,description);
            case"project":
                addProject(listName,expValue,description);
            case "task":
                addTask(listName,expValue,description);
            default:
                throw new invalidListTypeException();
        }
    }

    private void workContainerConstructionWizard(String listName, int expValue, String description, String listType,WorkContainer parent) throws invalidListTypeException {
        switch (listType) {
            case "epic":
                addEpic(listName,expValue,description,parent);
            case"project":
                addProject(listName,expValue,description,parent);
            case "task":
                addTask(listName,expValue,description,parent);
            default:
                throw new invalidListTypeException();
        }
    }

    private void listConstructionWizard(String listName, int expValue, String description) {
        System.out.println("Is the list sequential? (does each element in your list require that its precedent be completed first?)");
        boolean sequential = input.nextBoolean();

        addList(listName, expValue, description, sequential);
    }

    private void listConstructionWizard(String listName, int expValue, String description,WorkContainer parent) {
        System.out.println("Is the list sequential? (does each element in your list require that its precedent be completed first?)");
        boolean sequential = input.nextBoolean();

        addList(listName, expValue, description, sequential,parent);
    }

    private void addList(String listName, int expValue, String description, boolean sequential) {
        this.state.addTaskList(new List(listName, expValue, sequential, description));
    }

    private void addList(String listName, int expValue, String description, boolean sequential,WorkContainer parent) {
        this.state.addTaskList(new List(listName, expValue, sequential, description,parent));
    }

    private void addEpic(String listName, int expValue, String description) {
        this.state.addTaskList(new Epic(listName, expValue,description));
    }

    private void addEpic(String listName, int expValue, String description,WorkContainer parent) {
        this.state.addTaskList(new Epic(listName, expValue,description,parent));
    }

    private void addProject(String listName, int expValue, String description) {
        this.state.addTaskList(new Project(listName, expValue, description));
    }

    private void addProject(String listName, int expValue, String description,WorkContainer parent) {
        this.state.addTaskList(new Project(listName, expValue, description,parent));
    }

    private void addTask(String listName, int expValue, String description) {
        this.state.addTaskList(new Task(listName, expValue, description));
    }

    private void addTask(String listName, int expValue, String description,WorkContainer parent) {
        this.state.addTaskList(new Task(listName, expValue, description,parent));
    }


    // could add customisation options
    private void displayMenu() {
        System.out.println("Focus Home Menu:" + "\n" +
                "Commands:" + "\n" +
                "========================== List Commands ====================" + "\n" +
                "**Note that all searches are done by Item titles**" + "\n" +
                "Create a new list --------------------> createList" + "\n" +
                "Add an item to a list ----------------> addItem" + "\n" +
                "Remove an item -----------------------> remItem" + "\n" +
                "Complete an item ---------------------> complete" + "\n" +
                "Display a list -----------------------> dispList" + "\n" +
                "Edit item ----------------------------> editItem" + "\n" +
                "Delete a list ------------------------> delList" + "\n" +
                "Display item details -----------------> dispItemDet" + "\n" +
                "========================= Reward Commands ===================" + "\n" +
                "Make a new reward --------------------> createReward" + "\n" +
                "Remove a reward ----------------------> remReward" + "\n" +
                "Buy a reward -------------------------> buyReward" + "\n" +
                "Display reward tree ------------------> dispReward" + "\n" +
                "Edit reward --------------------------> editReward" + "\n" +
                "Get current Exp Count ----------------> getExp" + "\n" +
                "======================== Focus Timer Commands ===============" + "\n" +
                "Add item to your Focus List ----------> addFocusItem" + "\n" +
                "Remove an item from your Focus List --> remFocusItem" + "\n" +
                "Set focus time daily goal ------------> setTimeGoal" + "\n" +
                "Set Focus Timer Length ---------------> setTimerLength" + "\n" +
                "Set break length ---------------------> setBreakLength" + "\n" +
                "Set number of breaks -----------------> setNumBreaks" + "\n" +
                "Get the current timer length ---------> getTimerLength" + "\n" +
                "Get Focus time for this session ------> getCurrentFocusTime" + "\n" +
                "Start the timer ----------------------> start" + "\n" +
                "Pause the timer ----------------------> pause (in focus loop)" + "\n" +
                "Stop the timer (Reset) ---------------> stop  (in focus loop)" + "\n" +
                "Complete Focus Item ------------------> completeFocusItem (in focus loop)" + "\n" +
                "End break early ----------------------> endBreak (in focus loop)" + "\n" +
                "Display Focus List -------------------> dispFocusList" + "\n" +
                "Set Focus exp modifier ---------------> setFocusExpMod" + "\n" +
                "Get number of Focus Sessions ---------> getNumFocSes" + "\n" +
                "========================= Spotify Commands ===================" + "\n" +
                "COMING SOON"
                );
    }
}
