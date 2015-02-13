package org.genio.demo.exception;


/**
 * This exception is thrown when expected not null parameter value is null!
 */
public class DemoRemoveJPAException extends DemoJPAException {

	private static final long serialVersionUID = 5748094144131855635L;

	public DemoRemoveJPAException(){
		super();
    }

    public DemoRemoveJPAException(String msg){
    	super(msg);
    }

    public DemoRemoveJPAException(Throwable cause){
    	super(cause);
    }
    
}