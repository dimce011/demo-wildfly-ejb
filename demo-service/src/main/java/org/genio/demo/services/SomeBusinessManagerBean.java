package org.genio.demo.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.genio.demo.dao.SomeEntityDAO;
import org.genio.demo.dto.SomeEntityDTO;
import org.genio.demo.entities.SomeEntity;
import org.genio.demo.entities.SomeEntity_;
import org.genio.demo.exception.DemoBusinessException;
import org.genio.demo.exception.DemoJPAException;
import org.genio.demo.filter.SomeEntityFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name="SomeBusinessManager")
public class SomeBusinessManagerBean implements SomeBusinessManager, SomeBusinessLocalManager {

	private static final long serialVersionUID = -6714540747530727231L;

	final static Logger log = LoggerFactory.getLogger(SomeBusinessManagerBean.class);

	@PersistenceContext(unitName = "demo")
	public EntityManager em;

	@EJB
	public SomeEntityDAO someEntityDAO;

	public SomeBusinessManagerBean() {
		super();
		log.info("SomeBusinessManagerBean Called!");
	}
	
	@Override
	public SomeEntityDTO getSomeEntityDTO(String code) throws DemoBusinessException {
		if (code == null || code.trim().length() == 0) {
			log.error("Code is required!");
			throw new DemoBusinessException("Code is required!");
		}
		SomeEntity someEntity = someEntityDAO.get(code);
		SomeEntityDTO someEntityDTO = transform(someEntity);
		return someEntityDTO;
	}
	
	@Override
	public List<SomeEntity> getAllSomeEntities() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SomeEntity> cq = cb.createQuery(SomeEntity.class);
		Root<SomeEntity> seRoot = cq.from(SomeEntity.class);
		cq.select(seRoot);
		TypedQuery<SomeEntity> typeQuery = em.createQuery(cq);
		List<SomeEntity> allSomeEntities = typeQuery.getResultList();
		return allSomeEntities;
	}

	@Override
	public List<SomeEntityDTO> getAllSomeEntityDTOs() {
		log.info("SomeBusinessManagerBean.getAllSomeEntityDTOs Called!");
		List<SomeEntityDTO> allSomeEntityDTOs = transform(getAllSomeEntities());
		
		return allSomeEntityDTOs;
	}
	
	@Override
	public List<SomeEntity> getSomeEntities(SomeEntityFilter filter) throws DemoBusinessException {
		
		if (filter == null) {
			log.error("Filter is required!");
			throw new DemoBusinessException("Filter is required!");
		}
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<SomeEntity> cq = cb.createQuery(SomeEntity.class);
		Root<SomeEntity> seRoot = cq.from(SomeEntity.class);
		cq.select(seRoot);
		
		List<Predicate> restrictionList = new ArrayList<Predicate>();

		if (filter.getCode() != null && filter.getCode().trim().length() != 0) {
			restrictionList.add(cb.equal(seRoot.get(SomeEntity_.code), filter.getCode()));
		}
		if (filter.getName() != null && filter.getName().trim().length() != 0) {
			restrictionList.add(cb.like(seRoot.get(SomeEntity_.name), "%" + filter.getName() + "%"));
		}
		if (filter.getDescription() != null && filter.getDescription().trim().length() != 0) {
			restrictionList.add(cb.like(seRoot.get(SomeEntity_.description), "%" + filter.getDescription() + "%"));
		}
		
		Predicate[] restrictions = new Predicate[restrictionList.size()];
		restrictions = restrictionList.toArray(restrictions);
		cq.where(restrictions);
		
		TypedQuery<SomeEntity> typeQuery = em.createQuery(cq);
		List<SomeEntity> someEntities = typeQuery.getResultList();
		return someEntities;
	}

	@Override
	public List<SomeEntityDTO> getSomeEntityDTOs(SomeEntityFilter filter) throws DemoBusinessException {

		List<SomeEntityDTO> someEntityDTOs = transform(getSomeEntities(filter));
		
		return someEntityDTOs;
		
	}

	@Override
	public List<SomeEntityDTO> transform(Collection<SomeEntity> someEntities) {
		
		List<SomeEntityDTO> someEntityDTOs = null;
		if (someEntities != null) {
			someEntityDTOs = new ArrayList<SomeEntityDTO>();
			for (SomeEntity someEntity : someEntities) {
				SomeEntityDTO someEntityDTO = transform(someEntity);
				someEntityDTOs.add(someEntityDTO);
			}
		}
		return someEntityDTOs;
	}

	@Override
	public SomeEntityDTO transform(SomeEntity someEntity) {
		SomeEntityDTO someEntityDTO = null;
		if (someEntity != null) {
			someEntityDTO = new SomeEntityDTO();
			someEntityDTO.setId(someEntity.getId());
			someEntityDTO.setCode(someEntity.getCode());
			someEntityDTO.setName(someEntity.getName());
			someEntityDTO.setDescription(someEntity.getDescription());
		}
		return someEntityDTO;
	}

	@Override
	public SomeEntityDTO setSomeEntityDTO(SomeEntityDTO someEntityDTO) throws DemoBusinessException {
		if (someEntityDTO == null) {
			log.error("SomeEntityDTO is required!");
			throw new DemoBusinessException("SomeEntityDTO is required!");
		}
		SomeEntity someEntity = transform(someEntityDTO);
		someEntityDTO = transform(someEntity);
		return someEntityDTO;
	}

	@Override
	public void removeSomeEntity(String code) throws DemoBusinessException {
		if (code == null || code.trim().length() == 0) {
			log.error("Code is required!");
			throw new DemoBusinessException("Code is required!");
		}
		SomeEntity someEntity = someEntityDAO.get(code);
		if (someEntity == null) {
			log.error("Not existing Some Entity! Code: {}", code);
			throw new DemoBusinessException("Not existing Some Entity!");
		}
		try {
			someEntityDAO.remove(someEntity);
		} catch (DemoJPAException e) {
			log.error("Error removing Some Entity!", e);
			throw new DemoBusinessException("Error removing Some Entity!");
		}
		
	}
	
	@Override
	public SomeEntity transform(SomeEntityDTO someEntityDTO) throws DemoBusinessException {
		SomeEntity someEntity = null;
		if (someEntityDTO != null) {
			Integer id = someEntityDTO.getId();
			if (id != null) {
				someEntity = someEntityDAO.get(id);
				if (someEntity == null) {
					log.error("Not existing Some Entity! Id: {}", id);
					throw new DemoBusinessException("Not existing Some Entity! Id: " + id);
				}
			}
			someEntity.setCode(someEntityDTO.getCode());
			someEntity.setName(someEntityDTO.getName());
			someEntity.setDescription(someEntityDTO.getDescription());
		}
		return someEntity;
	}
	
}
