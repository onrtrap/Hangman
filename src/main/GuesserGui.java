import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class GuesserGui {

    String playerName;
    JLabel text;
    JFrame frame;
    JTextField guessField = new JTextField(1);
    JButton guess = new JButton("Guess");
    String whatIs;
    boolean pressed;
    boolean game;
    boolean playing;
    boolean correctGuess;
    int part;
    String answer;
    ArrayList<String> incomplete;
    String incompleteString;
    String wGuess;
    int increment;
    Dictionary wordGuess;
    public GuesserGui(String title) {
        game = true;
        playing = true;
        pressed = false;
        boolean won;
        increment = 0;
        String wGuess;
        incomplete = new ArrayList<String>();
        incompleteString = "";
        part = 1;
        wordGuess = new Dictionary();
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        HangmanGui hang = new HangmanGui("Hangman");
        hang.addImage("hangman0.jpg");
        text = new JLabel();
        whatIs = "";
       JPanel panelTop = new JPanel();
       panelTop.setPreferredSize(new Dimension(300, 100));
       JPanel panelBottom = new JPanel();

       guess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //System.out.println(returnGuess());
                wordCheck();

                pressed = true;
            }
        });

       panelTop.add(text);
       panelBottom.add(guessField);
       panelBottom.add(guess);

       frame.getContentPane().add(BorderLayout.NORTH, panelTop);
       frame.getContentPane().add(BorderLayout.SOUTH, panelBottom);

     playerName = JOptionPane.showInputDialog("What's your name?");
        hang.setVisible();
        this.setVisible();
    while(game) {
        part = 1;
        won = false;
        pressed = false;
        answer = wordGuess.getWord();
        wGuess = "";
        incomplete.clear();
        System.out.println(answer);
        playing = true;
        //playing = false;
        incompleteString = "";
        pressed = false;
        System.out.println(wGuess);
        //System.out.println(answer);
        for (int i = 0; i < answer.length(); i++) {
            //wGuess += "_";
            incomplete.add("_");
            incompleteString += "_";
        }
        while(playing) {
            setText(incompleteString);
            correctGuess = false;
            pressed = false;
            while (!pressed) {
                increment += 1;
               returnGuess();
            }
            if(!correctGuess) {
                part++;
                switch (part) {

                    case 2:
                        hang.addImage("hangman1.jpg");
                        System.out.println("2");
                        break;
                    case 3:
                        hang.addImage("hangman2.jpg");
                        System.out.println("3");
                        break;
                    case 4:
                        hang.addImage("hangman3.jpg");
                        System.out.println("4");
                        break;
                    case 5:
                        hang.addImage("hangman4.jpg");
                        System.out.println("5");
                        break;
                    case 6:
                        hang.addImage("hangman5.jpg");
                        System.out.println("6");
                        break;
                    case 7:
                        hang.addImage("hangman6.jpg");
                        System.out.println("7");
                        playing = false;
                        break;
                }
            }
            if(isSame()) {
                System.out.println("You Win");
                playing = false;
                won = true;
            }

        }
        int n;
        JOptionPane gameOver = new JOptionPane();
        if(won)
        {
            try {
                File file = new File("src/resources/Stats.txt");
               addHighScore(file);
                System.out.println("Successfully wrote to the file.");
            }catch(IOException e){
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            n = JOptionPane.showConfirmDialog(gameOver, "Play Again?", "You Win! Game Over.", JOptionPane.YES_NO_OPTION);
        }
        else {
            n = JOptionPane.showConfirmDialog(gameOver, "The correct answer was \n" + answer + ". Play Again? ", "You Lose. Game Over.",
                    JOptionPane.YES_NO_OPTION);
        }
        if (n == 1) {
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }

    }
    }

    public void setVisible(){
        frame.pack();
        frame.setVisible(true);
    }

    public void setText(String textT){
        text.setText(textT);
        frame.revalidate();
    }
    public void returnGuess(){
        wGuess = guessField.getText();
    }

    public String getPlayerName(){
        return playerName;
    }

    public String getGuess(){
        return whatIs;
    }

    public boolean isSame(){
        return incompleteString.equals(answer);
    }
    public void wordCheck(){
        incompleteString = "";
        if(!(incomplete.contains(wGuess))) {
            for (int i = 0; i < answer.length(); i++) {

                if ((wGuess).equals(answer.substring(i, i + 1))) {

                    incomplete.set(i, wGuess);
                    correctGuess = true;
                }
            }

        }
        for (String s : incomplete) {
            incompleteString += s;
        }
        System.out.println(incompleteString);
        setText(incompleteString);
    }


    private void addHighScore(File file) throws IOException {

        // default - create and write
        // if file not exists, create and write
        // if file exists, truncate and write
			/*try (FileWriter fw = new FileWriter(file);
					 BufferedWriter bw = new BufferedWriter(fw)) {
					bw.write(content);
					bw.newLine();
			}*/

        // append mode
        // if file not exists, create and write
        // if file exists, append to the end of the file

        ArrayList<String> lines = new ArrayList<String>();
        ArrayList<String> newScore = new ArrayList<String>();
        ArrayList<Player> players = new ArrayList<Player>();

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(getPlayerName() + "-Score: " + ((7 - part) + answer.length() * 5));
            bw.newLine();   // add new line, System.lineSeparator();
        }
        try{
            lines = new ArrayList<>(Files.readAllLines(Paths.get(file.getPath())));
        }
        catch(IOException e){
            System.out.println("An error occured while reading");
        }


        lines.remove(0);

        for(int i = 0; i < lines.size(); i++)
        {
            if(!(lines.get(i).isEmpty())) {
                players.add(Player.parsePlayer(lines.get(i)));
            }
        }

        newScore.add("Leaderboard");

        Player.playerSort(players);

        for(int j = players.size() - 1; j >= 0; j--)
        {
            newScore.add(players.get(j).giveScore());
            System.out.println(j);
        }

        new FileWriter(file, false).close();

        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for(int k = 0; k < newScore.size(); k++) {
                bw.write(newScore.get(k));
                bw.newLine();   // add new line, System.lineSeparator();
            }
        }

    }

    }