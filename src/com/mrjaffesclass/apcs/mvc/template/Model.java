package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;
  Boolean whoseMove;

  // Model's data variables
  

  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
    Boolean whoseMove = true; 
    // true = white; false = black;
    
    
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
    } else if (messageName.equals("spaceClicked")) {
        
        this.mvcMessaging.notify("colorChange", this.whoseMove);
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
  }
  
  
  
  
}
