package org.genio.demo.web.faces;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.genio.demo.dto.SomeEntityDTO;
import org.genio.demo.filter.SomeEntityFilter;
import org.genio.demo.services.SomeBusinessManager;
import org.primefaces.context.RequestContext;


@ManagedBean(name="someEntities")
@ViewScoped
public class SomeEntityFaces implements Serializable {

	private static final long serialVersionUID = 5719587402051506415L;

	/**
     * Resource data
     */
    private List<SomeEntityDTO> someEntities = new ArrayList<SomeEntityDTO>();

    /**
	 * Utility for logging errors
	 */
	private String stackTrace = null;
	
	/**
	 * Search fieldset
	 */
	private SomeEntityFilter filter = new SomeEntityFilter();
	
	
	@PostConstruct
	public void init() {
/*		try{ 
			loadOperators();
		} catch (Exception e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error loading page " + e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, msg);			
			e.printStackTrace();
		}
*/	}
	
	/**
	 * Resource data loaders
	 * @throws NamingException 
	 */
/*	private void loadOperators() throws NamingException {
		operatorCodes = new ArrayList<String>();
		List<OperatorDTO> operators = lookupLocationService().getAllOperators();
		for (OperatorDTO operator : operators) {
			operatorCodes.add(operator.getCode());
		}
	}
*/
	
	/**
	 * Methods for auto-complete components 
	 */
/*    public List<String> completeOperator(String query) {  
    	if (StringUtils.isEmpty(query)) {
			return operatorCodes;
		} 
	    List<String> suggestions = new ArrayList<String>();
	    for(String mno : operatorCodes) {  
			if(mno.toLowerCase().contains(query.toLowerCase())) { 
				suggestions.add(mno);
			}
		}          
		return suggestions;		
	}
*/    
	/**
	 * Actions
	 */
    public void fetchSomeEntities() {
    	try {
    		clearStackTrace();
			someEntities = lookupSomeBusinessManager().getSomeEntityDTOs(filter);		
		} catch (Exception e) {
			showStackTrace(e);
		}
    }
    
    public void resetInput() {
    	filter.setCode(null);
    	filter.setName(null);
    	filter.setDescription(null);
    }
    
	/**
	 * Utils for error logging 
	 */
	private void showStackTrace(Throwable e) {
		e.printStackTrace();
	    StringWriter stringWriter = new StringWriter();
	    e.printStackTrace(new PrintWriter(stringWriter, true));
	    stackTrace += stringWriter.toString();
	    RequestContext.getCurrentInstance().execute("showStackTrace()");
	}
	
	private void clearStackTrace() {
		stackTrace = "";
	}
	
	/**
	 * Getters and setters
	 */	
	public SomeEntityFilter getFilter() {
		return filter;
	}
	public void setFilter(SomeEntityFilter filter) {
		this.filter = filter;
	}

	public List<SomeEntityDTO> getSomeEntities() {
		return someEntities;
	}

	public void setSomeEntities(List<SomeEntityDTO> someEntities) {
		this.someEntities = someEntities;
	}

	public String getStackTrace() {
		return stackTrace;
	}
	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}		
	
	/**
	 * Remote loaders
	 * @throws NamingException 
	 */
	private SomeBusinessManager lookupSomeBusinessManager() throws NamingException {

		//We look into the same server, and war is packed in the same ear as ejb project.
		//No separate jboss-ejb-client.properties file/settings!!!
		Properties props = new Properties();
		props.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		
		//Application name, if not specified - the name of ear archive by default.
		final String appName = "demo";
		//Module name, if not specified - the name of ejb archive by default.
		final String moduleName = "demo-service";
		//We did not set any.
		final String distinctName = "";
		//Internal - Our - convention.
		final String beanName = SomeBusinessManager.class.getSimpleName();
		//This is Remote Interface - View Class Name.
		final String viewClassName = SomeBusinessManager.class.getName();
		
		String jndiValue = "ejb:" + appName + "/" + moduleName
				+ "/" + distinctName + "/" + beanName + "!" + viewClassName;
		
		System.out.println("jndiValue: " + jndiValue);

		Context context = new InitialContext(props);
		SomeBusinessManager someBusinessManager = (SomeBusinessManager)context.lookup(jndiValue);
		
		return someBusinessManager;
	}
	
}