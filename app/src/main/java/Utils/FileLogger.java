package Utils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger
{
  private static FileLogger logger;

  private FileLogger()
  {
  }

  public static FileLogger getLogger()
  {
    if (logger == null)
    {
      logger = new FileLogger();
    }
    return logger;
  }

  public boolean log(String msg)
  {
    try
    {
      PrintWriter writer = new PrintWriter(new FileWriter("log.txt", true));
      writer.println(msg);
      writer.close();
    }
    catch (FileNotFoundException ex)
    {
      return (false);
    }
    catch (IOException ex)
    {
      return (false);
    }
    return (true);
  }
}