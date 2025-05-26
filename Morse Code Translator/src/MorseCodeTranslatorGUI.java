import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MorseCodeTranslatorGUI extends JFrame implements KeyListener {
    private MorseCodeController morseCodeController;


    private JTextArea textInputArea , morseCodeArea;

    public MorseCodeTranslatorGUI() {

        //add text to tile bar
        super("Morse Code Translaor");

        //size of frame
        setSize(new Dimension(540 , 760));

        setResizable(false);

        setLayout(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        getContentPane().setBackground(Color.decode("#626F47"));

        setLocationRelativeTo(null);

        morseCodeController = new MorseCodeController();

        addGuiComponents();

    }

    private void addGuiComponents() {
        JLabel titleLabel = new JLabel("Morse Code Translator");
        titleLabel.setFont(new Font("Dialog", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(0 , 0 , 540 , 100);


        JLabel textInputLabel = new JLabel("Text:");
        textInputLabel.setFont(new Font("Dialog" , Font.BOLD, 16));
        textInputLabel.setForeground(Color.WHITE);
        textInputLabel.setBounds(20 , 100 , 200, 30);

        textInputArea = new JTextArea();
        textInputArea.setFont(new Font("Dialog", Font.PLAIN, 18));

        textInputArea.addKeyListener(this);

        textInputArea.setBounds(10 , 10 , 10 , 10);
        textInputArea.setLineWrap(true);
        textInputArea.setWrapStyleWord(true);
        JScrollPane textInputScroll = new JScrollPane(textInputArea);
        textInputScroll.setBounds(20 ,132 , 484 , 236);

        JLabel morseCodeInputLabel = new JLabel("Morse Code:");
        morseCodeInputLabel.setFont(new Font("Dialog" , Font.PLAIN , 16));
        morseCodeInputLabel.setForeground(Color.WHITE);
        morseCodeInputLabel.setBounds(20 , 390 , 200 , 30);

        morseCodeArea = new JTextArea();
        morseCodeArea.setFont(new Font("Dialog" , Font.PLAIN , 18));
        morseCodeArea.setEditable(false);
        morseCodeArea.setLineWrap(true);
        morseCodeArea.setWrapStyleWord(true);
        morseCodeInputLabel.setBorder(BorderFactory.createEmptyBorder(10 ,10 ,10 , 10));

        JScrollPane morseCodeScroll = new JScrollPane(morseCodeArea);
        morseCodeScroll.setBounds(20 ,430 , 484 , 236);

        //play sound button
        JButton playSoundButton = new JButton("Play Sound");
        playSoundButton.setBounds(210 , 680 , 100 , 30);
        playSoundButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSoundButton.setEnabled(false);

                Thread playMorseCodeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            String[] morseCodeMessage = morseCodeArea.getText().split(" ");
                            morseCodeController.playSound(morseCodeMessage);
                        } catch (LineUnavailableException lineUnavailableException) {
                            lineUnavailableException.printStackTrace();
                        }catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }finally {
                            playSoundButton.setEnabled(true);
                        }
                    }
                });

                playMorseCodeThread.start();
            }
        });

        add(titleLabel);
        add(textInputLabel);
        add(textInputScroll);
        add(morseCodeInputLabel);
        add(morseCodeScroll);
        add(playSoundButton);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_SHIFT) {
            String inputText = textInputArea.getText();

            morseCodeArea.setText(morseCodeController.translateToMorse(inputText));

        }
    }
}
