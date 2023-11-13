import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Ex: ");

        try (Scanner in = new Scanner(System.in)) {
            int a = in.nextInt();

            switch (a) {
                case 1:
                    main1(args);
                    break;
                case 2:
                    main2(args);
            }
        }
    }

    public static void main1(String[] args) {
        JFrame frame = new JFrame("Diagonal Text Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(new DiagonalAnimation());
        frame.setVisible(true);

        Timer timer = new Timer(10, new ActionListener() {
            public int dx = 1;
            public int dy = 1;

            public void actionPerformed(ActionEvent e) {
                DiagonalAnimation panel = (DiagonalAnimation) frame.getContentPane().getComponent(0);
                panel.x += dx;
                panel.y += dy;
                if (panel.x < 0 || panel.x > 350) {
                    dx = -dx;
                    panel.changeTextCaseAndFont();
                }
                if (panel.y < 0 || panel.y > 460) {
                    dy = -dy;
                    panel.changeTextCaseAndFont();
                }
                panel.repaint();
            }
        });

        timer.start();
    }

    public static void main2(String[] args) {

        JFrame frame = new JFrame("File to Array Application");
        JPanel panel = new JPanel();
        JButton browseButton = new JButton("Browse");
        JLabel label = new JLabel("Select a CSV file:");
        JTextField textField = new JTextField(20);
        JTextField TArea = new JTextField(20);

        panel.add(label);
        panel.add(textField);
        panel.add(browseButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(TArea, BorderLayout.CENTER);

        browseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    textField.setText(filePath);

                    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                        String line;
                        ArrayList<Integer> intData = new ArrayList<>();

                        while ((line = reader.readLine()) != null) {
                            String[] data = line.split(",");

                            for (String str : data) {
                                try {
                                    int a = Integer.parseInt(str);
                                    intData.add(a);
                                } catch (NumberFormatException ex) {
                                    TArea.setText("Invalid data:" + str + "\n");
                                }
                            }
                        }

                        int n = (int) Math.sqrt(intData.size());

                        int[][] A = new int[n][n];
                        for (int i = 0; i < n; i++) {
                            for (int j = 0; j < n; j++) {
                                A[i][j] = intData.get(i * n + j);
                            }
                        }

                        for (int i = 0; i < n; i++) {
                            for (int j = 0; j < n; j++) {
                                System.out.println(A[i][j]);
                            }
                        }

                        TArea.setText("Data has been read and populated into array A.");

                        int N = 20;
                        int[] B = new int[N];
                        int k = 0;
                        int h = 7;

                        for (int i = 0; i <= n - 1; i++)
                            for (int j = 0; j < n; j++) {
                                if ((A[i][j] == 1) && (A[i][j + 1] == 2) && (A[i][j + 2] == 3) && (A[i][j + 3] == 3)
                                        && (A[i][j + 4] == 2) && (A[i][j + 5] == 1)) {
                                    B[k++] = i;
                                }
                            }

                        for (int i = 0; i <= h - 1; i++)
                            for (int j = 0; j < h; j++) {
                                if ((A[i][j] == 1) && (A[i][j + 1] == 2) && (A[i][j + 2] == 3) && (A[i][j + 3] == 5)
                                        && (A[i][j + 4] == 3) && (A[i][j + 5] == 2) && (A[i][j + 6] == 1)) {
                                    B[k++] = i;
                                }
                            }

                        String resultT = "Array B: " + Arrays.toString(Arrays.copyOf(B, k));
                        TArea.setText(resultT);

                    } catch (FileNotFoundException ex) {
                        TArea.setText("File not found.");
                    } catch (IOException ex) {
                        TArea.setText("Error reading the file.");
                    }

                    /*
                     * int N = 20;
                     * int[] B = new int[N];
                     * int k = 0;
                     * int h = 7;
                     * 
                     * 
                     * for (int i = 0; i < h; i++) {
                     * if (isSymmetricSequence(A[i], h)) {
                     * System.out.println("Line " + (i + 1));
                     * B[k++] = i;
                     * }
                     * }
                     * 
                     * static boolean isSymmetricSequence(int[] line, int h) {
                     * for (int i = 0; i < h / 2; i++) {
                     * if (line[i] != line[h - 1 - i]) {
                     * return false;
                     * }
                     * }
                     * return true;
                     * }
                     */

                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }
}

class DiagonalAnimation extends JPanel {
    int x = 0;
    int y = 0;
    String movingText = "Java Java Java";
    int textX = 0;
    Font currentFont = new Font("Serif", Font.PLAIN, 20);
    String[] availableFonts = { "Serif", "SansSerif", "Monospaced", "Dialog" };

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(currentFont);
        g.drawString(movingText, textX, y);
    }

    public void changeTextCaseAndFont() {
        Random random = new Random();
        StringBuilder modifiedText = new StringBuilder(movingText);
        for (int i = 0; i < modifiedText.length(); i++) {
            char c = modifiedText.charAt(i);
            if (Character.isLetter(c)) {
                if (random.nextBoolean()) {
                    modifiedText.setCharAt(i, Character.toUpperCase(c));
                } else {
                    modifiedText.setCharAt(i, Character.toLowerCase(c));
                }
            }
        }
        movingText = modifiedText.toString();

        currentFont = new Font(availableFonts[random.nextInt(availableFonts.length)], Font.PLAIN, 20);
    }
}
