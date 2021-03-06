package com.nuaa.ec.dao;

import com.nuaa.ec.model.TftextbookConstructionTblevel;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TftextbookConstructionTblevel entities. Transaction control of the save(),
 * update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.nuaa.ec.model.TftextbookConstructionTblevel
 * @author MyEclipse Persistence Tools
 */
public class TftextbookConstructionTblevelDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TftextbookConstructionTblevelDAO.class);
	// property constants
	public static final String TBLEVEL = "tblevel";
	public static final String SCORE = "score";
	public static final String REFORM_RESEARCH_ID = "reformResearchId";
	public static final String SPARE_TIRE = "spareTire";

	public void save(TftextbookConstructionTblevel transientInstance) {
		log.debug("saving TftextbookConstructionTblevel instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TftextbookConstructionTblevel persistentInstance) {
		log.debug("deleting TftextbookConstructionTblevel instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TftextbookConstructionTblevel findById(java.lang.String id) {
		log.debug("getting TftextbookConstructionTblevel instance with id: "
				+ id);
		try {
			TftextbookConstructionTblevel instance = (TftextbookConstructionTblevel) getSession()
					.get("com.nuaa.ec.model.TftextbookConstructionTblevel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TftextbookConstructionTblevel instance) {
		log.debug("finding TftextbookConstructionTblevel instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.nuaa.ec.model.TftextbookConstructionTblevel")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TftextbookConstructionTblevel instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TftextbookConstructionTblevel as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTblevel(Object tblevel) {
		return findByProperty(TBLEVEL, tblevel);
	}

	public List findByScore(Object score) {
		return findByProperty(SCORE, score);
	}

	public List findByReformResearchId(Object reformResearchId) {
		return findByProperty(REFORM_RESEARCH_ID, reformResearchId);
	}

	public List findBySpareTire(Object spareTire) {
		return findByProperty(SPARE_TIRE, spareTire);
	}

	public List findAll() {
		log.debug("finding all TftextbookConstructionTblevel instances");
		try {
			String queryString = "from TftextbookConstructionTblevel "
					+ "where spareTire='1' ";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TftextbookConstructionTblevel merge(
			TftextbookConstructionTblevel detachedInstance) {
		log.debug("merging TftextbookConstructionTblevel instance");
		try {
			TftextbookConstructionTblevel result = (TftextbookConstructionTblevel) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TftextbookConstructionTblevel instance) {
		log.debug("attaching dirty TftextbookConstructionTblevel instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TftextbookConstructionTblevel instance) {
		log.debug("attaching clean TftextbookConstructionTblevel instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}