//importing all the required classes
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuScreen {
    //local members used in the main menu screen
    final JFrame frame;
    final JLabel titleLabel;
    final JButton startButton;
    final JButton exitButton;
    final JPanel optionPanel;

    public MainMenuScreen() { //Constructor for the object of main menu gui screen
        //creating and conducting basic setup and cleanup for the frame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(frame.getMostRecentFocusOwner());
        frame.setTitle("Main Menu");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setSize(300, 200);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        //Creating the main label introducing the title of the game
        titleLabel = new JLabel("Aim Trainer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 100));
        titleLabel.setForeground(Color.RED);
        frame.add(titleLabel, BorderLayout.NORTH);

        //creating a panel that will hold buttons with the choices the user can interact with.
        optionPanel = new JPanel(new GridLayout(2,1 ));
        optionPanel.setBackground(Color.BLACK);

        //creating and formatting the start game button
        startButton = new JButton("Start Game");
        startButton.setBackground(Color.BLACK);
        startButton.setForeground(Color.RED);
        startButton.setFont(startButton.getFont().deriveFont(75.0f));
        startButton.addActionListener(new ActionListener() { //this is an anonymous action listener, used for simplicity
            @Override
            public void actionPerformed(ActionEvent e) { //these functions are carried out when this button is pressed
                frame.dispose(); // Close the main menu window
                new GUI_main(); // Start the game
            }
        });

        //creating and formatting the exit game button
        exitButton = new JButton("Exit Game");
        exitButton.setBackground(Color.BLACK);
        exitButton.setForeground(Color.RED);
        exitButton.setFont(exitButton.getFont().deriveFont(75.0f));
        exitButton.addActionListener(new ActionListener() {//anonymous action listener
            @Override
            public void actionPerformed(ActionEvent e) { ////these functions are carried out when this button is pressed
                System.exit(0);
            }
        });

        //adding the two buttons to the option panel
        optionPanel.add(startButton);
        optionPanel.add(exitButton);
        //adding the option panel to the frame and formatting it to the center
        frame.add(optionPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    public static void main(String[] args) { //main method used to run the application.
        //this try and catch statement is to allow the buttons themselves to be formatted a different colour from the platform settings
        try {
            //java's UI manager, this line changes the platforms customization "look and feel"
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        new MainMenuScreen(); //creating a new object/gui for the main menu

    }
}