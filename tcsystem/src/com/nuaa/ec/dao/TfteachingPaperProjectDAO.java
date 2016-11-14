package com.nuaa.ec.dao;

import com.nuaa.ec.model.Teacher;
import com.nuaa.ec.model.TfteachingPaperPerformance;
import com.nuaa.ec.model.TfteachingPaperProject;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * TfteachingPaperProject entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.nuaa.ec.model.TfteachingPaperProject
 * @author MyEclipse Persistence Tools
 */
public class TfteachingPaperProjectDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TfteachingPaperProjectDAO.class);
	// property constants
	public static final String TEACH_PAPER_NAME = "teachPaperName";
	public static final String OTHER_AUTHOR_JOIN = "otherAuthorJoin";
	public static final String PROJECT_SUM_SCORE = "projectSumScore";
	public static final String SPARE_TIRE = "spareTire";
	public static final String CHECK_OUT = "checkOut";

	public void save(TfteachingPaperProject transientInstance) {
		log.debug("saving TfteachingPaperProject instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TfteachingPaperProject persistentInstance) {
		log.debug("deleting TfteachingPaperProject instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TfteachingPaperProject findById(java.lang.String id) {
		log.debug("getting TfteachingPaperProject instance with id: " + id);
		try {
			TfteachingPaperProject instance = (TfteachingPaperProject) getSession()
					.get("com.nuaa.ec.model.TfteachingPaperProject", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TfteachingPaperProject instance) {
		log.debug("finding TfteachingPaperProject instance by example");
		try {
			List results = getSession()
					.createCriteria("com.nuaa.ec.model.TfteachingPaperProject")
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
		log.debug("finding TfteachingPaperProject instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TfteachingPaperProject as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTeachPaperName(Object teachPaperName) {
		return findByProperty(TEACH_PAPER_NAME, teachPaperName);
	}

	public List findByOtherAuthorJoin(Object otherAuthorJoin) {
		return findByProperty(OTHER_AUTHOR_JOIN, otherAuthorJoin);
	}

	public List findByProjectSumScore(Object projectSumScore) {
		return findByProperty(PROJECT_SUM_SCORE, projectSumScore);
	}

	public List findBySpareTire(Object spareTire) {
		return findByProperty(SPARE_TIRE, spareTire);
	}

	public List findByCheckOut(Object checkOut) {
		return findByProperty(CHECK_OUT, checkOut);
	}

	public List findAll() {
		log.debug("finding all TfteachingPaperProject instances");
		try {
			String queryString = "from TfteachingPaperProject";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public void deleteBylogic(String projectId){
		try {
			String queryString = "update TfteachingPaperProject "
					+ "set spareTire='0' "
					+ "where spareTire='1' "
					+ "and teachPaperId=? ";
			Query queryObject = getSession().createQuery(queryString).setParameter(0, projectId);
			queryObject.executeUpdate();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public List findPaging(int currentRow,int limitRow,String condition){
		try {
			String queryString = "from TfteachingPaperProject "
					+ "where spareTire='1' "
					+condition
					+ "and tfteachingPaperRetrievalCondition.spareTire='1' ";
			Query queryObject = getSession().createQuery(queryString)
					.setFirstResult(currentRow).setMaxResults(limitRow);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public int getRows(String condition){
		try {
			String queryString = "from TfteachingPaperProject "
					+ "where spareTire='1' "
					+condition
					+ "and tfteachingPaperRetrievalCondition.spareTire='1' ";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list().size();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public TfteachingPaperProject merge(TfteachingPaperProject detachedInstance) {
		log.debug("merging TfteachingPaperProject instance");
		try {
			TfteachingPaperProject result = (TfteachingPaperProject) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TfteachingPaperProject instance) {
		log.debug("attaching dirty TfteachingPaperProject instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TfteachingPaperProject instance) {
		log.debug("attaching clean TfteachingPaperProject instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}