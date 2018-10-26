/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Other;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import net.lingala.zip4j.core.*;
import net.lingala.zip4j.model.*;
import net.lingala.zip4j.util.*;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author Aiglu
 */
public class Zip {
    public static File archive(String pass, File f) throws FileNotFoundException, IOException, ZipException{
        String s = new Date().toString();
        Integer h = s.hashCode();        
        ZipFile zip = new ZipFile("tmp//reestr" + h.toString() + ".zip");
        ZipParameters parameters = new ZipParameters();
        parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);  
        parameters.setEncryptFiles(true);
        parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
        parameters.setPassword(pass); 
        zip.addFile(f, parameters);
        return zip.getFile();
    }
}
