package com.nuaa.ec.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.AcademicWork;
import com.nuaa.ec.model.InvitedExpertsSpeech;
import com.nuaa.ec.model.ResearchLab;
import com.opensymphony.xwork2.ActionContext;

/**
 	* A data access object (DAO) providing persistence and search support for AcademicWork entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.AcademicWork
  * @author MyEclipse Persistence Tools 
 */
public class AcademicWorkDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(AcademicWorkDAO.class);
		//property constants
	public static final String FIRST_AUTHOR = "firstAuthor";
	public static final String WORK_NAME = "workName";
	public static final String PUBLISH_DATE = "publishDate";
	public static final String ISBN = "isbn";
	public static final String WORD_NUMBER = "wordNumber";
	public static final String OTHER_AUTHOR_JOIN = "otherAuthorJoin";
	public static final String SPARE_TIRE = "spareTire";
//	public static final String WORD_ID = "wordId";
	public static final String CHARGE_PERSON_ID = "chargePersonId";
	public static final String CHARGE_PERSON = "chargePerson";
	public static final String CHECKOUT = "checkout";
	
	private Map<String,Object> session=ActionContext.getContext().getSession();


	/**
	 * 所长审核功能
	 * 
	 * @param academicWorks
	 * @return
	 */
	public boolean updateCheckoutStatus(
			List<AcademicWork> academicWorks) {
		Session session = this.getSession();
		Transaction tx = null;
		boolean updateFlag = false;
		try {
			for (int i = 0; i < academicWorks.size(); i++) {
				session.update(academicWorks.get(i));
			}
			tx = session.beginTransaction();
			tx.commit();
			updateFlag = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			tx.rollback();
		}
		return updateFlag;
	}
	/**
	 * 如果项目没有通过那么项目里的所有成员都将不通过。
	 * 
	 * @param academicWorks
	 * @return
	 */
	public boolean cascadeUpdateCheckOutOfMembers(
			List<AcademicWork> academicWorks,String flag) {
		boolean operationFlag=false;
		Session session = this.getSession();
		Transaction tx=null;
		try{
			for (AcademicWork aw : academicWorks) {
				session.createQuery(
						"UPDATE TeacherAndacademicWork TAAW SET TAAW.checkOut="+flag
						+ " WHERE TAAW.academicWork.acaworkId='"+ aw.getAcaworkId()+"'").executeUpdate();
			}
			tx=session.beginTransaction();
			tx.commit();
			operationFlag=true;
		}catch(Exception ex){
			ex.printStackTrace();
			tx.rollback();
		}
		return operationFlag;
	}

	/**
	 * function：获得符合条件的所有参加学术会议的记录
	 * @param
	 * @param transientInstance
	 */
    @SuppressWarnings("unchecked")
	public List<AcademicWork> getAllRecordsWithCondition(int pageIndex,
			int pageSize, String foredate, String afterdate,
			ResearchLab researchLab, String checkOut, boolean isDivided){
    	StringBuffer hql=new StringBuffer("FROM AcademicWork AW WHERE AW.spareTire='1'"
    			+ " AND AW.researchLabId='"+researchLab.getResearchLabId()+"'"
				+ " AND AW.publishClub.spareTire='1'"
				+ " AND AW.wordsNumber.spareTire='1'");
    	List<AcademicWork> academicWorks=new ArrayList<AcademicWork>();
		if (checkOut != null && checkOut.length() != 0
				&& !checkOut.trim().equals("4")) {
			hql.append(" AND AW.checkout='" + checkOut + "'");
		}
		if (foredate != null && afterdate != null && foredate.length() != 0
				&& afterdate.length() != 0) {
			hql.append(" AND AW.publishDate BETWEEN '"+foredate+"' AND '"+afterdate+"'");
		}
		Query query=this.getSession().createQuery(hql.toString());
		if(!isDivided){
			academicWorks=query.list();
			int size=academicWorks.size();
			session.put("pageCount_GTAW", size%pageSize==0?(size/pageSize):(size/pageSize+1));
			session.put("recordNumber_GTAW", size);
		}
		academicWorks=query.setMaxResults(pageSize).setFirstResult((pageIndex-1)*pageSize).list();
    	return academicWorks;
    }



    
    public void save(AcademicWork transientInstance) {
        log.debug("saving AcademicWork instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(AcademicWork persistentInstance) {
        log.debug("deleting AcademicWork instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public AcademicWork findById( java.lang.String id) {
        log.debug("getting AcademicWork instance with id: " + id);
        try {
            AcademicWork instance = (AcademicWork) getSession()
                    .get("com.nuaa.ec.model.AcademicWork", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findMembersano(String acaworkId) {
   	 
        try {
           String queryString = "select new com.nuaa.ec.model.TeacherMember(t.teacher.teacherId,t.teacher.teacherName,t.selfUndertakeTask.undertakeTaskName) "
           		+ "from TeacherAndacademicWork as t "
           		+ "where t.academicWork.acaworkId=? "
           		+ "and t.spareTire='1' "
           		+ "and t.teacher.spareTire='1'";
           Query queryObject = getSession().createQuery(queryString);
  		  queryObject.setParameter(0, acaworkId);
  		 List li = queryObject.list();
  		return li;
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    
    public List findMember(String acaworkId) {
    	 
        try {
           String queryString = "select new com.nuaa.ec.model.TeacherMember(t.teacher.teacherId,t.teacher.teacherName,'') from TeacherAndacademicWork as t where t.academicWork.acaworkId=? and t.spareTire='1' and t.teacher.spareTire='1'";
           Query queryObject = getSession().createQuery(queryString);
  		  queryObject.setParameter(0, acaworkId);
  		 List li = queryObject.list();
  		return li;
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    
    public List findByExample(AcademicWork instance) {
        log.debug("finding AcademicWork instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.AcademicWork")
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
      log.debug("finding AcademicWork instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from AcademicWork as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByFirstAuthor(Object firstAuthor
	) {
		return findByProperty(FIRST_AUTHOR, firstAuthor
		);
	}
	
	public List findByWorkName(Object workName
	) {
		return findByProperty(WORK_NAME, workName
		);
	}
	
	public List findByPublishDate(Object publishDate
	) {
		return findByProperty(PUBLISH_DATE, publishDate
		);
	}
	
	public List findByIsbn(Object isbn
	) {
		return findByProperty(ISBN, isbn
		);
	}
	
	public List findByWordNumber(Object wordNumber
	) {
		return findByProperty(WORD_NUMBER, wordNumber
		);
	}
	
	public List findByOtherAuthorJoin(Object otherAuthorJoin
	) {
		return findByProperty(OTHER_AUTHOR_JOIN, otherAuthorJoin
		);
	}
	
	public List findBySpareTire(Object spareTire
	) {
		return findByProperty(SPARE_TIRE, spareTire
		);
	}
	
//	public List findByWordId(Object wordId
//	) {
//		return findByProperty(WORD_ID, wordId
//		);
//	}
	
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
	
	public List findAll(int currentrow,int limit,String condition) {
		log.debug("finding all AcademicWork instances");
		try {
			String queryString = "from AcademicWork aw "
					+ "where aw.spareTire = '1' "
					+ "and aw.publishClub.spareTire='1'  "+condition+" order by aw.publishDate desc";
	         Query queryObject = getSession().createQuery(queryString).setFirstResult(currentrow);
	         queryObject.setMaxResults(limit);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	public int getRows(String condition) {
		try {
			String queryString = "from AcademicWork aw where aw.spareTire = '1' "+condition+" order by aw.publishDate,aw.acaworkId desc";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list().size();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public AcademicWork merge(AcademicWork detachedInstance) {
        log.debug("merging AcademicWork instance");
        try {
            AcademicWork result = (AcademicWork) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(AcademicWork instance) {
        log.debug("attaching dirty AcademicWork instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(AcademicWork instance) {
        log.debug("attaching clean AcademicWork instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}