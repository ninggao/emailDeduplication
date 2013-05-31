/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicateemailsdetection;

import java.io.IOException;

/**
 *
 * @author ediscovery
 */
public class DuplicateEmailsDetection {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Email compareDupPairs = new Email();
      //  compareDupPairs.getduppairs();
     
        compareDupPairs.print_file("C:\\Users\\ning\\Dropbox\\courses\\Computational Liguistics II\\week 3\\convote_v1.1\\data_stage_three","C:\\Users\\ning\\Dropbox\\courses\\Computational Liguistics II\\week 3\\convote_v1.1\\filelist.txt");
        
     //   printcommend test = new printcommend();
      //  test.printcommends();
    }
}
