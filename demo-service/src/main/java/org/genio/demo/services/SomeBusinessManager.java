package org.genio.demo.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import org.genio.demo.dto.SomeEntityDTO;
import org.genio.demo.exception.DemoBusinessException;
import org.genio.demo.filter.SomeEntityFilter;

/**
 * @author dimce
 * 
 * This is remote interface for SomeEntityDTO operations.
 * 
 */
@Remote
public interface SomeBusinessManager extends Serializable {
	
	/**
	 * Returns Some Entity DTO by native key Code.
	 * 
	 * @param code
	 * @return SomeEntityDTO
	 * @throws DemoBusinessException
	 */
	public SomeEntityDTO getSomeEntityDTO(String code) throws DemoBusinessException;
	
	/**
	 * Returns a list of all Some Entity DTOs.
	 * 
	 * @return List<SomeEntityDTO>
	 */
	public List<SomeEntityDTO> getAllSomeEntityDTOs();

	/**
	 * Returns a list of Some Entity DTOs by Filter.
	 * 
	 * @return List<SomeEntityDTO>
	 */
	public List<SomeEntityDTO> getSomeEntityDTOs(SomeEntityFilter filter) throws DemoBusinessException;

	/**
	 * Method for Insert or Update of SomeEntity
	 * 
	 * @param someEntityDTO
	 * @return
	 * @throws DemoBusinessException
	 */
	public SomeEntityDTO setSomeEntityDTO(SomeEntityDTO someEntityDTO) throws DemoBusinessException;
	
	/**
	 * Method for Delete of SomeEntity.
	 * 
	 * @param code
	 * @throws DemoBusinessException
	 */
	public void removeSomeEntity(String code) throws DemoBusinessException;
	
}
