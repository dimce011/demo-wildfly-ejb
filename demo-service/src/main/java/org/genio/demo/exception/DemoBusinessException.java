package org.genio.demo.exception;

import javax.ejb.ApplicationException;

/**
 * This is class that must be thrown by local code!
 */
@ApplicationException (rollback=true)
public class DemoBusinessException extends Exception {

	private static final long serialVersionUID = 1468439517055329872L;

	public DemoBusinessException(){
		super();
    }

    public DemoBusinessException(String msg){
    	super(msg);
    }

    public DemoBusinessException(Throwable cause){
    	super(cause);
    }
    
}