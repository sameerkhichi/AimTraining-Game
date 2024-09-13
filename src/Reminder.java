import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Reminder{ //reminder class which is essentially a java timer class
    //private local members used in the class
    final Timer timer;
    private int secondsLeft;
    //these two link this class to the super class by passing information through the constructor
    final GUI_main gui;
    final JLabel timeleftlabel;

    //constructor of the reminder class, receiving arguments from gui main
    //these parameters store the information required that is related to the time aspect of the game
    public Reminder(int seconds, GUI_main gui, JLabel timeleftlabel){
        //essentially initializing the variables using member variables and values from the arguments in gui main class
        secondsLeft=seconds;
        this.gui = gui;
        this.timeleftlabel = timeleftlabel;
        timer = new Timer(); //creating a new instance of a timer
        timer.schedule(new RemindTask(), 0,1000); //making it so that the timer iterates every second
    }

    class RemindTask extends TimerTask { //nested class to run the timer and what happens when the timer finishes
        public void run() { //runs a void statement
            secondsLeft--; //decreases the variable storing the time left
            if(secondsLeft>=0){ //while the timer is running
                timeleftlabel.setText("Time left: " + secondsLeft + " seconds"); //updates the label showing how much time is left
            }
            else{ //when the timer is finished
                timer.cancel(); //cancels the timer so there is no overlapping
                gui.gameoverSequence();//runs the gameover sequence from the gui main class
            }
        }
    }
}
