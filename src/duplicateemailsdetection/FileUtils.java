package duplicateemailsdetection;



import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class FileUtils
{
    List<File> list = new ArrayList<File>();

   
    public List<File> visitAll(File root)
    {
        File[] dirs = root.listFiles();
        if (dirs != null)
        {
            for (int i = 0; i < dirs.length; i++)
            {
                if (dirs[i].isDirectory())
                {
                    System.out.println("name:" + dirs[i].getPath());
                    list.add(dirs[i]);
                }
                visitAll(dirs[i]);
            }
        }
        return list;
    }
    
    public void removeNullFile(List<File> list)
    {
        for (int i = 0; i < list.size(); i++)
        {
            File temp = list.get(i);
          
            if (temp.isDirectory() && temp.listFiles().length <= 0)
            {
                temp.delete();
            }
        }
    }


    /**
     * @param args
     */
    public static void main(String[] args)
    {
        FileUtils m = new FileUtils();
        List<File> list = m.visitAll(new File("D:/eclips/Deduplication/data"));
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++)
        {
            System.out.println(list.get(i).getPath());
        }
        m.removeNullFile(list);
        System.out.println("ok");
    }

}