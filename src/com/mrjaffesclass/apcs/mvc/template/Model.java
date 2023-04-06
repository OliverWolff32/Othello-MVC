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
        String MPString = (String)(messagePayload);
        MPString = MPStringSetter(MPString);
        System.out.println(MPString);
        int row = Integer.valueOf(MPString.substring(0,1));
        int col = Integer.valueOf(MPString.substring(1,2));
        
        
        if(board[row][col] == 0) {
            board[row][col] =  whoseMove ? 1 : -1;
            
            this.whoseMove = !this.whoseMove;
            String spaceColor = MPString;
            this.mvcMessaging.notify("colorChange", spaceColor);
        }
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
  }
  
  
  
  
}
