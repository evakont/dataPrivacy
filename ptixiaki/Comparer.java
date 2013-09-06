package ptixiaki;
import java.util.Comparator;

/**
 * 
 */

/**
 * @author aliki
 * @vesion 
 */
public class Comparer implements Comparator{

    public int compare(Object o1, Object o2) throws ClassCastException{
	 int i1 = ((Node)o1).return_supp();
         int i2 = ((Node)o2).return_supp();
         if( i1 > i2 )
             return 1;
         else if( i1 < i2 )
             return -1;
         else
             return 0;

    }
        	
}
