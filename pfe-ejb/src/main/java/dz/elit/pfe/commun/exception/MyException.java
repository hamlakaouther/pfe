/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dz.elit.pfe.commun.exception;

import java.util.List;
import jakarta.ejb.ApplicationException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author leghettas.rabah
 */
@ApplicationException(rollback=true)
public class MyException extends Exception{   
        
    private @Getter @Setter String message;
    private @Getter @Setter String code;
    private @Getter @Setter List<String> errors;

    public MyException(String message) {
        this.message = message;
    }
    
    public MyException(List<String> errors) {
        this.errors = errors;
    }

    public MyException(String message, Throwable cause) {
        super(message, cause);
    }   

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    public String transformeListError(List<String> errors) {
        String message = "";
        for (String error : errors) {
            if (message.trim().equals("")) {
                message = "- " + error;
            } else {
                message = message + "\n" + "- " + error;
            }
        }
        return message;
    }
    
    public MyException(String message, String code) {
        this.message = message;
        this.code = code;
    }
}