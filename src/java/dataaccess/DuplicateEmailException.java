
package dataaccess;

/**
 *
 * @author 734972
 */
public class DuplicateEmailException extends Exception{
    
    public DuplicateEmailException(){
    } 
    
    public DuplicateEmailException(String message){
        super(message);
    } 
    
}
