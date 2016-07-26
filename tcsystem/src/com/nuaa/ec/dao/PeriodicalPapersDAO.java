package com.nuaa.ec.dao;

import java.util.List;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.PeriodicalPapers;

/**
 	* A data access object (DAO) providing persistence and search support for PeriodicalPapers entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.PeriodicalPapers
  * @author MyEclipse Persistence Tools 
 */
public class PeriodicalPapersDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(PeriodicalPapersDAO.class);
		//property constants
	public static final String PPID = "ppid";
	public static final String FIRST_AUTHOR = "firstAuthor";
	public static final String SECOND_AUTHOR = "secondAuthor";
	public static final String THESIS_TITLE = "thesisTitle";
	public static final String YEAR = "year";
	public static final String FILE = "file";
	public static final String PHASE = "phase";
	public static final String DESCRIBE = "describe";
	public static final String SPARE_TIRE = "spareTire";
	public static final String CHARGE_PERSON_ID = "chargePersonId";
	public static final String CHARGE_PERSON = "chargePerson";
	public static final String CHECKOUT = "checkout";



    
    public void save(PeriodicalPapers transientInstance) {
        log.debug("saving PeriodicalPapers instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(PeriodicalPapers persistentInstance) {
        log.debug("deleting PeriodicalPapers instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public PeriodicalPapers findById( java.lang.Integer id) {
        log.debug("getting PeriodicalPapers instance with id: " + id);
        try {
            PeriodicalPapers instance = (PeriodicalPapers) getSession()
                    .get("com.nuaa.ec.model.PeriodicalPapers", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(PeriodicalPapers instance) {
        log.debug("finding PeriodicalPapers instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.PeriodicalPapers")
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
      log.debug("finding PeriodicalPapers instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from PeriodicalPapers as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByPpid(Object ppid
	) {
		return findByProperty(PPID, ppid
		);
	}
	
	public List findByFirstAuthor(Object firstAuthor
	) {
		return findByProperty(FIRST_AUTHOR, firstAuthor
		);
	}
	
	public List findBySecondAuthor(Object secondAuthor
	) {
		return findByProperty(SECOND_AUTHOR, secondAuthor
		);
	}
	
	public List findByThesisTitle(Object thesisTitle
	) {
		return findByProperty(THESIS_TITLE, thesisTitle
		);
	}
	
	public List findByYear(Object year
	) {
		return findByProperty(YEAR, year
		);
	}
	
	public List findByFile(Object file
	) {
		return findByProperty(FILE, file
		);
	}
	
	public List findByPhase(Object phase
	) {
		return findByProperty(PHASE, phase
		);
	}
	
	public List findByDescribe(Object describe
	) {
		return findByProperty(DESCRIBE, describe
		);
	}
	
	public List findBySpareTire(Object spareTire
	) {
		return findByProperty(SPARE_TIRE, spareTire
		);
	}
	
	public List findByChargePersonId(Object chargePersonId
	) {
		return findByProperty(CHARGE_PERSON_ID, chargePersonId
		);
	}
	
	public List findByChargePerson(Object chargePerson
	) {
		return findByProperty(CHARGE_PERSON, chargePerson
		);
	}
	
	public List findByCheckout(Object checkout
	) {
		return findByProperty(CHECKOUT, checkout
		);
	}
	

	public List findAll() {
		log.debug("finding all PeriodicalPapers instances");
		try {
			String queryString = "from PeriodicalPapers";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public PeriodicalPapers merge(PeriodicalPapers detachedInstance) {
        log.debug("merging PeriodicalPapers instance");
        try {
            PeriodicalPapers result = (PeriodicalPapers) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(PeriodicalPapers instance) {
        log.debug("attaching dirty PeriodicalPapers instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(PeriodicalPapers instance) {
        log.debug("attaching clean PeriodicalPapers instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}