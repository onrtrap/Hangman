import javax.swing.*;

public class HangmanGui {
    private final JFrame frame;

    public HangmanGui(String title){
        frame = new JFrame(title);
        //frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(500,600, 500, 800);
        JLabel hangmanPic = new JLabel();

    }

    public void addImage(String filepath){
        frame.getContentPane().removeAll();
        frame.repaint();

        ImageIcon icon = new ImageIcon(getClass().getResource(filepath));

        frame.add(new JLabel(icon));
        frame.revalidate();
    }

    public void setVisible(){
        frame.pack();
        frame.setVisible(true);
    }
}
