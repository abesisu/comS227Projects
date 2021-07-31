package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import api.Direction;
import api.Descriptor;
import api.MoveResult;
import api.TilePosition;
import hw3.Powers;

/**
 * User interface for the main grid 
 */
public class GamePanel extends JPanel
{
  /**
   * Not used, required to turn off compiler warning.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Background color.
   */
  private static final Color BACKGROUND_COLOR = Color.GRAY;
  
  /**
   * Colors of cells
   */
  private Color[] colors = {
    null, // placeholder
    Color.YELLOW, // 2
    new Color(255, 234, 153), // 4
    new Color(255, 204, 0), // 8
    new Color(224, 224, 102), // 16
    new Color(51, 214, 133), // 32
    new Color(0, 255, 204), // 64
    new Color(112, 219, 219), // 128 
    new Color(77, 184, 255), // 256
    new Color(153, 204, 255), // 512
    new Color(184, 148, 255), // 1024
    Color.RED,    // 2048

  };
  
  /**
   * Flag indicating whether to try to animate the moving tiles.
   */
  private boolean useAnimation;
  
  /**
   * Flag indicating whether to print lots of text output.
   */
  private boolean verbose;
  
  /**
   * Millis per frame when animating.
   */
  private int frameRate = 10; // millis per frame when animating

  /**
   * Number of frames over which to animate the collapsing tiles.
   */
  private final int framesPerCollapse = 30;
  
  /**
   * Number of frames over which to animate the appearance of a new tile.
   */
  private final int framesPerNewTile = 30;

  /**
   * Counter for animation frames for collapsing.
   */
  private int frameCount = 0;
  
  /**
   * Counter for animation frames for appearance of new tile.
   */
  private int newTileCount = 0;
  
  /**
   * Score panel associated with the game.
   */
  private ScorePanel scorePanel;

  /**
   * The javax.swing.Timer instance used to animate the UI.
   */
  private Timer timer;

  /**
   * The Game instance for which this is the UI.
   */
  private Powers game;

  /**
   * All tiles currently being rendered.
   */
  private ArrayList<Tile> tiles = new ArrayList<>();

  /**
   * New tile about to appear in the grid, before being added to list.
   */
  private Tile newTile = null;

  /**
   * Constructs a GamePanel with the given game and associated ScorePanel.
   * @param game 
   *   the Powerws instance for which this is the UI
   * @param scorePanel
   *   panel for displaying scores associated with the game
   * @param useAnimation
   *   true if the UI should attempt to animate the movement of tiles
   * @param verbose
   *   true if UI should print console output
   */
  public GamePanel(Powers game, ScorePanel scorePanel, boolean useAnimation, boolean verbose)
  {
    this.game = game;
    this.scorePanel = scorePanel;
    this.useAnimation = useAnimation;
    this.verbose = verbose;
    
    timer = new Timer(frameRate , new TimerCallback());
    KeyListener listener = new MyKeyListener();
    this.addKeyListener(listener);
    
    // initialize tiles from initial game state
    for (int row = 0; row < game.getSize(); row += 1)
    {
      for (int col = 0; col < game.getSize(); col += 1)
      {
        int value = game.getTileValue(row, col);
        if (value > 0)
        {
          Tile t = new Tile(col, row, value);
          tiles.add(t);
        }
      }
    }
  }
  
  // The paintComponent method is invoked by the Swing framework whenever
  // the panel needs to be rendered on the screen.  In this application,
  // repainting is normally triggered by the calls to the repaint() 
  // method in the timer callback and the keyboard event handler (see below).
  
  @Override
  public void paintComponent(Graphics g)
  {
    // clear background
    g.setColor(BACKGROUND_COLOR);
    g.fillRect(0, 0, getWidth(), getHeight());
    
    // paint occupied cells of the grid
    for (Tile t : tiles)
    {
      paintTile(g, t);
    }
  }
  
