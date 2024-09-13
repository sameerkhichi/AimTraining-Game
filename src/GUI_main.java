import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.Random;

public class GUI_main implements MouseListener { //the mouse listener class is implemented in order to track how many times the user clicks their mouse

    //private local members that are used in the game code
    DecimalFormat twoplace = new DecimalFormat("0.00");
    private int score = 0;
    private int clickcount;
    private double accuracy = 0;
    final JLabel scorelabel;
    final JLabel timeleftlabel;
    final JFrame frame;
    final JPanel buttonpannel;
    final JPanel toppannel;
    final JButton[][] buttons; //2D array holding the targets in rows and columns
    final Random rb = new Random();
    final Reminder reminder;

    public GUI_main() { //constructor of the object gui main which is essentially the game GUI
        //creating, formatting and conducting basic set up of the frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(frame.getMostRecentFocusOwner());
        frame.setTitle("Aim Trainer");
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setVisible(true);

        //creating and formatting the label that will keep track of the users score and their accuracy
        scorelabel = new JLabel("Score: " + score + " || Accuracy: " + twoplace.format(accuracy) + "%");
        scorelabel.setHorizontalAlignment(SwingConstants.RIGHT);
        scorelabel.setFont(scorelabel.getFont().deriveFont(25.0f));
        scorelabel.setForeground(Color.WHITE);

        //creating and formatting the label that will keep track of the amount of time left
        timeleftlabel = new JLabel("Time left: ");
        timeleftlabel.setHorizontalAlignment(SwingConstants.LEFT);
        timeleftlabel.setFont(timeleftlabel.getFont().deriveFont(25.0f));
        timeleftlabel.setForeground(Color.WHITE);

        //this object is of the reminder class, which is essentially the timer
        //its perameters entail the amount of time the user has for the game, the gui for accessing this classe' methods and access to the time label to update it
        reminder = new Reminder(30, this, timeleftlabel);

        //creating and formatting a panel that will hold the information of the score, accuracy and time left
        toppannel = new JPanel(new GridLayout(1, 2));
        toppannel.setBackground(Color.BLACK);
        toppannel.add(timeleftlabel);
        toppannel.add(scorelabel);

        //creating a button panel which is organized as a grid which will hold all possible buttons/targets
        buttonpannel = new JPanel(new GridLayout(25, 45)); //25 rows and 45 columns of buttons for best target size
        buttonpannel.setBackground(Color.BLACK);

        //Gridbagconstraints object is an object that helps format and organize grids and panels kept in "gridlayout"
        GridBagConstraints buttongrid = new GridBagConstraints();
        buttongrid.fill = GridBagConstraints.BOTH; //the button grid will fill the x and y directions
        //this ensures that all cells in the grid are the exact same size
        buttongrid.weightx = 1.0;
        buttongrid.weighty = 1.0;

        buttons = new JButton[25][45]; //initializing the 2D array holding the buttons in rows and columns

        //this nested loop creates buttons of equal size and formatting and adds it to the 2D array until it is full
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[row].length; col++) {
                //formatting the buttons
                buttons[row][col] = new JButton();
                buttons[row][col].setForeground(Color.RED);
                buttons[row][col].setBackground(Color.RED);
                buttons[row][col].setVisible(false); //making sure all the buttons/targets dont appear on startup
                buttongrid.gridx = col; //the x location on the grid of the button is associated with the index on the 2D array the button is given
                buttongrid.gridy = row; //the y location on the grid of the button is associated with the index on the 2D array the button is given
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));//adding an action listener to every button in the 2D array
                buttonpannel.add(buttons[row][col], buttongrid); //adding all the buttons as they are created to the button panel grid
            }
        }

        //adding the top panel and the button panel grid to the frame completing the frame setup process
        frame.add(toppannel, BorderLayout.NORTH);
        frame.add(buttonpannel, BorderLayout.CENTER);
        frame.addMouseListener(this);
        targetVisibility(); //essentailly starts the game by showing one of the buttons in the array at random

    }
    public class ButtonClickListener implements ActionListener { //nested class which is used to check when the target is hit
        //local members for the row and columns of the buttons
        int row;
        int col;

        public ButtonClickListener(int row, int col){ //the perameters are fed from the gui main class telling this method where the button that was pressed is
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) { //these actions are performed when the target is hit
            score++; //updates score
            clickcount++; //increases total amount of clicks
            accuracy = (((double) score/clickcount)*100);
            scorelabel.setText("Score: "+score+" || Accuracy: "+twoplace.format(accuracy)+"%"); //updates label with score and accuracy
            buttons[row][col].setVisible(false); //hides the button that was pressed
            targetVisibility(); //looping the process of showing one random button at a time
        }
    }

    public void targetVisibility(){ //the method that chooses a random button/target and shows it
        //these two variables are the row and column values of the 2D array
        //the two variables are randomly generated bounds within the range of the length of both dimensions of the array
        int row = rb.nextInt(buttons.length);
        int col = rb.nextInt(buttons[row].length);

        for(int c=0; c< buttons.length; c++){ //nested loop to continue to show one random button at a time
            for(int nc=0; nc<buttons[c].length; nc++){
                buttons[c][nc].setVisible(c==row && nc==col);
            }
        }
    }
    //method to increase the total amount of clicks when the user clicks anywhere in the frame
    //this method is run by the mouse listener method.
    public void increaseclickcount(){
        clickcount++;
        accuracy = (((double) score/clickcount)*100);
        scorelabel.setText("Score: "+score+" || Accuracy: "+twoplace.format(accuracy)+"%"); //updating the label for accuracy
    }

    public int getScore(){ //getter for the users score
        return score;
    }

    public double getAccuracy(){ //getter for the users accuracy
        return accuracy;
    }

    public void gameoverSequence(){ //when the time runs out, this method is called
        new EndingScreen(getScore(), getAccuracy()); //running the ending screen window by calling the object, while feeding arguments of the score and accuracy
        frame.dispose(); //closing the game window
    }


    @Override
    public void mouseClicked(MouseEvent e) { //mouse listener class method that tracked each time the mouse is clicked
        increaseclickcount(); //calling the method that handles the games behaviour for when the mouse is clicked
    }

    //the rest of these methods are used by the mouse listener class
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    //main class for the games gui
    public static void main(String[] args){
        //this try and catch statement is used to format buttons themselves seperate from the platform settings.
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        new GUI_main(); //this instance is of the gui main object which is the games code and gui
    }
}
