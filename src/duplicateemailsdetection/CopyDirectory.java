package duplicateemailsdetection;

import java.io.*;  
 
public class CopyDirectory {  
    // original dir 
    static String url1 = "D:/eclips/Deduplication/data/edrm-enron-v2_allen-p_xml.zip";  
    // target dir 
    static String url2 = "D:/eclips/Deduplication/data/copy-edrm-enron-v2_allen-p_xml.zip";  
    public static void main(String args[]) throws IOException {  
        // build the target dir  
        (new File(url2)).mkdirs();  
        // get the dir or category from the original dir  
        File[] file = (new File(url1)).listFiles();  
        for (int i = 0; i < file.length; i++) {  
            if (file[i].isFile()) {  
                // copy the files 
            	
            	File newfile = new File(url2+file[i].getName());
            	if(!newfile.exists())
            	{
            		System.out.println("not exist");
            		copyFile(file[i],new File(url2+file[i].getName())); 
            	}
            }  
            if (file[i].isDirectory()) {  
                // copy the directory   
                String sourceDir=url1+File.separator+file[i].getName();  
                String targetDir=url2+File.separator+file[i].getName();  
                if(!sourceDir.contains("native"))
                {
                	copyDirectiory(sourceDir, targetDir); 
                }
                 
            }  
        }  
    }  
// copy the files   
public static void copyFile(File sourceFile,File targetFile)   
	throws IOException{  
	
	    if(sourceFile.getAbsolutePath().contains("text_000") && !targetFile.exists())
	    {
	    	FileInputStream input = new FileInputStream(sourceFile);  
	        BufferedInputStream inBuff=new BufferedInputStream(input);  
	  
	           
	        FileOutputStream output = new FileOutputStream(targetFile);  
	        BufferedOutputStream outBuff=new BufferedOutputStream(output);  
	          
	           
	        byte[] b = new byte[1024 * 5];  
	        int len;  
	        while ((len =inBuff.read(b)) != -1) {  
	            outBuff.write(b, 0, len);  
	        }  
	           
	        outBuff.flush();  
	          
	           
	        inBuff.close();  
	        outBuff.close();  
	        output.close();  
	        input.close();  
	    }
        
    }  
    // copy the dir   
    public static void copyDirectiory(String sourceDir, String targetDir)  
            throws IOException {
    	
    	System.out.println("copying file "+sourceDir);
           
        (new File(targetDir)).mkdirs();  
          
        File[] file = (new File(sourceDir)).listFiles();  
        for (int i = 0; i < file.length; i++) 
        {  
            if (file[i].isFile()) {  
                   
                File sourceFile=file[i];  
                   
               File targetFile=new File(new File(targetDir).getAbsolutePath() +File.separator+file[i].getName());  
                copyFile(sourceFile,targetFile);  
            }  
            if (file[i].isDirectory()) {  
                   
                String dir1=sourceDir + "/" + file[i].getName();  
                  
                String dir2=targetDir + "/"+ file[i].getName();  
                copyDirectiory(dir1, dir2);  
            }  
        }  
        
    }  
}