  /**
   * Paint the given tile using the given graphics context. If animation
   * is taking place, the current new tile is treated specially.
   * @param g
   * @param t
   */
  private void paintTile(Graphics g, Tile t)
  {
    // don't paint the new tile until we finish moving the old ones
    if (t == newTile && frameCount > 0) return;
    
    int x = t.getCurrentX();
    int y = t.getCurrentY();
    
    Color c = getColor(t);
    if (t == newTile && newTileCount > 0)
    {
      // animate appearance of new tile on the grid by "fading in"
      // interpolate between background color and new tile color
      int bRed = BACKGROUND_COLOR.getRed();
      int bGreen = BACKGROUND_COLOR.getGreen();
      int bBlue = BACKGROUND_COLOR.getBlue();

      int red = bRed + (c.getRed() - bRed) / framesPerNewTile * (framesPerNewTile - newTileCount);
      int green = bGreen + (c.getGreen() - bGreen) / framesPerNewTile *  (framesPerNewTile - newTileCount);
      int blue = bBlue + (c.getBlue() - bBlue) / framesPerNewTile *  (framesPerNewTile - newTileCount);
      c = new Color(red, green, blue);
    }

    // draw the rectangle
    g.setColor(c);
    g.fillRoundRect(x + 4, y + 4, GameMain.TILE_SIZE - 8, GameMain.TILE_SIZE - 8, 10, 10);
    
    // font nonsense to center text on the rectangle
    Font f = new Font(Font.SANS_SERIF, Font.PLAIN, GameMain.SCORE_FONT);
    g.setFont(f);
    FontMetrics fm = g.getFontMetrics(f);
    String text = "" + t.getCurrentValue();
    int h = fm.getHeight();
    int w = fm.stringWidth(text);
    int textX = x + GameMain.TILE_SIZE / 2 - (w / 2);
    int textY = y + GameMain.TILE_SIZE / 2 + (h / 2) - 2;
    
    // draw the number
    g.setColor(Color.BLACK);
    g.drawString(text, textX, textY);
  }
  
  /**
   * Pick a color for the tile based on the number.
   * @param t
   * @return
   */
  private Color getColor(Tile t)
  {
    int value = t.getCurrentValue();
    
    // find power of 2
    int count = 1;
    int p = 2;
    while (p < value)
    {
      count += 1;
      p *= 2;
    }
    
    // clamp to range if necessary
    int index = Math.max(Math.min(count, colors.length - 1), 0);
    return colors[index];
  }
  
  /**
   * Print a string to the console, if in verbose mode.
   * @param msg
   */
  private void log(String msg)
  {
    if (verbose)
    {
      System.out.println(msg);
    }
  }
  
  /**
   * Print the game grid to the console if in verbose mode.
   */
  private void printGrid()
  {
    if (verbose)
    {
      System.out.println("---------------");
      for (int row = 0; row < game.getSize(); row += 1)
      {
        for (int col = 0; col < game.getSize(); col += 1)
        {
          System.out.printf("%4d", game.getTileValue(row, col));
        }
        System.out.println();
      }
      System.out.println("---------------");
    }    
  }
  
  /**
   * Error check the given descriptors, convert to normalized moves, and update the tiles
   * in the 'before' array with new positions and values.
   * @param moves
   * @param normalizedMoves
   * @param before
   * @return
   */
  private boolean createNormalizedMoves(ArrayList<Descriptor> moves, ArrayList<NormalizedMove> normalizedMoves, Tile[][] before)
  {   
    for (Descriptor mm : moves)           
    {
      log(mm.toString());
      if (mm.getDirection() == null || mm.getRowOrColumn() < 0 || mm.getRowOrColumn() >= game.getSize())
      {
        log("ERROR: Descriptor has missing direction or invalid row/column index");
        return false;  // can't animate
      }
      NormalizedMove m = makeMoveFromMiniMove(mm);
      log(m.toString());

      int row = m.getOldPosition().getRow();
      int col = m.getOldPosition().getCol();
      if (row < 0 || row >= game.getSize() || col < 0 || col >= game.getSize())
      {
        log("ERROR: Descriptor has existing row or column out of range");
        return false;  // can't animate
      }
      int newRow = m.getNewPosition().getRow();
      int newCol = m.getNewPosition().getCol();
      if (newRow < 0 || newRow >= game.getSize() || newCol < 0 || newCol >= game.getSize())
      {
        log("ERROR: Descriptor has destination row or column out of range");
        return false;  // can't animate
      }
      if (m.getOldPosition2() != null)
      {
        int row2 = m.getOldPosition2().getRow();
        int col2 = m.getOldPosition2().getCol();
        if (row2 < 0 || row2 >= game.getSize() || col2 < 0 || col2 >= game.getSize())
        {
          log("ERROR: Merge descriptor has existing row or column out of range for second tile");
          return false;  // can't animate
        }
      }     

      int value = m.getValue();
      
      // each old position should correspond to the existing position of a tile
      // in the before array
      if (before[row][col] == null)
      {
        log("WARNING: attempt to move from empty cell " + row + ", " + col);
      }
      if (before[row][col].getCurrentValue() != value)
      {
        log("WARNING: move from " + row + ", " + col + " has incorrect value" );                               
      }
      if (m.getOldPosition2() != null)
      {
        int row2 = m.getOldPosition2().getRow();
        int col2 = m.getOldPosition2().getCol();
        if (before[row2][col2] == null) 
        {
          log("WARNING: attempt to move from empty cell " + row2 + " " + col2);
        }
        if (before[row2][col2].getCurrentValue() != value)
        {
          log("WARNING: move from " + row2 + " " + col2 + " has incorrect value" );                               
        }

        // set up tile for a merge to new position, value doubles
        before[row][col].setNew(newCol, newRow, value + value, framesPerCollapse);
        before[row2][col2].setNew(newCol, newRow, value + value, framesPerCollapse);
      }
      else
      {
        // set up tile for a move, not a merge, so new value will be the same
        before[row][col].setNew(newCol, newRow, value, framesPerCollapse);

      }

      normalizedMoves.add(m);
    }
    
    return true;
  }
  
