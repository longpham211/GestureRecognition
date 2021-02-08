/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Long
 */
public class TestTimeAtMillisecond {
    public static void main(String[] args){
        test2();
        
    }
    
    public static void test1(){
        Date temp = new Date (System.currentTimeMillis());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
        System.out.println(temp);
        System.out.println(dateFormatter.format(temp));
        //System.out.println(System.currentTimeMillis());
            
    }
    
    public static void test2(){
        Calendar rightNow = Calendar.getInstance();
        long offset = rightNow.get(Calendar.ZONE_OFFSET) +    rightNow.get(Calendar.DST_OFFSET);
        long sinceMidnight = (rightNow.getTimeInMillis() + offset) %    (24 * 60 * 60 * 1000);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(dateFormatter.format(rightNow.getTime())+ "." + sinceMidnight%(60*60*1000)%(60*1000)%(1000));        
    }
    
    public static void test3(){
	long timeInMillis = System.currentTimeMillis();
		
		Calendar cal = Calendar.getInstance();
		
		cal.setTimeInMillis(timeInMillis);
		
		java.util.Date date = cal.getTime();
		
		System.out.println(date);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
            System.out.println(dateFormatter.format(date));
        }
    
    public static void test4(){
        long lDateTime = new Date().getTime();
      System.out.println("Date() - Time in milliseconds: " + lDateTime);
 
      Calendar lCDateTime = Calendar.getInstance();
      System.out.println("Calender - Time in milliseconds :" + lCDateTime.getTimeInMillis());
     
    }
}
