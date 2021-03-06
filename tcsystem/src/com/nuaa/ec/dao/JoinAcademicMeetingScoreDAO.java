package com.nuaa.ec.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.JoinAcademicMeetingScore;
import com.nuaa.ec.model.MeetingType;
import com.nuaa.ec.model.PaperRetrievalCondition;

/**
 	* A data access object (DAO) providing persistence and search support for JoinAcademicMeetingScore entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.JoinAcademicMeetingScore
  * @author MyEclipse Persistence Tools 
 */
public class JoinAcademicMeetingScoreDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(JoinAcademicMeetingScoreDAO.class);
		//property constants
	public static final String SCORE = "score";
	public static final String SPARE_TIRE = "spareTire";


    
    public void save(JoinAcademicMeetingScore transientInstance) {
        log.debug("saving JoinAcademicMeetingScore instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(JoinAcademicMeetingScore persistentInstance) {
        log.debug("deleting JoinAcademicMeetingScore instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public JoinAcademicMeetingScore findById( java.lang.String id) {
        log.debug("getting JoinAcademicMeetingScore instance with id: " + id);
        try {
            JoinAcademicMeetingScore instance = (JoinAcademicMeetingScore) getSession()
                    .get("com.nuaa.ec.model.JoinAcademicMeetingScore", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(JoinAcademicMeetingScore instance) {
        log.debug("finding JoinAcademicMeetingScore instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.JoinAcademicMeetingScore")
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
      log.debug("finding JoinAcademicMeetingScore instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from JoinAcademicMeetingScore as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

    public JoinAcademicMeetingScore findByMeetTypeAndPaperretri(MeetingType mt,String prcId){
    	try {
            String queryString = "from JoinAcademicMeetingScore "
            		+ "where meetingType=? "
            		+ "and meetingType.spareTire='1' "
            		+ "and paperRetrievalCondition.prconditionId=? "
            		+ "and spareTire='1' ";
            Query queryObject = getSession().createQuery(queryString);
	   		 queryObject.setParameter(0, mt);
	   		 queryObject.setParameter(1, prcId);
	   		 if(queryObject.list().size()>0){
	   			return (JoinAcademicMeetingScore) queryObject.list().get(0);
	   		 }else{
	   			 return null;
	   		 }
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
	
	public int findByMeetType(MeetingType mt){
		return findByProperty("meetingType", mt).size();
	}
	
	public List findBySpareTire(Object spareTire
	) {
		return findByProperty(SPARE_TIRE, spareTire
		);
	}
	

	public List findAll() {
		log.debug("finding all JoinAcademicMeetingScore instances");
		try {
			String queryString = "from JoinAcademicMeetingScore where spareTire='1' ";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public JoinAcademicMeetingScore merge(JoinAcademicMeetingScore detachedInstance) {
        log.debug("merging JoinAcademicMeetingScore instance");
        try {
            JoinAcademicMeetingScore result = (JoinAcademicMeetingScore) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(JoinAcademicMeetingScore instance) {
        log.debug("attaching dirty JoinAcademicMeetingScore instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(JoinAcademicMeetingScore instance) {
        log.debug("attaching clean JoinAcademicMeetingScore instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}