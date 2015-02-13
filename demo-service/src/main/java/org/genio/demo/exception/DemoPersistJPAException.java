package org.genio.demo.exception;


/**
 * This exception is thrown when expected not null parameter value is null!
 */
public class DemoPersistJPAException extends DemoJPAException {

	private static final long serialVersionUID = 3631889502036037803L;

	public DemoPersistJPAException(){
		super();
    }

    public DemoPersistJPAException(String msg){
    	super(msg);
    }

    public DemoPersistJPAException(Throwable cause){
    	super(cause);
    }
    
}