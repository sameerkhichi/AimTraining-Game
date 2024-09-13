import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class EndingScreen {
    //local members used in the ending screen gui
    DecimalFormat twoplace = new DecimalFormat("0.00");
    final JFrame frame;
    final JPanel infoPanel;
    final JPanel optionPanel;
    final JLabel titleLabel;
    final JLabel scoreLabel;
    final JLabel accuracyLabel;
    final JButton quitGame;
    final JButton restart;
    final JButton mainMenu;

    //this contructors parameters are storing the users score and accuracy for the labels displaying the final results.
    public EndingScreen(int score, double accuracy){ //constructor for the object of the ending screen gui
        //creating and conducting basic setup and cleanup for the frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(frame.getMostRecentFocusOwner());
        frame.setTitle("Game Over");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        //creating a new panel to hold the information of the users score and accuracy
        infoPanel = new JPanel(new GridLayout(1, 2));
        infoPanel.setBackground(Color.BLACK);

        //creating the label that says game over at the top of the screen
        titleLabel = new JLabel("Game Over", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 100));
        titleLabel.setForeground(Color.RED);
        frame.add(titleLabel, BorderLayout.NORTH);

        //creating and formatting the label that shows the users score and adding it to the info panel
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        scoreLabel.setForeground(Color.WHITE);
        infoPanel.add(scoreLabel);

        //creating and formatting the label that shows the users accuracy and adding it to the info panel
        accuracyLabel = new JLabel("Accuracy: " + twoplace.format(accuracy) + "%");
        accuracyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accuracyLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        accuracyLabel.setForeground(Color.WHITE);
        infoPanel.add(accuracyLabel);

        //creating another panel that will hold the buttons the user can interact with
        optionPanel = new JPanel(new GridLayout(3,1));
        optionPanel.setBackground(Color.BLACK);

        //creating and formatting a button that will let the user quit the game
        quitGame = new JButton("Quit Game");
        quitGame.setBackground(Color.BLACK);
        quitGame.setForeground(Color.RED);
        quitGame.setFont(quitGame.getFont().deriveFont(75.0f));
        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //this event will take place when the button is pressed
                System.exit(0);
            }
        });

        //creating and formatting a button that will restart the game without having to close and re-open it
        restart = new JButton("Restart");
        restart.setBackground(Color.BLACK);
        restart.setForeground(Color.RED);
        restart.setFont(restart.getFont().deriveFont(75.0f));
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //these events will take place when the button is pressed
                frame.dispose(); //closing the ending game window
                new GUI_main(); //starting the game
            }
        });

        //creating and formatting a button that will let the user go back to the main menu without having to close and re-opening
        mainMenu = new JButton("Return to Main Menu");
        mainMenu.setBackground(Color.BLACK);
        mainMenu.setForeground(Color.RED);
        mainMenu.setFont(mainMenu.getFont().deriveFont(75.0f));
        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //these events will take place when the button is pressed
                frame.dispose(); //ending game window will close
                new MainMenuScreen(); //activating the main menu gui object opening that window
            }
        });

        //adding the three buttons to the option panel
        optionPanel.add(restart);
        optionPanel.add(mainMenu);
        optionPanel.add(quitGame);
        //adding the two panels to the frame and formatting them neatly
        frame.add(optionPanel, BorderLayout.SOUTH);
        frame.add(infoPanel, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    public static void main(String[] args){ //main method for the ending screen gui
        try{ //this try and catch statement is used to format the buttons themselves seperate from the platform settings
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        GUI_main gui = new GUI_main(); //creating an object of the gui main class which contains the code to the game itself
        //creating a new object of the ending screen gui with arguments from the games code which contain the user score and accuracy
        new EndingScreen(gui.getScore(), gui.getAccuracy());
    }

}
