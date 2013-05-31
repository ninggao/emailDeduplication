package duplicateemailsdetection;

import java.io.*;
import java.lang.*;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;


public class printcommend {
	
	static TreeMap<String, String> files = new TreeMap<String, String>();
	
	
	
	 public static void reprocessemails(){
		 	String filepath = "/home/ediscovery/workspace/deduplication/data/results/originalfilepath.txt";
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
	            	//email or attachment
		        	String eoa = ss[0];
		        	eoa=eoa.substring(0, eoa.length()-4);
		        //	System.out.println(ss[0]+"\t"+eoa);
		        	int eoadot = eoa.lastIndexOf(".");
		        	if(eoadot<10)
		        	{
		        		files.put(ss[0],ss[1]);
		        	}
	            	
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
	        int caline=0;
	        //process each email
	        for(Object key: files.keySet())
	        {
	        	
	        	
	        	String emailpath = files.get(key);
		        File finemail = new File(emailpath);
				BufferedReader emailreader = null;
				
		//		System.out.println(files.get(key));
				int pzip=files.get(key).lastIndexOf("/text_000");
				String foldername = files.get(key).substring(59, pzip);
				caline++;
			//	System.out.println(caline);

				File dirfile = new File( "/home/ediscovery/workspace/deduplication/data/ContentAttach/"+foldername);
				
				if(!dirfile.exists())
				{
					dirfile.mkdir();
					System.out.println("processing email directory "+foldername);
				}
				
				
				String fout="/home/ediscovery/workspace/deduplication/data/ContentAttach/"+foldername+"/"+key;
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
				
				try
				{
					InputStreamReader read = new InputStreamReader(new FileInputStream(finemail),"utf-8"); 
					emailreader = new BufferedReader(read);
		            String tempString = null;
		            int line = 1;
		           
                    String currenttag="";
		            boolean getXSDoc = false;
		           
		            while ((tempString = emailreader.readLine())!= null) 
		            {
		            	if(tempString.length()==0)continue;
		            	
		            	else if(tempString.startsWith("X-Filename")||tempString.startsWith("X-Folder")||tempString.startsWith("X-ZLID")||tempString.startsWith("*********"))
		            	{}
		            	else if(tempString.startsWith("X-SDOC"))
		            	{
		            		getXSDoc = true;
		            	}
		            	else if(tempString.startsWith("EDRM Enron Email Data Set"))
		            	{
		            		break;
		            	}
		            	else if(tempString.startsWith("Message-ID")||tempString.startsWith("Content-Type")||tempString.startsWith("Content-Transfer-Encoding"))
		            	{}
		            	else if(tempString.startsWith("Date:") && getXSDoc==false)
		            	{
		            		currenttag="Date:";
		            		Fr.write(tempString+"\n");
		            	}
		            	else if(tempString.startsWith("From:") && getXSDoc==false)
		            	{
		            		currenttag="From:";
		            		Fr.write(tempString+"\n");
		            	}
		            	else if(tempString.startsWith("To:") && getXSDoc==false)
		            	{
		            		currenttag="To:";
		            		Fr.write(tempString+"\n");
		            	}
		            	else if(tempString.startsWith("Cc:") && getXSDoc==false)
		            	{
		            		currenttag="Cc:";
		            		Fr.write(tempString+"\n");
		            	}
		            	else if(tempString.startsWith("Subject:") && getXSDoc==false)
		            	{
		            		currenttag="Subject:";
		            		Fr.write(tempString+"\n");
		            	}
		            	else if(getXSDoc==false)
		            	{
		            		Fr.write(currenttag+" "+tempString+"\n");
		            	}
		            	else if(getXSDoc==true)
		            	{
		            		Fr.write("Content: "+tempString+"\n");
		            	}
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
	        }
			
	 }
	 
	 public static void reprocessattachments(){
		 	files.clear();
		 
		 	String filepath = "/home/ediscovery/workspace/deduplication/data/results/originalfilepath.txt";
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
	            	
	            	String eoa = ss[0];
		        	eoa=eoa.substring(0, eoa.length()-4);
		        //	System.out.println(ss[0]+"\t"+eoa);
		        	int eoadot = eoa.lastIndexOf(".");
		        	if(eoadot>10)
		        	{
		        		files.put(ss[0],ss[1]);
		        	}
	            	
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

		String lastfoldername = "null";
	        
	        //process each attachments
	        for(Object key: files.keySet())
	        {
	        	String emailpath = files.get(key);
		        File finemail = new File(emailpath);
				BufferedReader emailreader = null;
				
				String emailfilename = key.toString().substring(0, key.toString().length()-5);
				int position = emailfilename.lastIndexOf('.');
				emailfilename = emailfilename.substring(0, position);
				
				int pzip=files.get(key).lastIndexOf("/text_000");
				String foldername = files.get(key).substring(59, pzip);
			//	System.out.println(files.get(key)+"\t"+foldername);

				
				
				String fout="/home/ediscovery/workspace/deduplication/data/ContentAttach/"+foldername+"/"+emailfilename+".txt";
				
			//	System.out.println(fout);
				Writer Fr = null;
				try {
					Fr = new PrintWriter(  
					       new OutputStreamWriter(new FileOutputStream(fout, true), "utf-8"));
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try
				{
					InputStreamReader read = new InputStreamReader(new FileInputStream(finemail),"utf-8"); 
					emailreader = new BufferedReader(read);
		            String tempString = null;
		            int line = 1;
		           
		           
		            while ((tempString = emailreader.readLine())!= null) 
		            {
		            	if(tempString.length()==0)continue;
		            	
		                Fr.write("Attachment: "+tempString+"\n");
		            	
		            	line++;
		            	
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
	        }
			
	 }
	 
	 //get some parts of the emails
	 public static void segemails()
	 {
		    String filepath = "/home/ediscovery/workspace/deduplication/data/results/tagfilepath.txt";
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
	           // 	
		        	files.put(ss[0],ss[1]);
		        	
	            	
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
	        int caline=0;
	        //process each email
	        for(Object key: files.keySet())
	        {
	        	
	        	
	        	String emailpath = files.get(key);
		        File finemail = new File(emailpath);
				BufferedReader emailreader = null;
				
		//		System.out.println(files.get(key));
				int pzip=files.get(key).lastIndexOf("/");
				String foldername = files.get(key).substring(57, pzip);
				caline++;
				System.out.println(foldername);

				File dirfile = new File( "/home/ediscovery/workspace/deduplication/data/ContentAttach/"+foldername);
				
				if(!dirfile.exists())
				{
					dirfile.mkdir();
					System.out.println("processing email directory "+foldername);
				}
				
				
				String fout="/home/ediscovery/workspace/deduplication/data/ContentAttach/"+foldername+"/"+key;
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
				
				try
				{
					InputStreamReader read = new InputStreamReader(new FileInputStream(finemail),"utf-8"); 
					emailreader = new BufferedReader(read);
		            String tempString = null;
		            int line = 1;
		           
               
		            while ((tempString = emailreader.readLine())!= null) 
		            {
		            	if(tempString.length()==0)continue;
		            	
		            	else if(tempString.startsWith("Content") || tempString.startsWith("Attachment"))
		            	{
                                        tempString = tempString.replaceAll("Content:", " ");
                                        tempString = tempString.replaceAll("Attachment:", " ");
                                        tempString = tempString.toLowerCase();
                                        tempString = tempString.replaceAll("\t", " ");
                                        tempString = tempString.replaceAll("\n", " ");
                                        
                                        String[] tempss = tempString.split(" ");
                                        for(int p = 0; p < tempss.length; p++)
                                        {
                                            if(tempss[p].length()>0)
                                            {
                                                Fr.write(tempss[p]+" ");
                                            }
                                        }
                                        Fr.write("\n");
		            	}
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
	        }
	 }
	 
	 //print the md5 hash commend
	 public static void printcommends()
	 {
		    String filepath = "/home/ediscovery/workspace/deduplication/data/results/ContentAttachfilepath.txt";
	        File finmen = new File(filepath);
			BufferedReader inreader = null;
			
			String fout="/home/ediscovery/workspace/deduplication/data/results/commend.sh";
	    	
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
			
			try
			{
				InputStreamReader read = new InputStreamReader(new FileInputStream(finmen),"utf-8"); 
				inreader = new BufferedReader(read);
	            String tempString = null;
	            int line = 1;
	            
	            int lastdocid = 1;
	            
	            Fr.write("# !/bin/bash\n");
	            
	            while ((tempString = inreader.readLine())!= null) 
	            {
	            //	System.out.println(tempString);
	            	String[] ss=new String[20];
	            	/*
	            	ss=tempString.split(",");
	            	String first = ss[0];
	            	String second = ss[1];
	          //  System.out.println(first+","+second+"\n");
			
	            	if(!first.equals(second))
	            	{
	            		line++;
	            		Fr.write(first+","+second+"\n");
	            	}
			*/
	            	
	            	ss=tempString.split("\t");
	            	String filename = ss[0];
	            	
	        
	            	String fullpath = ss[1];
	            	
			/*
	            	int position=filename.length()-6;
	            	if((filename.charAt(position) == '.' )||(filename.charAt(position-1) == '.' )||(filename.charAt(position-2) == '.' ))
	            	{
	            		Fr.write(filename+"\t"+fullpath+"\n");
	            		line++;
	            	}
			*/
	            	Fr.write("md5sum "+fullpath+" >> /home/ediscovery/workspace/deduplication/data/results/ContentAttachRegFormathash.txt\n");
	            	
	            }
	            System.out.println(line);
	            
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
	    }
	 
	
	 public static void main(String[] args)
	    {
		 //	segemails();
		//	readinfiles();
		 //	reprocessemails();
		//	System.out.println("processing attachments");
		//	reprocessattachments();
		   printcommends();
		 	System.out.println("done!");
	    }
	       
}