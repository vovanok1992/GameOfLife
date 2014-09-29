package tk.vovanok.gameoflife.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingUtilities;

import tk.vovanok.gameoflife.core.FieldCalculator;

public class MainWindow extends JFrame implements MouseListener, MouseMotionListener  {

    private JPanel contentPane;
    private FieldPanel gamePanel;

    private JButton btnAuto;
    private Timer t;
    private JSpinner delaySpin;
    private JButton btnRandom;
    private JButton btnClear;
    private JMenuBar menuBar;
    private JMenu mnFile;
    private JMenuItem mntmSettings;
    private JMenuItem mntmSave;
    private JMenuItem mntmRestore;

    private SettingsWindow settingsGui;
    private JComboBox comboBox;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            System.out.println("Can not load look and feel!");
            e1.printStackTrace();
        }
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainWindow frame = new MainWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        mnFile = new JMenu("File");
        menuBar.add(mnFile);

        mntmSettings = new JMenuItem("Settings");
        mntmSettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                settingsGui.setGamePanel(gamePanel);
                settingsGui.setVisible(true);
            }
        });
        mnFile.add(mntmSettings);

        mntmSave = new JMenuItem("Save");
        mnFile.add(mntmSave);

        mntmRestore = new JMenuItem("Restore");
        mnFile.add(mntmRestore);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
        setResizable(false);

        gamePanel = new FieldPanel(100, 80);
        gamePanel.addMouseListener(this);
        gamePanel.addMouseMotionListener(this);
        contentPane.add(gamePanel, BorderLayout.CENTER);

        JPanel controls = new JPanel();
        contentPane.add(controls, BorderLayout.NORTH);

        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gamePanel.calculate();
                gamePanel.repaint();
            }
        });

        btnRandom = new JButton("Random");
        btnRandom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                gamePanel.randomize();
            }
        });

        btnClear = new JButton("Clear");
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.clear();
            }
        });
        controls.add(btnClear);
        controls.add(btnRandom);
        controls.add(btnNext);

        btnAuto = new JButton("Start!");
        btnAuto.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (t.isRunning()) {
                    t.stop();
                    ((JButton) arg0.getSource()).setText("Start!");
                } else {
                    t.start();
                    ((JButton) arg0.getSource()).setText("Stop!");
                }
            }
        });

        controls.add(btnAuto);

        delaySpin = new JSpinner();
        delaySpin.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {

                t.setDelay(((int) delaySpin.getValue()) * 25);

            }
        });
        delaySpin.setModel(new SpinnerNumberModel(12, 1, 50, 1));
        controls.add(delaySpin);

        comboBox = new JComboBox();
        comboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                switch(comboBox.getSelectedIndex()){
                    case 0:
                        gamePanel.setRule(FieldCalculator.RULE_CLASSIC);
                        break;
                    case 1:
                        gamePanel.setRule(FieldCalculator.RULE_GNARL);
                        break;
                    case 2:
                        gamePanel.setRule(FieldCalculator.RULE_MAZE);
                        break;
                    case 3:
                        gamePanel.setRule(FieldCalculator.RULE_COAGULATIONS);
                }
            }
        });
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Classic life", "Gnarl", "Maze", "Coagulations"}));
        comboBox.setToolTipText("Choose rule");
        controls.add(comboBox);
        pack();

        t = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.calculate();
                gamePanel.repaint();
            }
        });

        settingsGui = new SettingsWindow();
        settingsGui.setModal(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gamePanel.reverseGameArrayValue((e.getX() / 10), (e.getY() / 10));
        gamePanel.fCalc.calcCols();
        gamePanel.repaint();
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gamePanel.setEraser(false);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        int x = e.getX();
        int y = e.getY();
        gamePanel.updateCursorPosition(x, y);
        
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (gamePanel.fCalc.isAlive(x / 10, y / 10)) {
                return;
            }
            gamePanel.reverseGameArrayValue((x / 10), (y / 10));
            gamePanel.fCalc.calcCols();
            gamePanel.repaint();
        } else {
            gamePanel.setEraser(true);
            gamePanel.fCalc.erase((x / 10), (y / 10), 9);
            gamePanel.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gamePanel.updateCursorPosition(e.getX(), e.getY());
        gamePanel.repaint();
    }

}