  /**
   * Converts the Descriptor object returned by the game into a normalized form
   * with actual row and column indices.
   * @param mv
   * @return
   */
  private NormalizedMove makeMoveFromMiniMove(Descriptor mv)
  {
    int rowOrColumn = mv.getRowOrColumn();
    Direction dir = mv.getDirection();
    
    Position oldPos = null;
    Position newPos= null;
    Position oldPos2 = null;
  
    switch (dir)
    {
      case LEFT:
        oldPos = new Position(rowOrColumn, mv.getOldIndex());
        newPos = new Position(rowOrColumn, mv.getNewIndex());
        if (mv.isMerge())
        {
          oldPos2 = new Position(rowOrColumn, mv.getOldIndex2());
        }
        break;
      case RIGHT:
        oldPos = new Position(rowOrColumn, game.getSize() - mv.getOldIndex() - 1);
        newPos = new Position(rowOrColumn, game.getSize() - mv.getNewIndex() - 1);
        if (mv.isMerge())
        {
          oldPos2 = new Position(rowOrColumn, game.getSize() - mv.getOldIndex2() - 1);
        }
        break;
      case UP:
        oldPos = new Position(mv.getOldIndex(), rowOrColumn);
        newPos = new Position(mv.getNewIndex(), rowOrColumn);
        if (mv.isMerge())
        {
          oldPos2 = new Position(mv.getOldIndex2(), rowOrColumn);
        }
        break;
      case DOWN:
        oldPos = new Position(game.getSize() - mv.getOldIndex() - 1, rowOrColumn);
        newPos = new Position(game.getSize() - mv.getNewIndex() - 1, rowOrColumn);
        if (mv.isMerge())
        {
          oldPos2 = new Position(game.getSize() - mv.getOldIndex2() - 1, rowOrColumn);
        }
        break;
        
    }
 
    if (mv.isMerge())
    {
      NormalizedMove m = new NormalizedMove(oldPos, oldPos2, newPos, mv.getValue());
      return m; 
    }
    else
    {
      NormalizedMove m = new NormalizedMove(oldPos, newPos, mv.getValue());
      return m;            
    }

  }
   
  /**
   * Creates a 2D array of Tile objects from the current game state.
   * @return
   */
  private Tile[][] initializeFromGame()
  {
    Tile[][] ret = new Tile[game.getSize()][game.getSize()];  
    for (int row = 0; row < game.getSize(); row += 1)
    {
      for (int col = 0; col < game.getSize(); col += 1)
      {
        int value = game.getTileValue(row, col);
        {
          if (value > 0)
          {
            Tile t = new Tile(col, row, value);
            ret[row][col] = t;
            
            //System.out.println("Recording tile at " + row + " " + col + " value " + value);
          }
        }
      }
    }
    return ret;
  }
  
  /**
   * Cancel current animation.
   */
  private void stopAnimation()
  {
    if (frameCount > 0 || newTileCount > 0)
    {
      timer.stop();
    }
    if (frameCount > 0)
    {
      for (Tile t : tiles)
      {
        t.finish();
      }
      tiles.add(newTile);
      newTile = null;
      scorePanel.updateScore(game.getScore());
    }
    frameCount = 0;
    newTileCount = 0;    
  }
  
