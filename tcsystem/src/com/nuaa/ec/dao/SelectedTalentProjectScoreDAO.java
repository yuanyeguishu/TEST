package com.nuaa.ec.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.SelectedTalentProjectScore;

/**
 	* A data access object (DAO) providing persistence and search support for SelectedTalentProjectScore entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.SelectedTalentProjectScore
  * @author MyEclipse Persistence Tools 
 */
public class SelectedTalentProjectScoreDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(SelectedTalentProjectScoreDAO.class);
		//property constants
	public static final String SCORE = "score";
	public static final String SPARE_TIRE = "spareTire";



    
    public void save(SelectedTalentProjectScore transientInstance) {
        log.debug("saving SelectedTalentProjectScore instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(SelectedTalentProjectScore persistentInstance) {
        log.debug("deleting SelectedTalentProjectScore instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public SelectedTalentProjectScore findById( java.lang.String id) {
        log.debug("getting SelectedTalentProjectScore instance with id: " + id);
        try {
            SelectedTalentProjectScore instance = (SelectedTalentProjectScore) getSession()
                    .get("com.nuaa.ec.model.SelectedTalentProjectScore", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(SelectedTalentProjectScore instance) {
        log.debug("finding SelectedTalentProjectScore instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.SelectedTalentProjectScore")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }    
    
    public List findByProperty(String propertyName, Object value) {
      log.debug("finding SelectedTalentProjectScore instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from SelectedTalentProjectScore as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByScore(Object score
	) {
		return findByProperty(SCORE, score
		);
	}
	
	public List findBySpareTire(Object spareTire
	) {
		return findByProperty(SPARE_TIRE, spareTire
		);
	}
	

	public List findAll() {
		log.debug("finding all SelectedTalentProjectScore instances");
		try {
			String queryString = "from SelectedTalentProjectScore";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public SelectedTalentProjectScore merge(SelectedTalentProjectScore detachedInstance) {
        log.debug("merging SelectedTalentProjectScore instance");
        try {
            SelectedTalentProjectScore result = (SelectedTalentProjectScore) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SelectedTalentProjectScore instance) {
        log.debug("attaching dirty SelectedTalentProjectScore instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(SelectedTalentProjectScore instance) {
        log.debug("attaching clean SelectedTalentProjectScore instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}