/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package synchronize.controller;

import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscription;
import java.util.logging.Logger;
import synchronize.model.DataModel;
import synchronize.util.OhmLogger;
import synchronize.view.MainView;

/**
 *
 * @author le
 */
public class ValueAdapter implements Flow.Subscriber<Integer[]>
{
  private static Logger lg = OhmLogger.getLogger();  
  
  private MainView view;
  private DataModel model;
  private Flow.Subscription subscription;
  
  public ValueAdapter(MainView view, DataModel model)
  {
    this.view = view;
    this.model = model;
  }
  
  public void doSubscribe()
  {
    model.addValueSubscriptior(this);
  }

  @Override
  public void onSubscribe(Flow.Subscription subscription)
  {
    lg.info("Subscribe");
    this.subscription = subscription;
    this.subscription.request(1);
  }

  @Override
  public void onNext(Integer[] item)
  {  
    view.getLblCounter1().setText(String.valueOf(item[0]));
    view.getLblCounter2().setText(String.valueOf(item[1]));
    view.getLblCounter3().setText(String.valueOf(item[2]));

    subscription.request(1);
  }

  @Override
  public void onError(Throwable throwable)
  {
    System.err.println(throwable);
  }

  @Override
  public void onComplete()
  {
    System.err.println("FERTIG");
  }
}
