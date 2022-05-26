package com.application.focus.console;

import com.focus.ExpCounter;
import com.focus.SpotifyIntegration;
import com.focus.focusTimer.FocusTimer;
import com.focus.userModel.State;

import java.util.Locale;
import java.util.Scanner;

public class ConsoleApplication {
    private State state;
    private Scanner input;
    private FocusTimer focusTimer;
    private ExpCounter expCounter;
    private SpotifyIntegration spotifyIntegration;

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
                    processCommands();
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
        this.expCounter = ExpCounter.getExpCounterInstance();
        this.spotifyIntegration = new SpotifyIntegration();

        input.useDelimiter("\n");
    }

    private void processCommands(String command) {
        switch (command) {
            case "h":
                displayMenu();
            case "createlist":
                createNewList();
            case "additem":
                addItemToList();
            case "remtitem":
                removeItemFromList();
            case "complete":
                completeItem();
            case "displist":
                displayList();
            case "edititem":
                editListItem();
            case "dellist":
                deleteList();
            case"createreward":
                createReward();//Parent and child are both nullable
            case"remreward":
                removeReward();
            case"buyreward":
                buyReward();
            case"dispreward":
                displayReward();
            case"editreward":
                editReward();
            case"getexp":
                getExpCurrentAmount();
            case"dispitemdet":
                displayItemDetails();
            case"addFocusItem":
                addFocusItem();
            case"remfocusitem":
                removeFocusItem();
            case"settimegoal":
                setFocusTimerDailyGoal();
            case"settimerlength":
                setTimerLength();
            case"setbreaklength":
                setBreakLength();
            case"setnumbreaks":
                setNumberOfBreaks();
            case"gettimerlength":
                getCurrentTimerLength();
            case"getcurrentfocustime":
                getCurrentTotalFocusTime();
            case"start":
                startTimer();
            case"pause":
                pauseTimer();
            case"stop":
                stopTimer();
            case"completefocusitem":
                completeFocusItem();
            case"endbreak":
                endCurrentBreak();
            case"dispfocuslist":
                displayFocusList();
            case"setfocusexpmod":
                setFocusTimerExpMultiplier();
            case"getnumfocses":
                getNumberOfFocusSessionsCompleted();
        }
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
                "Pause the timer ----------------------> pause" + "\n" +
                "Stop the timer (Reset) ---------------> stop" + "\n" +
                "Complete Focus Item ------------------> completeFocusItem" + "\n" +
                "End break early ----------------------> endBreak" + "\n" +
                "Display Focus List -------------------> dispFocusList" + "\n" +
                "Set Focus exp modifier ---------------> setFocusExpMod" + "\n" +
                "Get number of Focus Sessions ---------> getNumFocSes" + "\n" +
                "========================= Spotify Commands ===================" + "\n" +
                "COMING SOON"
                );
    }
}
