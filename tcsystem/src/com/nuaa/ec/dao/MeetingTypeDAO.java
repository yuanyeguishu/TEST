package com.nuaa.ec.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.MeetingType;

/**
 	* A data access object (DAO) providing persistence and search support for MeetingType entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.MeetingType
  * @author MyEclipse Persistence Tools 
 */
public class MeetingTypeDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(MeetingTypeDAO.class);
		//property constants
	public static final String MEETING_TYPE_NAME = "meetingTypeName";
	public static final String SPARE_TIRE = "spareTire";



    
    public void save(MeetingType transientInstance) {
        log.debug("saving MeetingType instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(MeetingType persistentInstance) {
        log.debug("deleting MeetingType instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public MeetingType findById( java.lang.String id) {
        log.debug("getting MeetingType instance with id: " + id);
        try {
            MeetingType instance = (MeetingType) getSession()
                    .get("com.nuaa.ec.model.MeetingType", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(MeetingType instance) {
        log.debug("finding MeetingType instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.MeetingType")
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
      log.debug("finding MeetingType instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from MeetingType as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByMeetingTypeName(Object meetingTypeName
	) {
		return findByProperty(MEETING_TYPE_NAME, meetingTypeName
		);
	}
	
	public List findBySpareTire(Object spareTire
	) {
		return findByProperty(SPARE_TIRE, spareTire
		);
	}
	

	public List findAll() {
		log.debug("finding all MeetingType instances");
		try {
			String queryString = "from MeetingType";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public MeetingType merge(MeetingType detachedInstance) {
        log.debug("merging MeetingType instance");
        try {
            MeetingType result = (MeetingType) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(MeetingType instance) {
        log.debug("attaching dirty MeetingType instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(MeetingType instance) {
        log.debug("attaching clean MeetingType instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}