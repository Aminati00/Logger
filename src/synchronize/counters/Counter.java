/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package synchronize.counters;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;
import synchronize.util.OhmLogger;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;


/**
 *
 * @author svent
 */


public class Counter implements Runnable
{
    private static Logger lg = OhmLogger.getLogger();
    private SubmissionPublisher<Integer[]> iPublisher;
    private boolean running;
    private Integer position;
    private Integer[] value;
    private final Object LOCK;
    private ExecutorService eService;
    private Future task;

    
    public Counter(int position)
    {
        iPublisher = new SubmissionPublisher<>();
        running = true;
        this.position = position;
        value = new Integer[2];
        value[0] = this.position;
        value[1] = 0;
        LOCK = new Object();
        eService = Executors.newSingleThreadExecutor();
        task = null;
    }
    
    public void addValueSubscriptior(Flow.Subscriber<Integer[]> subscriber)
    {
        iPublisher.subscribe(subscriber);
    }
    
    
    public void start()
    {
      synchronized(LOCK)
      {
        lg.info("Start");
        running = true;
        LOCK.notifyAll();
        if (task == null)
        {
            task = eService.submit(this);
        }
      }
    }
    
    public void stop()
    {
      synchronized(LOCK)
      {
        lg.info("Stop");
        running = false;
      }
    }
    
    @Override
    public void run()
    {
    while (true)
    {
      while (!running) // nicht if verwenden!
      {
        synchronized(LOCK)
        {
          try
          {
            LOCK.wait();
          }
          catch (InterruptedException ex)
          {
            System.err.println(ex);
          }
        }
      }
      try
      {
        Thread.sleep(10);
      }
      catch (InterruptedException ex)
      {
        System.err.println(ex);
      }
      synchronized(this)
      {
        value[1] = (int)(9*Math.random());
      }
      // Subscriber benachrichtigen und Wert mitsenden (via Event)
      iPublisher.submit(value);  
    }
  }
    
}
