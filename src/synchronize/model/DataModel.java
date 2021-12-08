/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package synchronize.model;

import synchronize.counters.Counter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;
import synchronize.util.OhmLogger;


/**
 *
 * @author le
 */
public class DataModel implements Flow.Subscriber<Integer[]> // Callable
{
  //Singleton:
  private static Logger lg = OhmLogger.getLogger();
  private final Object LOCK;
  private SubmissionPublisher<Integer[]> iPublisher;
  private ExecutorService eService;
  private Flow.Subscription[] subscription;
  
  private int count_Subs;
  private Integer[] hand_over;
  
  Counter counter1;
  Counter counter2;
  Counter counter3;
  
  public DataModel()
  {
    subscription = new Flow.Subscription[3];
    iPublisher = new SubmissionPublisher<>();
    eService = Executors.newSingleThreadExecutor();
    LOCK = new Object();
    counter1 = new Counter(0);
    counter2 = new Counter(1);
    counter3 = new Counter(2);
    count_Subs = 0;
    hand_over = new Integer[3];
  }
  
  public void addValueSubscriptior(Subscriber<Integer[]> subscriber)
  {
    iPublisher.subscribe(subscriber);
  }
  
  public void doSubscribe()         //subscribed bei allen 3 Counters
  {
    counter1.addValueSubscriptior(this);
    counter2.addValueSubscriptior(this);
    counter3.addValueSubscriptior(this);
    //lg.info("subscribe");
  }
  
  public synchronized void start()
  {
    
      //lg.info("Start");
      counter1.start();
      counter2.start();
      counter3.start();
    
    //eService.execute(this); // submit(this) liefert Future Object
  }
  
  public void stop()
  {
    synchronized(LOCK)
    {
      counter1.stop();
      counter2.stop();
      counter3.stop();
      //lg.info("Stop");
    }
  }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        lg.info("Subscribe");
        this.subscription[count_Subs] =  subscription;
        this.subscription[count_Subs].request(1);
        count_Subs += 1;
    }

    @Override
    public void onNext(Integer[] item) {
      hand_over[item[0]] = item[1];
      iPublisher.submit(hand_over);
      subscription[item[0]].request(1);
      //System.out.println("hier");
    }

    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onComplete() {
        System.err.println("Fertig"); //To change body of generated methods, choose Tools | Templates.
    }
}
