package duplicateemailsdetection;

import java.io.*;
import java.io.IOException;
import java.lang.*;
import java.lang.reflect.Array;
import java.util.*;

public class files {
	public static String emailname;
	public static String filehash;
	public static String path;
	public int isAttach=0;
	public ArrayList<String> attach;
	
	
	
	public static void test ()
        {
		
		String tempString ="3.1  1633  57.IPI   GZTJ    IDAVE\\nGZWI4C     RO4J  K4SAYXIZU5B.123.txt";
		
		tempString = tempString.toLowerCase();
                                        tempString = tempString.replaceAll("\t", " ");
                                        tempString = tempString.replaceAll("\n", "");
                                        String[] tempss = tempString.split(" ");
                                        for(int p = 0; p < tempss.length; p++)
                                        {
                                            if(tempss[p].length()>0)
                                            {
                                               System.out.println(tempss[p]);
                                            }
                                        }
	}
	
}

