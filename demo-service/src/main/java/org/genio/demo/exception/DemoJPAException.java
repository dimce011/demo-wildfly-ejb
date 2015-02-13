package org.genio.demo.exception;

import javax.ejb.ApplicationException;

/**
 * This is abstract class that must be inherited for JPA exceptions thrown by local code!
 */
@ApplicationException (rollback=true)
public abstract class DemoJPAException extends Exception {

	private static final long serialVersionUID = -2316207358169297381L;

	public DemoJPAException(){
		super();
    }

    public DemoJPAException(String msg){
    	super(msg);
    }

    public DemoJPAException(Throwable cause){
    	super(cause);
    }
    
}