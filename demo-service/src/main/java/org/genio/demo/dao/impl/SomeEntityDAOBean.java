package org.genio.demo.dao.impl;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.genio.demo.dao.SomeEntityDAO;
import org.genio.demo.entities.SomeEntity;
import org.genio.demo.entities.SomeEntity_;
import org.genio.demo.exception.DemoNullParamJPAException;
import org.genio.demo.exception.DemoPersistJPAException;
import org.genio.demo.exception.DemoRemoveJPAException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author nilic
 */
@Stateless
@Local(SomeEntityDAO.class)
public class SomeEntityDAOBean implements SomeEntityDAO {

	private static final long serialVersionUID = -2512352006501576617L;

	final Logger log = LoggerFactory.getLogger(SomeEntityDAOBean.class);
	
	@PersistenceContext(unitName="demo")
	public EntityManager em;

	/**
	 * Default constructor.
	 */
	public SomeEntityDAOBean() {
		super();
	}
	
	@Override
    public SomeEntity set(SomeEntity entity) throws DemoNullParamJPAException, DemoPersistJPAException {
		if (entity == null) {
			throw new DemoNullParamJPAException("Entity is required!");
		}
		try {
			if (entity.getId() == null) {
				em.persist(entity);
			} else {
				entity = em.merge(entity);
			}
			return entity;
		} catch (Exception ex) {
			throw new DemoPersistJPAException(ex);
		}
    }

    @Override
    public SomeEntity get(Integer id) {
    	SomeEntity entity = em.find(SomeEntity.class, id);
    	
    	return entity;
    }

    @Override
    public SomeEntity get(Integer id, LockModeType lockType) {
    	SomeEntity entity = em.find(SomeEntity.class, id, lockType);
    	
    	return entity;
    }

    @Override
    public SomeEntity get(String code) {
    	if(code == null || code.trim().length() == 0) {
    		log.error("code is required!");
    		return null;
    	}
    	
    	CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SomeEntity> cqSrv = cb.createQuery(SomeEntity.class);
        Root<SomeEntity> rtSrv = cqSrv.from(SomeEntity.class);
        cqSrv.select(rtSrv);
        Predicate wherePart = cb.equal(rtSrv.get(SomeEntity_.code), code);
        cqSrv.where(wherePart);
        TypedQuery<SomeEntity> typeQuery = em.createQuery(cqSrv);
        List<SomeEntity> objects = typeQuery.getResultList();
        SomeEntity object = null;
        if (objects!=null && objects.size()>0) {
        	object = objects.get(0);
        }
    	return object;
    }
    
    @Override
    public void remove(SomeEntity entity) throws DemoNullParamJPAException, DemoRemoveJPAException {
		if (entity == null) {
			throw new DemoNullParamJPAException("Entity is required!");
		}
    	try {
    		em.remove(entity);
    	} catch (Exception ex) {
    		throw new DemoRemoveJPAException(ex);
    	}
    }

}
