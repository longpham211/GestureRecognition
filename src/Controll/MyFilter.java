/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Controll;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Long
 */
public class MyFilter implements FileFilter{
    String fileName;
    @Override
    public boolean accept(File file)
    {
        fileName = file.getName().toLowerCase();
        for(int i= 0; i< Model.DefaultParameterInterface.imageType.length; i++)
        if(fileName.endsWith(Model.DefaultParameterInterface.imageType[i]))
            return true;
        return false;
     }       
}
