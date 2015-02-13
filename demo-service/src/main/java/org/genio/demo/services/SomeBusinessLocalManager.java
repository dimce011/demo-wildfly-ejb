package org.genio.demo.services;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;

import org.genio.demo.dto.SomeEntityDTO;
import org.genio.demo.entities.SomeEntity;
import org.genio.demo.exception.DemoBusinessException;
import org.genio.demo.filter.SomeEntityFilter;

/**
 * @author nilic
 * 
 * This is interface for CountryDTO operations.
 * 
 */
@Local
public interface SomeBusinessLocalManager extends Serializable {

	/**
	 * Returns a list of all Some Entity DTOs.
	 * 
	 * @return List<SomeEntityDTO>
	 */
	public List<SomeEntity> getAllSomeEntities();

	/**
	 * Returns a list of Some Entity DTOs by Filter.
	 * 
	 * @return List<SomeEntityDTO>
	 */
	public List<SomeEntity> getSomeEntities(SomeEntityFilter filter) throws DemoBusinessException;

	/**
	 * Returns a list of Some Entity DTOs transformation for any Collection of SomeEntities.
	 * 
	 * @param someEntities
	 * @return List<SomeEntityDTO>
	 */
	public List<SomeEntityDTO> transform(Collection<SomeEntity> someEntities);

	/**
	 * Returns Some Entity DTOs transformation from SomeEntity entity.
	 * 
	 * @param someEntity
	 * @return SomeEntityDTO
	 */
	public SomeEntityDTO transform(SomeEntity someEntity);
	
	/**
	 * Returns persistent or not SomeEntity for selected SomeEntityDTO.
	 * 
	 * @param someEntityDTO
	 * @return SomeEntity
	 */
	public SomeEntity transform(SomeEntityDTO someEntityDTO) throws DemoBusinessException;
}
