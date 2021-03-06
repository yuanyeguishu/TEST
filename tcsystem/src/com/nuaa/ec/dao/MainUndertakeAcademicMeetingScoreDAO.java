package com.nuaa.ec.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.MainUndertakeAcademicMeetingPlace;
import com.nuaa.ec.model.MainUndertakeAcademicMeetingScore;
import com.nuaa.ec.model.MainUndertakeAcademicMeetingType;

/**
 	* A data access object (DAO) providing persistence and search support for MainUndertakeAcademicMeetingScore entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.MainUndertakeAcademicMeetingScore
  * @author MyEclipse Persistence Tools 
 */
public class MainUndertakeAcademicMeetingScoreDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(MainUndertakeAcademicMeetingScoreDAO.class);
		//property constants
	public static final String SCORE = "score";
	public static final String SPARE_TIRE = "spareTire";



    
    public void save(MainUndertakeAcademicMeetingScore transientInstance) {
        log.debug("saving MainUndertakeAcademicMeetingScore instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MainUndertakeAcademicMeetingScore persistentInstance) {
        log.debug("deleting MainUndertakeAcademicMeetingScore instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MainUndertakeAcademicMeetingScore findById( java.lang.String id) {
        log.debug("getting MainUndertakeAcademicMeetingScore instance with id: " + id);
        try {
            MainUndertakeAcademicMeetingScore instance = (MainUndertakeAcademicMeetingScore) getSession()
                    .get("com.nuaa.ec.model.MainUndertakeAcademicMeetingScore", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public MainUndertakeAcademicMeetingScore findByscoreCondition(MainUndertakeAcademicMeetingPlace mp,MainUndertakeAcademicMeetingType mt){
    	 try {
             String queryString = "from MainUndertakeAcademicMeetingScore where mainUndertakeAcademicMeetingPlace=? "
             		+ "and mainUndertakeAcademicMeetingType=? "
             		+ "and spareTire='1' "
             		+ "and mainUndertakeAcademicMeetingPlace.spareTire='1' "
             		+ "and mainUndertakeAcademicMeetingType.spareTire='1' ";
             Query queryObject = getSession().createQuery(queryString).setParameter(0, mp);
    		 queryObject.setParameter(1, mt);
    		 if(queryObject.list().size()>0){
    			 return (MainUndertakeAcademicMeetingScore) queryObject.list().get(0);
    		 }else return null;
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    public List findByExample(MainUndertakeAcademicMeetingScore instance) {
        log.debug("finding MainUndertakeAcademicMeetingScore instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.MainUndertakeAcademicMeetingScore")
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
      log.debug("finding MainUndertakeAcademicMeetingScore instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MainUndertakeAcademicMeetingScore as model where model." 
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
		log.debug("finding all MainUndertakeAcademicMeetingScore instances");
		try {
			String queryString = "from MainUndertakeAcademicMeetingScore where spareTire='1' ";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public MainUndertakeAcademicMeetingScore merge(MainUndertakeAcademicMeetingScore detachedInstance) {
        log.debug("merging MainUndertakeAcademicMeetingScore instance");
        try {
            MainUndertakeAcademicMeetingScore result = (MainUndertakeAcademicMeetingScore) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MainUndertakeAcademicMeetingScore instance) {
        log.debug("attaching dirty MainUndertakeAcademicMeetingScore instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MainUndertakeAcademicMeetingScore instance) {
        log.debug("attaching clean MainUndertakeAcademicMeetingScore instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}