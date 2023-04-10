package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;
import javax.swing.JButton;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;
  Boolean whoseMove = true;
  int[][] board = new int[8][8];
  

  // Model's data variables
  
  
  
  
  
  
  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
    Boolean whoseMove = true; 
    for(int i = 0; i < 8; i++) {
        for(int j = 0; j < 8; j++) {
            board[i][j] = 0;
        }
    }
    // true = white; false = black;
  }
  
  public String spaceNameTrim(String name) {
      name = name.substring(7);
      return name;
  }
  
  public String MPStringSetter(String str) {
      String ret = spaceNameTrim(str);
      if(this.whoseMove) {
              ret += "t";
            } else {
              ret += "f";
            }
      return ret; 
  }
  
  
  public int getSquareValue(int row, int col) {
      return this.board[row][col]; 
  }
  
  public int getDistanceToWall(String dir, int row, int col) {
      if(dir.equals(Constants.NORTH) ) {
          return row;
          
      } else if (dir.equals(Constants.NORTHEAST)) {
          return (7-col) < row ? (7-col) : row;
          
      } else if (dir.equals(Constants.EAST)) {
          return (7-col);
          
      } else if (dir.equals(Constants.SOUTHEAST) ) {
          return (7-col) < (7-row) ? (7-col) : (7-row);
          
      } else if (dir.equals(Constants.SOUTH)) {
          return (7-row);
          
      } else if (dir.equals(Constants.SOUTHWEST) )  {
          return (col) < (7-row) ? col : (7-row);
          
      } else if (dir.equals(Constants.WEST) ) {
          return col ;
          
      } else if (dir.equals(Constants.NORTHWEST) ) {
          return row < col ? row : col ;
      }
      
      return -1;
  }
  
  /**
   * returns square int value 1 space in direction passed
   * @param dir
   * @param row
   * @param col
   * @return 
   */
  public int getDirectionSquare(String dir, int row, int col, int spaces) {
      if(dir.equals(Constants.NORTH) ) {
          return getSquareValue(row-spaces, col);
          
      } else if (dir.equals(Constants.NORTHEAST)) {
          return getSquareValue(row-spaces, col+spaces);
          
      } else if (dir.equals(Constants.EAST)) {
          return getSquareValue(row, col+spaces);
          
      } else if (dir.equals(Constants.SOUTHEAST) ) {
          return getSquareValue(row+spaces, col+spaces);
          
      } else if (dir.equals(Constants.SOUTH)) {
          return getSquareValue(row+spaces, col);
          
      } else if (dir.equals(Constants.SOUTHWEST) )  {
          return getSquareValue(row+spaces, col-spaces);
          
      } else if (dir.equals(Constants.WEST) ) {
          return getSquareValue(row, col-spaces);
          
      } else if (dir.equals(Constants.NORTHWEST) ) {
          return getSquareValue(row-spaces, col-spaces);
      }
      return 10; 
  }
  
  public Boolean checkDirectionForLegalMove(String dir, int row, int col) {
      String str = "";
      int target = (getSquareValue(row, col));
      
      for (int i = 0; i < getDistanceToWall(dir, row, col); i++) {
          str += getDirectionSquare(dir, row, col, i+1);          
      }
      Boolean bool; 
      for(int i = 0; i < str.length(); i++) {
          if(target != (-1 * Integer.valueOf(str.charAt(i)))) {
              bool = false;
          }
      }// THIS IS WHERE I LEFT OFF
      
      
      return false;
  }
  
  
  /**
   * checks adjacent squares in a line recursively if they are the opposite color
   * if there is an opposite color, continue in that direction until you hit 
   * a blank square(return false) or a target colored square(return true)
   * @return 
   */
  public Boolean checkAdjSquaresRec(int targetColor, String dir, int row, int col, int count) { 
      
      
      
      
      
      
      return false; 
  }
  
  public Boolean isLegalMove(String mp) {
      Boolean legal;
      int row = Integer.valueOf(mp.substring(0,1));
      int col = Integer.valueOf(mp.substring(1,2));
      legal = this.board[row][col] == 0; // check if target is filled
      
      
      
      
      
      return legal;
  }
  
  public void setBoardState(String mp) {
      int row = Integer.valueOf(mp.substring(0,1));
      int col = Integer.valueOf(mp.substring(1,2));
      board[row][col] =  this.whoseMove ? 1 : -1;
      this.mvcMessaging.notify("colorChange", mp); // tells view to change the visuals
  }
  
  public int countWhiteSquares() {
      int whiteSquares = 0;
      for(int i = 0; i < 8; i++) {
          for(int j = 0; j < 8; j++) {
              if(board[i][j] == 1) {
                  whiteSquares++;
              }
          }
      }
      return whiteSquares;
  }
  
  public int countBlackSquares() {
      int blackSquares = 0;
      for(int i = 0; i < 8; i++) {
          for(int j = 0; j < 8; j++) {
              if(board[i][j] == -1) {
                  blackSquares++;
              }
          }
      }
      return blackSquares;
  }
  
  
  
  public void setInitialBoard() {
      board[3][3] = 1;
      this.mvcMessaging.notify("colorChange", "33t");
      board[3][4] = -1;
      this.mvcMessaging.notify("colorChange", "34f");
      board[4][3] = -1;
      this.mvcMessaging.notify("colorChange", "43f");
      board[4][4] = 1;
      this.mvcMessaging.notify("colorChange", "44t");
  }
  
  
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
    setInitialBoard();
    this.mvcMessaging.subscribe("spaceClicked", this);
  }
  
  
  
  
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
    if (messagePayload != null) {
      System.out.println("MSG: received by model: "+messageName+" | "+messagePayload.toString());
    } 
    if (messageName.equals("spaceClicked")) {
        //set message payload to row, col, whosemove
        String MPString = (String)(messagePayload);
        MPString = MPStringSetter(MPString);
        System.out.println(MPString);
        
        
        if(isLegalMove(MPString)) {
            setBoardState(MPString); //changes the board spot that was clicked 
                                     //to 1 if white and -1 if black        
            
            this.whoseMove = !this.whoseMove; // changes whose move it is
            
            this.mvcMessaging.notify("countWhiteSquares", countWhiteSquares());
            this.mvcMessaging.notify("countBlackSquares", countBlackSquares());
            this.mvcMessaging.notify("displayWhosMove", this.whoseMove);            
            
        }
            
        
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
  }
  
  
  
  
}
