package ui;


import java.awt.Dimension;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import hw3.Powers;
import hw3.ShiftUtil;

/**
 * Main class for a GUI for a Powers
 *  game sets up a 
 * GamePanel instance in a frame.
 */
public class GameMain
{
  /**
   * Size of grid for the game.
   */
  private static final int GRID_SIZE = 4;
  
  /**
   * Attempt to animate movement of tiles.
   */
  private static final boolean USE_ANIMATION = true;
  
  /**
   * Print lots of output to the console.
   */
  private static final boolean VERBOSE = true;
  
  /**
   * Tile size in pixels.
   */
  public static final int TILE_SIZE = 100; 

  /**
   * Font size for displaying score.
   */
  public static final int SCORE_FONT = 24; 

  
  /**
   * Entry point.  Main thread passes control immediately
   * to the Swing event thread.
   * @param args not used
   */
  public static void main(String[] args)
  {
    final Random rand = new Random();
    
    Runnable r = new Runnable()
    {
      public void run()
      {
        create(GRID_SIZE, rand, USE_ANIMATION, VERBOSE);
      }
    };
    SwingUtilities.invokeLater(r);
  }

  
  /**
   * Helper method for instantiating the components.  This
   * method should be executed in the context of the Swing
   * event thread only.
   */
  private static void create(int gridSize, Random rand, boolean useAnimation, boolean verbose)
  {
    // create a game 
    ShiftUtil util = new ShiftUtil();
    Powers game = new Powers(gridSize, util, rand);
    
    // create the two UI panels
    ScorePanel scorePanel = new ScorePanel();
    GamePanel panel = new GamePanel(game, scorePanel, useAnimation, verbose);
    
    // arrange the two panels vertically
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.add(panel);
    mainPanel.add(scorePanel);
    
    // put main panel in a window
    JFrame frame = new JFrame("Com S 227 Powers Game");
    frame.getContentPane().add(mainPanel);

    // give panels a nonzero size
    Dimension d = new Dimension(gridSize * GameMain.TILE_SIZE, gridSize * GameMain.TILE_SIZE);   
    panel.setPreferredSize(d);
    d = new Dimension(gridSize * GameMain.TILE_SIZE, GameMain.TILE_SIZE);
    scorePanel.setPreferredSize(d);
    frame.pack();
    
    // we want to shut down the application if the 
    // "close" button is pressed on the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
    panel.grabFocus();
    
    // rock and roll...
    frame.setVisible(true);
  }
  
  
  
  
}
