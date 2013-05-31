package duplicateemailsdetection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.TreeMap;


public class md5 {
	static TreeMap<String, String> files = new TreeMap<String, String>();
	public static void getHash()
	{
	
		String fhash="/cliphomes/ninggao/Deduplication//ContentAttach-noformat-hash.txt";
		Writer Fh = null;
		try {
			Fh = new PrintWriter(  
			       new OutputStreamWriter(new FileOutputStream(fhash), "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		String filepath = "/cliphomes/ninggao/Deduplication/visablepartsfilepath.txt";
              File finmen = new File(filepath);
		BufferedReader inreader = null;
		try
		{
			InputStreamReader read = new InputStreamReader(new FileInputStream(finmen),"utf-8"); 
			inreader = new BufferedReader(read);
            String tempString = null;
            int line = 1;
           
           
            while ((tempString = inreader.readLine())!= null) 
            {
            //	System.out.println(tempString);
            	String[] ss=new String[20];
            	
            	ss=tempString.split("\t");
           // 	System.out.println(tempString);
            	
            	
            		files.put(ss[0],ss[1]);
            //		System.out.println(tempString);
            	
            	
            }
       //     System.out.println(line);
            
		}catch (IOException e) 
        {
            e.printStackTrace();
        } finally 
        {
            if (inreader != null) 
            {
                try 
                {
                    inreader.close();
                } catch (IOException e1) 
                {
                }
            }
        }
        
     //   System.out.println(files.size());
        //process each email
        for(Object key: files.keySet())
        {
        	String emailpath = files.get(key);
	        File finemail = new File(emailpath);
			BufferedReader emailreader = null;
			
			try
			{
				InputStreamReader read = new InputStreamReader(new FileInputStream(finemail),"utf-8"); 
				emailreader = new BufferedReader(read);
	            String tempString = null;
	            String hashstring = "";
		     int line=0;
	            while ((tempString = emailreader.readLine())!= null) 
	            {
	            	if(tempString.length()==0)continue;
	            	
	            	
	             	
	            	if( tempString.startsWith("Content:")||tempString.startsWith("Attachment:"))
	            	{
	            		tempString.replace('\n', ' ');
		            	tempString.replace('\t', ' ');
		            	String[] ts = tempString.split(" ");
		            	for(int j=0; j<ts.length; j++)
		            	{
		            		if(ts[j].length()>0)
		            		{
		            			hashstring=hashstring+ts[j]+" ";
		            		}
		            	}
	            		
	            		
	            	}
	            	
	            }
			String partpath = files.get(key).substring(62, files.get(key).length());
			
			if(hashstring.length()>1)
			{
				
				
				
				
				Fh.write(getMD5(hashstring.getBytes())+"  "+partpath+"\n"); 
			}
			
	      
			}catch (IOException e) 
	        {
	            e.printStackTrace();
	        } finally 
	        {
	            if (emailreader != null) 
	            {
	                try 
	                {
	                    emailreader.close();
	                } catch (IOException e1) 
	                {
	                }
	            }
	        }
	       
        }
        try {
			Fh.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			Fh.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public static void main (String args[ ]) throws IOException{
		
	 getHash();

	}
	
		 public static String getMD5(byte[] source) {
		  String s = null;
		  char hexDigits[] = {       
		     '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'}; 
		   try
		   {
		    java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
		    md.update( source );
		    byte tmp[] = md.digest();          
		                                               
		    char str[] = new char[16 * 2];   
		                                                
		    int k = 0;                               
		    for (int i = 0; i < 16; i++) {          
		                                                
		     byte byte0 = tmp[i];                 
		     str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
		                                                            
		     str[k++] = hexDigits[byte0 & 0xf];            
		    } 
		    s = new String(str);                                

		   }catch( Exception e )
		   {
		    e.printStackTrace();
		   }
		   return s;
		 }
		
}