  /**
   * Listener for timer events.  The actionPerformed method
   * is invoked each time the timer fires and the call to
   * repaint() at the bottom of the method causes the panel
   * to be redrawn.
   */
  private class TimerCallback implements ActionListener
  {
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
      
      if (frameCount > 0)
      {
        // update state of each tile
        for (Tile t : tiles)
        {
          t.step();
        }

        frameCount -= 1;

        if (frameCount == 0)
        {
          for (Tile t : tiles)
          {
            // just in case, make sure tiles are in their final places
            t.finish();
          }
          tiles.add(newTile);
          
          // animate appearance of new tile
          newTileCount = framesPerNewTile;
          
          scorePanel.updateScore(game.getScore());
        }
      }
      else
      {
        if (newTileCount > 0)
        {
 
          newTileCount -= 1;
          if (newTileCount == 0)
          {
            timer.stop();
            newTile = null;
          }
        }
      }
      
      // trigger a repaint event
      repaint();
    }
  }
  
  /**
   * Handler for key events.
   */
  private class MyKeyListener implements KeyListener
  {
    @Override
    public void keyPressed(KeyEvent event)
    {
      int key = event.getKeyCode();
      Direction dir = null;
      switch( key ) { 
        case KeyEvent.VK_UP:
          log("INFO: Key UP");
          dir = Direction.UP;
          break;
        case KeyEvent.VK_DOWN:
          log("INFO: Key DOWN");
          dir = Direction.DOWN;
          break;
        case KeyEvent.VK_LEFT:
          log("INFO: Key LEFT");
          dir = Direction.LEFT;
          break;
        case KeyEvent.VK_RIGHT :
          log("INFO: Key RIGHT");
          dir = Direction.RIGHT;
          break;
        default:
          return;
      }
      
      log("INFO: Grid before collapse:");
      printGrid();
      
      // if we can't set up animation due to errors in moves, revert to 
      // doing it without animation
      boolean animationSetupOk = true;
      
      if (!useAnimation)
      {
        // just get current locations after collapse, ignore Result object
        game.doMove(dir);
        log("INFO: Grid after collapse:");
        printGrid();
      }
      else
      {
        // if we are currently animating anything, stop before processing next key
        stopAnimation();
        
        // get current locations before collapse, save by row and col
        Tile[][] before = initializeFromGame();

        // collapse and get moves
        MoveResult gameResult = game.doMove(dir);

        log("INFO: Grid after collapse:");
        printGrid();

        ArrayList<Descriptor> moves = gameResult.getMoves();        
        if (moves.size() == 0)
        {
          log("INFO: MoveResult contains no moves");
        }
        else
        {
          ArrayList<NormalizedMove> normalizedMoves = new ArrayList<>();
          boolean validMoves = createNormalizedMoves(moves, normalizedMoves, before);         
          if (!validMoves)
          {
            log("ERROR: unable to attempt animation due to errors in move descriptors");
            animationSetupOk = false;
          }
          TilePosition newTilePosition = gameResult.getNewTile();
          newTile = new Tile(newTilePosition.getCol(), newTilePosition.getRow(), newTilePosition.getValue());
          if (newTile == null)
          {
            animationSetupOk = false;
            log("ERROR: unable to attempt animation due to missing new tile position");
          }
          
          if (validMoves && newTile != null)
          {
            // copy tiles from before array into our list
            tiles.clear();
            for (int row = 0; row < game.getSize(); row += 1)
            {
              for (int col = 0; col < game.getSize(); col += 1)
              {
                if (before[row][col] != null)
                {
                  Tile t = before[row][col];
                  tiles.add(t);
                }
              }
            }

            // start animation
            frameCount = framesPerCollapse;
            timer.start();
          }
        }
      }
      
      if (!useAnimation || !animationSetupOk)
      {
        // just get current locations after collapse, ignore Result object
        scorePanel.updateScore(game.getScore());

        tiles.clear();
        for (int row = 0; row < game.getSize(); row += 1)
        {
          for (int col = 0; col < game.getSize(); col += 1)
          {
            int value = game.getTileValue(row, col);
            {
              if (value > 0)
              {
                Tile t = new Tile(col, row, value);
                tiles.add(t);
              }
            }
          }
        }
      }
      
      repaint();    
    }
   
    @Override
    public void keyReleased(KeyEvent event)
    {
      // not used
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
      // not used
    }
    
  }

}
