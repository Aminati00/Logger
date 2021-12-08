/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package synchronize.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import synchronize.model.DataModel;
import synchronize.view.MainView;

/**
 *
 * @author le
 */
public class StartStopController implements ActionListener
{
  private MainView view;
  private DataModel model;
  
  public StartStopController(MainView view, DataModel model)
  {
    this.view = view;
    this.model = model;
  }
  
  public void registerEvents()
  {
    view.getBtnStart().addActionListener(this);
    view.getBtnStop().addActionListener(this);
  }

  @Override
  public void actionPerformed(ActionEvent evt)
  {
    if (evt.getSource() == view.getBtnStart())
    {
      model.start();
    }
    else
    {
      model.stop();
    }
  }
}
