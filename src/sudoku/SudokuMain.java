// Software: Sudoku Solver
// Author: Hy Truong Son
// Major: BSc. Computer Science
// Class: 2013 - 2016
// Institution: Eotvos Lorand University
// Email: sonpascal93@gmail.com
// Website: http://people.inf.elte.hu/hytruongson/
// Copyright (c) 2015. All rights reserved.

package sudoku;

import gui.SudokuGUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SudokuMain {
    
    private static final String titleFrame = "Sudoku Solver - Hy Truong Son";
    private static final int widthBox = 200;
    private static final int widthButton = 90;
    private static final int heightComponent = 30;
    private static final int marginFrame = 20;
    private static final int widthFrame = 4 * marginFrame + widthBox + 2 * widthButton;
    private static final int heightFrame = 2 * marginFrame + heightComponent;
    private static final int widthTableFrame = 800;
    private static final int heightTableFrame = 600;
    
    private static SudokuGUI gui = null;
    
    private static final String optionStrings[] = new String [] {
        "9 x 9 (cell 3 x 3)",
        "6 x 6 (cell 2 x 3)",
        "6 x 6 (cell 3 x 2)",
        "4 x 4 (cell 2 x 2)"
    };
    
    private static JFrame frame;
    private static JComboBox optionBox;
    private static JButton startButton, aboutButton;
    
    public static void main(String[] args) {
        frame = new JFrame();
        frame.setTitle(titleFrame);
        frame.setSize(widthFrame, heightFrame);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int x = marginFrame;
        
        optionBox = new JComboBox(optionStrings);
        optionBox.setBounds(x, marginFrame, widthBox, heightComponent);
        frame.add(optionBox);
        
        x += widthBox + marginFrame;
        
        startButton = new JButton("Start");
        startButton.setBounds(x, marginFrame, widthButton, heightComponent);
        frame.add(startButton);
        
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = optionBox.getSelectedIndex();
                String titleTableFrame = "Table " + optionStrings[option] + " - Hy Truong Son";
                if (option == 0) {
                    gui = new SudokuGUI(titleTableFrame, widthTableFrame, heightTableFrame, 9, 9, 3, 3);
                } else 
                    if (option == 1) {
                        gui = new SudokuGUI(titleTableFrame, widthTableFrame, heightTableFrame, 6, 6, 2, 3);
                    } else 
                        if (option == 2) {
                            gui = new SudokuGUI(titleTableFrame, widthTableFrame, heightTableFrame, 6, 6, 3, 2);
                        } else {
                            gui = new SudokuGUI(titleTableFrame, widthTableFrame, heightTableFrame, 4, 4, 2, 2);
                        }
            }
        });
        
        x += widthButton + marginFrame;
        
        aboutButton = new JButton("About");
        aboutButton.setBounds(x, marginFrame, widthButton, heightComponent);
        frame.add(aboutButton);
        
        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, 
                        "Software: Sudoku Solver\n"
                        + "Author: Hy Truong Son\n"
                        + "Major: BSc. Computer Science\n"
                        + "Class: 2013 - 2016\n"
                        + "Institution: Eotvos Lorand University, Budapest, Hungary\n"
                        + "Email: sonpascal93@gmail.com\n"
                        + "Website: http://people.inf.elte.hu/hytruongson/\n"
                        + "Copyright (c) 2015. All rights reserved.");
            }
        });
        
        frame.setVisible(true);
    }
    
}
