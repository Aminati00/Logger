/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package synchronize.util;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.*;

/**
 *
 * @author le
 */
public class OhmLogger 
{
  private static Logger lg = null;
  private static Properties properties;
  public static String Level;
  public static String Dateipfad;
  
  
  private OhmLogger()
  {
 
  }
  
  public static Logger getLogger()
  {
    if (lg == null)
    {
      lg = Logger.getLogger("OhmLogger");
      initLogger();
    }
    return lg;
  }
  
  private static void getProperties() 
  {
      properties = new Properties();
      InputStream is = OhmLogger.class.getResourceAsStream("logger.properties");
      
      try
      {
          properties.load(is);
      }
      catch(IOException ex)
      {
         System.err.print(ex.toString());
      }
      
      Level = properties.getProperty("LOG_LEVEL");
      Dateipfad = properties.getProperty("LOG_DATEI");
  }
  
  private static void initLogger()
  {
    lg.setUseParentHandlers(false);
    getProperties();
    try
    {
        FileHandler fh = new FileHandler(Dateipfad);
        fh.setFormatter(new OhmFormatter());
        lg.addHandler(fh);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new OhmFormatter());
        lg.addHandler(ch);
    }
    catch (IOException ex)
    {
        System.err.print(ex.toString());
    }
  }
  
  
}

class OhmFormatter extends SimpleFormatter
{
  @Override
  public String format(LogRecord record)
  {
    String logLine = "| ";
    LocalDateTime ldt = LocalDateTime.now();
    logLine += ldt.toString();
    logLine += " | " + record.getLevel() + " | " + record.getSourceClassName() + " | " + record.getMessage() + "|";
    logLine += "\n";
    return logLine;
  }
}
