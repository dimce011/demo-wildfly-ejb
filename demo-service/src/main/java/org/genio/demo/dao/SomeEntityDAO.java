package org.genio.demo.dao;

import java.io.Serializable;

import javax.persistence.LockModeType;

import org.genio.demo.entities.SomeEntity;
import org.genio.demo.exception.DemoNullParamJPAException;
import org.genio.demo.exception.DemoPersistJPAException;
import org.genio.demo.exception.DemoRemoveJPAException;

/**
 * @author dimce
 * This is interface for SomeEntity CRUD operations. 
 */
public interface SomeEntityDAO extends Serializable {

	   /**
     * If id is null then Creates Entity object, otherwise update.
     * 
     * @param entity
     * @return Entity.
     * @throws NullParamJPAException, PersistJPAException
     */
	public SomeEntity set(SomeEntity entity) throws DemoNullParamJPAException, DemoPersistJPAException;

	/**
     * Returns Entity object for requested primary key id.
     * 
     * @param id
     * @return Entity.
     */
	public SomeEntity get(Integer id);

	/**
	 * Returns Entity object for requested primary key id with Lock!
	 * @param id
	 * @param lockType
	 * @return
	 */
	public SomeEntity get(Integer id, LockModeType lockType);

	/**
     * Returns SomeEntity object for unique code.
     * 
     * @param code
     * @return SomeEntity.
     */
	public SomeEntity get(String code);
	
    /**
     * Deletes the object in database
     * 
     * @param Entity
     * @throws NullParamJPAException, RemoveJPAException
     */
	public void remove(SomeEntity entity) throws DemoNullParamJPAException, DemoRemoveJPAException;

}
