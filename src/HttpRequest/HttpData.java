package HttpRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
public class HttpData {
      public String content;
      public Hashtable cookies = new Hashtable();
      public Hashtable headers = new Hashtable();
      public InputStream stream ;
}