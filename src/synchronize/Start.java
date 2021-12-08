/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package synchronize;

import synchronize.controller.StartStopController;
import synchronize.controller.ValueAdapter;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import synchronize.counters.Counter;
import synchronize.model.DataModel;
import synchronize.view.MainView;

/**
 *
 * @author le
 */
public class Start 
{
  public Start()
  {
    MainView view = new MainView();
    DataModel model = new DataModel();
    model.doSubscribe();
    
    StartStopController controller = new StartStopController(view, model);
    ValueAdapter vAdapter = new ValueAdapter(view, model);
    
    controller.registerEvents();
    
    //model.doSubscribe();
    vAdapter.doSubscribe();
    //view.pack();
    view.setVisible(true);
  }

  public static void main(String[] args) 
  {
    try    
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }    
    catch (Exception ex)    
    {
      JOptionPane.showMessageDialog(null, ex.toString());
    }
    new Start();
  }

}
