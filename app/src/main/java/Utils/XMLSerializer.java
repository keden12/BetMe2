package Utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Stack;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XMLSerializer implements Serializer
{
	private Stack<Object> stack = new Stack<>();
  private File file;
  
  public XMLSerializer(File file)
	{
		this.file = file;
	}
	@Override
	public void push(Object o)
	{
		stack.push(o);
	}

	@Override
	public Object pop()
	{
		return stack.pop();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read() throws Exception
	{
    ObjectInputStream inputStream = null;

    try
    {
      XStream xstream = new XStream(new DomDriver());
      inputStream = xstream.createObjectInputStream(new FileReader(file));
      stack = (Stack<Object>) inputStream.readObject();
    }
    finally
    {
      if (inputStream != null)
      {
        inputStream.close();
      }
    }		
	}
	
	@Override
	public void write() throws Exception
	{
		ObjectOutputStream outputStream = null;

    try
    {
      XStream xstream = new XStream(new DomDriver());
      outputStream = xstream.createObjectOutputStream(new FileWriter(file));
      outputStream.writeObject(stack);
    }
    finally
    {
      if (outputStream != null)
      {
        outputStream.close();
      }
    }
		
	}



}
	
	
	
	

