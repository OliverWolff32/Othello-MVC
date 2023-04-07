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
  
  public Boolean isLegalMove(String mp) {
      int row = Integer.valueOf(mp.substring(0,1));
      int col = Integer.valueOf(mp.substring(1,2));
      return this.board[row][col] == 0;
  }
  
  public void setBoardState(String mp) {
      int row = Integer.valueOf(mp.substring(0,1));
      int col = Integer.valueOf(mp.substring(1,2));
      board[row][col] =  this.whoseMove ? 1 : -1;
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
  
  
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
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
            
            this.mvcMessaging.notify("displayMove", this.whoseMove);            
            this.mvcMessaging.notify("colorChange", MPString); // tells view to change the visuals
        }
            
        
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
  }
  
  
  
  
}
