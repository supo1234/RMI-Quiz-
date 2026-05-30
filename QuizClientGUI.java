import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class QuizClientGUI extends JFrame {
    private JLabel questionLabel;
    private JButton[] optionButtons;
    private JLabel timerLabel, resultLabel, scoreLabel;
    private Timer timer;
    private int timeLeft = 10;
    private Quiz quizService;
    private int currentQuestion = 0;
    private int score;

    public QuizClientGUI() {
        setTitle("Online Knowledge Assessment System");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Connect to RMI server
        try {
            String serverIp = "serverIP"; // Server-side IP

            // Connect to server
            quizService = (Quiz) Naming.lookup("rmi://" + serverIp + ":1099/MyService");

            System.out.println("Connected to server");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Cannot connect to server:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        // GUI layout
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new BorderLayout());
        questionLabel = new JLabel("Question will appear here", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        questionLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        topPanel.add(questionLabel, BorderLayout.CENTER);

        timerLabel = new JLabel("Time: 10", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        timerLabel.setForeground(Color.RED);
        topPanel.add(timerLabel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Options loop
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        optionButtons = new JButton[3];
        for (int i = 0; i < 3; i++) {
            optionButtons[i] = new JButton("Option " + (char) ('A' + i));
            optionButtons[i].setFont(new Font("Segoe UI", Font.PLAIN, 16));
            optionButtons[i].setBackground(new Color(230, 240, 255));
            // action
            int finalI = i;
            optionButtons[i].addActionListener(e -> submitAnswer(finalI));
            centerPanel.add(optionButtons[i]);
        } // end loop
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
        add(centerPanel, BorderLayout.CENTER);

        // Bottom
        JPanel bottomPanel = new JPanel(new BorderLayout());
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bottomPanel.add(resultLabel, BorderLayout.CENTER);

        JButton viewScoreBtn = new JButton("View Score");
        viewScoreBtn.addActionListener(e -> {
            try {
                score = quizService.getScore();// Get Score form server
                JOptionPane.showMessageDialog(this, "Your score: " + quizService.getScore(), "Score",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

        });
        bottomPanel.add(viewScoreBtn, BorderLayout.EAST);

        scoreLabel = new JLabel("", SwingConstants.LEFT);
        scoreLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bottomPanel.add(scoreLabel, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);

        loadNextQuestion();
        setVisible(true);
    }

    // Question request from server
    private void loadNextQuestion() {
        try {
            String[] q = quizService.getQuestion(currentQuestion);
            if (q == null) {
                JOptionPane.showMessageDialog(this, " Quiz completed! ", "Done",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Questions
            questionLabel.setText((currentQuestion + 1) + ")  " + q[0]);
            for (int i = 0; i < 3; i++) {
                optionButtons[i].setText(q[i + 1]);
                optionButtons[i].setEnabled(true);
            }

            // Timmer
            resultLabel.setText("");
            timeLeft = 10;
            timerLabel.setText("Time: " + timeLeft);
            if (timer != null)
                timer.stop();

            timer = new Timer(1000, e -> {
                timeLeft--;
                timerLabel.setText("Time: " + timeLeft);
                if (timeLeft <= 0) {
                    timer.stop();
                    disableButtons();
                    resultLabel.setText("Time's up!");
                    moveToNext();
                }
            });
            timer.start();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading question:\n" + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void submitAnswer(int selectedIndex) {
        try {
            disableButtons();
            boolean correct = quizService.submitAnswer(currentQuestion, selectedIndex);
            if (correct) {
                resultLabel.setText("Correct!");
                resultLabel.setForeground(Color.GREEN);

                // score++;
                // scoreLabel.setText("Score: " + score);
            } else {
                resultLabel.setText("Incorrect!");
                resultLabel.setForeground(Color.RED);

            }

            timer.stop();
            moveToNext();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Submit error:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void moveToNext() {
        new Timer(2000, e -> {
            ((Timer) e.getSource()).stop();
            currentQuestion++;
            loadNextQuestion();
        }).start();
    }

    private void disableButtons() {
        for (JButton btn : optionButtons) {
            btn.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizClientGUI::new);
    }
}