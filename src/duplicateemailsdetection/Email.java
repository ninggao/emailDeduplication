package duplicateemailsdetection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.*;
import java.io.*;


public class Email {
	public static int filecount =0;
//	static TreeMap<String, Vector<files>> allfiles = new TreeMap<String, Vector<files>>();
	static Set<String> duphash =new HashSet<String>();
	static Vector<String> dupemails = new Vector<String>();
	static Set<String> uniqmsg = new HashSet<String>();
	
	static TreeMap<String, String> originalfilepath = new TreeMap<String, String>();
	static TreeMap<String, String> tagfilepath = new TreeMap<String, String>();
	
	static void comparedups()
	{
		//read in original filepath
				String ofilepath = "/home/ediscovery/workspace/deduplication/data/results/originalfilepath.txt";
				
		        File finmen = new File(ofilepath);
				BufferedReader inreader = null;
				try
				{
					InputStreamReader read = new InputStreamReader(new FileInputStream(finmen),"utf-8"); 
					inreader = new BufferedReader(read);
		            String tempString = null;
		         
		            while ((tempString = inreader.readLine())!= null) 
		            {
		            	String[] ss=new String[20];
		            	ss=tempString.split("\t");
		          //  	System.out.println(ss[0]+"\t"+ss[1]);
		            	originalfilepath.put(ss[0], ss[1]);
		            }
		         //   System.out.println(originalfilepath.size());
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
				
				//read in tag emailpath
				String tfilepath = "/home/ediscovery/workspace/deduplication/data/results/tagfilepath.txt";
				
		        File tfinmen = new File(tfilepath);
				BufferedReader tinreader = null;
				try
				{
					InputStreamReader tread = new InputStreamReader(new FileInputStream(tfinmen),"utf-8"); 
					tinreader = new BufferedReader(tread);
		            String tempString = null;
		         
		            while ((tempString = tinreader.readLine())!= null) 
		            {
		            	String[] ss=new String[20];
		            	ss=tempString.split("\t");
		         //   	System.out.println(ss[0]+"\t"+ss[1]);
		            	tagfilepath.put(ss[0], ss[1]);
		            }
		         //   System.out.println(tagfilepath.size());
				}catch (IOException e) 
		        {
		            e.printStackTrace();
		        } finally 
		        {
		            if (tinreader != null) 
		            {
		                try 
		                {
		                    tinreader.close();
		                } catch (IOException e1) 
		                {
		                }
		            }
		        }
				
				//read in trec uniqmsg
				String trecfilepath = "/home/ediscovery/workspace/deduplication/data/results/contentattach-uniq.txt";
				
		        File trecfinmen = new File(trecfilepath);
				BufferedReader trecinreader = null;
				try
				{
					InputStreamReader trecread = new InputStreamReader(new FileInputStream(trecfinmen),"utf-8"); 
					trecinreader = new BufferedReader(trecread);
		            String tempString = null;
		         
		            while ((tempString = trecinreader.readLine())!= null) 
		            {
		            	String[] ss=new String[20];
		            	ss=tempString.split("\n");
		            //	ss[0]+=".txt";
		         //   	System.out.println(ss[0]+"\t"+ss[1]);
		            	uniqmsg.add(ss[0]);
		            }
		         //   System.out.println("email-uniq size: " + uniqmsg.size());
				}catch (IOException e) 
		        {
		            e.printStackTrace();
		        } finally 
		        {
		            if (trecinreader != null) 
		            {
		                try 
		                {
		                	trecinreader.close();
		                } catch (IOException e1) 
		                {
		                }
		            }
		        }
				
				//read in duppairs and compare
				String dfilepath = "/home/ediscovery/workspace/deduplication/data/results/ContentAttachRegFormat-duppairs.txt";
				
		        File dfinmen = new File(dfilepath);
				BufferedReader dinreader = null;
				try
				{
					InputStreamReader dread = new InputStreamReader(new FileInputStream(dfinmen),"utf-8"); 
					dinreader = new BufferedReader(dread);
		            String tempString = null;
		         
                            int readnumber =0;
		            while ((tempString = dinreader.readLine())!= null) 
		            {
                                readnumber++;
                                if(readnumber<4890)continue;
                                   
		            	String[] ss=new String[50000];
		            	ss=tempString.split("\t");
		            	dupemails.clear();
		            	
		            	int dupcount =0;
		            	//all candidate duplicate emails
		            	for(int i=0;i<ss.length;i++)
		            	{
		            		//System.out.println(ss[i]);
		            		if(uniqmsg.contains(ss[i]))
		            		{
		            			dupcount++;
                                                dupemails.add(ss[i]);
		            		}
		            		
		            	}
		            	//System.out.println("dupcount: "+dupcount);
		            	if(dupcount<2)continue;
		            	
		            	String readinput = "";
		            	BufferedReader inputreader;
		            	inputreader = new BufferedReader(new InputStreamReader(System.in));
		            	
		            	
		            	
		            	//compare the original emails
		            	for(int i=0;i<dupemails.size();i++)
		            	{
		            		System.out.print("readnumber: "+readnumber+" "+dupemails.size()+"\t"+"original email: "+dupemails.get(i));
		            		
		            		 try{
		            			 readinput = inputreader.readLine(); 
		            			} 
		            		 catch (IOException ioe){ 
		            		
		            			 System.out.println("An unexpected error occured."); 
		            			 }
		            		
		            		 //open the original email
		            		String path=tagfilepath.get(dupemails.get(i));
		            	//	System.out.println(path);
		            		
		            		
		            		File cfin = new File(path);
		            		BufferedReader cin = null;
		            		try
		         			{
		         				InputStreamReader cread = new InputStreamReader(new FileInputStream(cfin),"utf-8"); 
		         				cin = new BufferedReader(cread);
		         	            String ctempString = null;
		         	         
		         	            while ((ctempString = cin.readLine())!= null) 
		         	            {
                                                
                                             //   if(!ctempString.startsWith("Content") && !ctempString.startsWith("Attachment"))
                                             //     continue;
                                                
                                                
		         	            	System.out.println(ctempString);
		         	            }
		         	            System.out.println();
		         			}catch (IOException e) 
		         	        {
		         	            e.printStackTrace();
		         	        } finally 
		         	        {
		         	            if (cin != null) 
		         	            {
		         	                try 
		         	                {
		         	                    cin.close();
		         	                } catch (IOException e1) 
		         	                {
		         	                }
		         	            }
		         	        }
		            		
		            		
		            		
		            		
		            		
		            	}
		            	
		            	
		            	
		            }
		           
				}catch (IOException e) 
		        {
		            e.printStackTrace();
		        } finally 
		        {
		            if (dinreader != null) 
		            {
		                try 
		                {
		                    dinreader.close();
		                } catch (IOException e1) 
		                {
		                }
		            }
		        }
				
	}
	
	static void comparedupemails()
	{
		//read in original filepath
		String ofilepath = "/home/ediscovery/workspace/deduplication/data/results/originalfilepath.txt";
		
        File finmen = new File(ofilepath);
		BufferedReader inreader = null;
		try
		{
			InputStreamReader read = new InputStreamReader(new FileInputStream(finmen),"utf-8"); 
			inreader = new BufferedReader(read);
            String tempString = null;
         
            while ((tempString = inreader.readLine())!= null) 
            {
            	String[] ss=new String[20];
            	ss=tempString.split("\t");
          //  	System.out.println(ss[0]+"\t"+ss[1]);
            	originalfilepath.put(ss[0], ss[1]);
            }
           // System.out.println(originalfilepath.size());
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
		
		//read in tag emailpath
		String tfilepath = "/home/ediscovery/workspace/deduplication/data/results/tagfilepath.txt";
		
        File tfinmen = new File(tfilepath);
		BufferedReader tinreader = null;
		try
		{
			InputStreamReader tread = new InputStreamReader(new FileInputStream(tfinmen),"utf-8"); 
			tinreader = new BufferedReader(tread);
            String tempString = null;
         
            while ((tempString = tinreader.readLine())!= null) 
            {
            	String[] ss=new String[20];
            	ss=tempString.split("\t");
         //   	System.out.println(ss[0]+"\t"+ss[1]);
            	tagfilepath.put(ss[0], ss[1]);
            }
        //    System.out.println(tagfilepath.size());
		}catch (IOException e) 
        {
            e.printStackTrace();
        } finally 
        {
            if (tinreader != null) 
            {
                try 
                {
                    tinreader.close();
                } catch (IOException e1) 
                {
                }
            }
        }
		
		//read in duppairs and compare
		String dfilepath = "/home/ediscovery/workspace/deduplication/data/results/contentattach-duppairs.txt";
		
        File dfinmen = new File(dfilepath);
		BufferedReader dinreader = null;
		try
		{
			InputStreamReader dread = new InputStreamReader(new FileInputStream(dfinmen),"utf-8"); 
			dinreader = new BufferedReader(dread);
            String tempString = null;
         
            while ((tempString = dinreader.readLine())!= null) 
            {
            	String[] ss=new String[5000];
            	ss=tempString.split("\t");
            	dupemails.clear();
            	
            	Runtime runtime = Runtime.getRuntime();
            	runtime.exec("clear");
            	
            	//all candidate duplicate emails
            	for(int i=0;i<ss.length;i++)
            	{
            		dupemails.add(ss[i]);
            	}
            	
            	String readinput = "";
            	BufferedReader inputreader;
            	inputreader = new BufferedReader(new InputStreamReader(System.in));
            	
            	
            	
            	//compare then original emails
            	for(int i=0;i<dupemails.size();i++)
            	{
            		System.out.print(dupemails.size()+"\t"+"original email: "+dupemails.get(i));
            		
            		 try{
            			 readinput = inputreader.readLine(); 
            			} 
            		 catch (IOException ioe){ 
            		
            			 System.out.println("An unexpected error occured."); 
            			 }
            		
            		 //open the original email
            		String path=originalfilepath.get(dupemails.get(i));
            	//	System.out.println(path);
            		
            		
            		File cfin = new File(path);
            		BufferedReader cin = null;
            		try
         			{
         				InputStreamReader cread = new InputStreamReader(new FileInputStream(cfin),"utf-8"); 
         				cin = new BufferedReader(cread);
         	            String ctempString = null;
         	         
         	            while ((ctempString = cin.readLine())!= null) 
         	            {
         	            	System.out.println(ctempString);
         	            }
         	        //    System.out.println(tagfilepath.size());
         			}catch (IOException e) 
         	        {
         	            e.printStackTrace();
         	        } finally 
         	        {
         	            if (cin != null) 
         	            {
         	                try 
         	                {
         	                    cin.close();
         	                } catch (IOException e1) 
         	                {
         	                }
         	            }
         	        }
            		
            		
            		
            		
            		
            	}
            	
            	
            	
            }
           
		}catch (IOException e) 
        {
            e.printStackTrace();
        } finally 
        {
            if (dinreader != null) 
            {
                try 
                {
                    dinreader.close();
                } catch (IOException e1) 
                {
                }
            }
        }
		
	}
	
	static void getduppairs()
	{
		String fout="/home/ediscovery/workspace/deduplication/data/results/ContentAttachRegFormat-duppairs.txt";
		Writer Fr = null;
		try {
			Fr = new PrintWriter(  
			       new OutputStreamWriter(new FileOutputStream(fout), "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String foutuniq="/home/ediscovery/workspace/deduplication/data/results/ContentAttachRegFormat-uniq.txt";
		Writer Funiq = null;
		try {
			Funiq = new PrintWriter(  
			       new OutputStreamWriter(new FileOutputStream(foutuniq), "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String filepath = "/home/ediscovery/workspace/deduplication/data/results/ContentAttachRegFormathash.txt";
        File finmen = new File(filepath);
		BufferedReader inreader = null;
		try
		{
			InputStreamReader read = new InputStreamReader(new FileInputStream(finmen),"utf-8"); 
			inreader = new BufferedReader(read);
            String tempString = null;
            int line = 0;
            int max=0;
           
            String lasthash="";
            while ((tempString = inreader.readLine())!= null) 
            {
            //	System.out.println(tempString);
            	String[] ss=new String[20];
            	
            	ss=tempString.split("  ");
           // 	System.out.println(tempString);
            	
            	String hash=ss[0];
            	String path=ss[1];
            	path=path.substring(path.lastIndexOf("/")+1);
            	if((!hash.equals(lasthash)) )
            	{
            		line++;
            		if(dupemails.size()>1)
            		{
            			for(int i=0; i<dupemails.size();i++)
                		{
                			Fr.write(dupemails.get(i)+"\t");
                		}
                		Fr.write("\n");
                		Funiq.write(dupemails.get(0)+"\n");
            		}
            		
            		if(dupemails.size()>max)
            		{
            			max=dupemails.size();
            		}
            		dupemails.clear();
            		lasthash=hash;
            		dupemails.add(path);
            	}
            	else
            	{
            		dupemails.add(path);
            	}
            	
          
            	
            }
            double percent = (double)line/619928*100;
            System.out.println(line+"\t"+percent+"\t"+max);
            
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
        try {
			Fr.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			Fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
        	Funiq.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
        	Funiq.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readinduphash()
	{
		String filepath = "D:\\eclips\\Deduplication\\data\\duplicatehash.txt";
        File finmen = new File(filepath);
		BufferedReader inreader = null;
		try
		{
			InputStreamReader read = new InputStreamReader(new FileInputStream(finmen),"utf-8"); 
			inreader = new BufferedReader(read);
            String tempString = null;
        
            int lastdocid = 1;
            while ((tempString = inreader.readLine())!= null) 
            {
            	
            	
            	
            }
           
            
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
		
	}
	
	public static void readinemails()
	{
		/*
		//read in email names
		String filepath = "/home/ediscovery/workspace/deduplication/data/results/tagemailhash.txt";
        File finmen = new File(filepath);
		BufferedReader inreader = null;
		try
		{
			InputStreamReader read = new InputStreamReader(new FileInputStream(finmen),"utf-8"); 
			inreader = new BufferedReader(read);
            String tempString = null;
         
            
            int lastdocid = 1;
            while ((tempString = inreader.readLine())!= null) 
            {
            	String[] ss=new String[20];
            	ss=tempString.split("  ");
            	String hashid=ss[0];
            	String filename=ss[1];
            	files email = new files();
            	email.filehash=hashid;
            	email.emailname=filename;
            	
            	if(true)
        		{
            		if(allfiles.containsKey(hashid))
            		{
            			allfiles.get(hashid).add(email);
            		//	System.out.println(allfiles.get(hashid).get(allfiles.get(hashid).size()-1).filehash + "\t" +allfiles.get(hashid).get(allfiles.get(hashid).size()-1).emailname);
            		}
            		else
            		{
            			Vector<files> addinfile = new Vector<files>();
            			addinfile.add(email);
            		//	System.out.println(newfiles.size());
            			allfiles.put(hashid, addinfile);
            			System.out.println(allfiles.get(hashid).get(allfiles.get(hashid).size()-1).filehash );
            			addinfile.clear();
            		//	System.out.println(allfiles.get(hashid).get(allfiles.get(hashid).size()-1).filehash );
            			System.out.println(allfiles.get(hashid).size() );
            			
            		}
        	//		System.out.println(email.filehash+"\t"+email.filename);
        		//	allfiles.get(hashid).add(email);
        		//	System.out.println(allfiles.get(hashid).get(allfiles.get(hashid).size()-1).emailname);
        			 
        		}
            	
            	//is a email
            //	System.out.println(email.filehash+"\t"+email.filename);
            	if((email.emailname.charAt(email.emailname.length()-2)!='.')&&(email.emailname.charAt(email.emailname.length()-3)!='.')&&(email.emailname.charAt(email.emailname.length()-4)!='.'))
            	{
            	//	System.out.println(email.filehash +"\t"+ allfiles.containsKey(hashid));
            		
            		
            	}
            	
            	
            	
                
            }
            
            
            
            int dupnum=0;
            
           
            
            for(Object hashkey: allfiles.keySet())
            {
            	dupnum+=allfiles.get(hashkey).size();
            	
            	 
            	
            //	System.out.println(allfiles.get(hashkey).get(allfiles.get(hashkey).size()-2).emailname);
            	if(allfiles.get(hashkey).size()==0)continue;
            	
            	
            	for(Iterator iter=allfiles.get(hashkey).iterator(); iter.hasNext();)
            	{
            		files outfile = (files) iter.next();
            		System.out.println(hashkey+"\t"+allfiles.get(hashkey).size()+"\t"+outfile.filehash);
            	}
            }
            System.out.println(dupnum);
            
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
		*/
	}
	
	
	public static void main (String args[ ]) throws IOException{
		
	//	comparedups();
	//	comparedupemails();
		
	//	getduppairs();
		
	//	readinduphash();
		
		
	//	readinemails();
		
		
		print_file("/home/ediscovery/workspace/EM evaluation/trec4.results.input/adhoc/CategoryA","/home/ediscovery/workspace/EM evaluation/results/inputfiles.txt");
		
	//	System.out.println(filecount);
		}
	
	
	
	
	
	//print out the full path of the emails
	public static void print_file (String dir, String outputdir) throws IOException
	{
		String fout=outputdir;
    	
		BufferedWriter out = null;  
        try {  
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fout,true)));  
            
            
		
		File file = new File(dir);
		File files[] = file.listFiles();
		for(File tempFile : files)
		{
			if(tempFile.isDirectory())
			{
				
				
				print_file(tempFile.getAbsolutePath(), outputdir);
			}else{
				
				
				String filepath = tempFile.getAbsoluteFile().toString();
				
				if(true)
				{
					filecount++;
					
                                        /*
					out.write(tempFile.getName()+"\t"+tempFile.getAbsolutePath()+"\n");
					System.out.println(tempFile.getName()+"\t"+tempFile.getAbsolutePath());
                                        */
                                        
                                        if(!tempFile.getAbsolutePath().endsWith(".gz"))
                                        {
                                            out.write(tempFile.getAbsolutePath()+"\n");
                                            System.out.println("\""+tempFile.getAbsolutePath()+"\"\n"+", ");
                                        }
					
                                        
                                         
					/*
					//unzip edrm-enron-v2_rapp-b_xml.zip "text_000/*" -d ./rapp-b
					String foldername = tempFile.getName().substring(14);
					int position = foldername.lastIndexOf("_");
					foldername = foldername.substring(0, position);
					String commend = "unzip "+tempFile.getAbsolutePath()+" \"text_000/*\" -d ./"+foldername;
					out.write(commend+"\n");
					//remove the zip file
					String rmcommend = "rm "+tempFile.getName()+"\n";
					out.write(rmcommend);
                    System.out.println(commend+"\n"+rmcommend);
                    */
					
				}
				
			}
		}
		System.out.println(filecount);

       
    } catch (Exception e) {  
        e.printStackTrace();  
    } finally {  
        try {  
            out.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
        
		
	}
}