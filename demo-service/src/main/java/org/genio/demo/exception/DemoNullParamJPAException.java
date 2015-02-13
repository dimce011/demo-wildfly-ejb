package org.genio.demo.exception;


/**
 * This exception is thrown when expected not null parameter value is null!
 */
public class DemoNullParamJPAException extends DemoJPAException {

	private static final long serialVersionUID = 7701871871264483263L;

	public DemoNullParamJPAException(){
		super();
    }

    public DemoNullParamJPAException(String msg){
    	super(msg);
    }

    public DemoNullParamJPAException(Throwable cause){
    	super(cause);
    }
    
}