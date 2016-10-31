package com.nuaa.ec.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.Department;
import com.nuaa.ec.model.TfjoinStudentActivityPerformance;
import com.nuaa.ec.model.TfundergraduateTutorGuidancePerformance;
import com.opensymphony.xwork2.ActionContext;

/**
 	* A data access object (DAO) providing persistence and search support for TfundergraduateTutorGuidancePerformance entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.TfundergraduateTutorGuidancePerformance
  * @author MyEclipse Persistence Tools 
 */
public class TfundergraduateTutorGuidancePerformanceDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(TfundergraduateTutorGuidancePerformanceDAO.class);
		//property constants
	public static final String STUDENT_QUANTITY = "studentQuantity";
	public static final String YEARS = "years";
	public static final String FINAL_SCORE = "finalScore";
	public static final String CHECK_OUT = "checkOut";
	public static final String SPARE_TIRE = "spareTire";
	public static final String YEARCEILING = "yearceiling";

	private Map<String,Object> session=ActionContext.getContext().getSession();

	private List<TfundergraduateTutorGuidancePerformance> tfUndergraduateTutorGuidancePerformanceList = null;
	public boolean updateCheckoutStatus(List<TfundergraduateTutorGuidancePerformance> tfUndergraduateTutorGuidancePerformance){
		Session session=this.getSession();
		Transaction tx=null;
		boolean updateFlag=false;
		try{
			for(int i=0;i<tfUndergraduateTutorGuidancePerformance.size();i++){
				session.update(tfUndergraduateTutorGuidancePerformance.get(i));
			}
			tx=session.beginTransaction();
			tx.commit();
			updateFlag=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return updateFlag;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getTfUndergraduateTutorGuidancePerformanceListToBeAudited(int pageIndex, int pageSize, String termId,
			Department department, String checkOut, boolean isDivided) {
		try{
			StringBuffer hqlBuffer = null;
			if (department.getDepartmentId() == null
					|| department.getDepartmentId().length() == 0) {
				/*
				 * 第一次进入的时候，不显示记录
				 */
				session.put("pageCount_UTG", 0);
				session.put("recordNumber_UTG", 0);
				return tfUndergraduateTutorGuidancePerformanceList = new ArrayList<TfundergraduateTutorGuidancePerformance>();
			} else {
				// 查出符合条件的全部的记录
				hqlBuffer = new StringBuffer(
						"select UTG from TfundergraduateTutorGuidancePerformance UTG,Tfterm TERM where TERM.termId=UTG.termId"
								+ " and UTG.spareTire='1'"
								+ " and TERM.spareTire='1'"
								+ " and UTG.checkOut='" + checkOut + "'"
								+ " and UTG.tfundergraduateTutorGuidanceCache.spareTire='1'"
								+ " and UTG.teacher.spareTire='1'"
								+ " and UTG.teacher.department.spareTire='1'"
								+ " and UTG.teacher.department.departmentId='"+department.getDepartmentId()+"'"
								+ " and UTG.termId='"+termId+"'"
								+ " order by UTG.tfundergraduateTutorGuidanceCache.guidanceId asc");
				// 判断是否为分页操作
				if (!isDivided) {
					//如果不是分页操作，取出所有符合条件的记录
					tfUndergraduateTutorGuidancePerformanceList = this.getSession()
							.createQuery(hqlBuffer.toString()).list();
					int recordNumber=tfUndergraduateTutorGuidancePerformanceList.size();
					session.put("pageCount_UTG", recordNumber%pageSize==0?(recordNumber/pageSize):(recordNumber/pageSize+1));
					session.put("recordNumber_UTG", tfUndergraduateTutorGuidancePerformanceList.size());
				} 
				//无论是不是分页查询，都在后台进行分页操作。
				tfUndergraduateTutorGuidancePerformanceList = this.getSession()
						.createQuery(hqlBuffer.toString())
						.setFirstResult((pageIndex - 1) * pageSize)
						.setMaxResults(pageSize).list();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return tfUndergraduateTutorGuidancePerformanceList;
	}

    
    public void save(TfundergraduateTutorGuidancePerformance transientInstance) {
        log.debug("saving TfundergraduateTutorGuidancePerformance instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(TfundergraduateTutorGuidancePerformance persistentInstance) {
        log.debug("deleting TfundergraduateTutorGuidancePerformance instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TfundergraduateTutorGuidancePerformance findById( java.lang.Integer id) {
        log.debug("getting TfundergraduateTutorGuidancePerformance instance with id: " + id);
        try {
            TfundergraduateTutorGuidancePerformance instance = (TfundergraduateTutorGuidancePerformance) getSession()
                    .get("com.nuaa.ec.model.TfundergraduateTutorGuidancePerformance", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TfundergraduateTutorGuidancePerformance instance) {
        log.debug("finding TfundergraduateTutorGuidancePerformance instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.TfundergraduateTutorGuidancePerformance")
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
      log.debug("finding TfundergraduateTutorGuidancePerformance instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TfundergraduateTutorGuidancePerformance as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByStudentQuantity(Object studentQuantity
	) {
		return findByProperty(STUDENT_QUANTITY, studentQuantity
		);
	}
	
	public List findByYears(Object years
	) {
		return findByProperty(YEARS, years
		);
	}
	
	public List findByFinalScore(Object finalScore
	) {
		return findByProperty(FINAL_SCORE, finalScore
		);
	}
	
	public List findByCheckOut(Object checkOut
	) {
		return findByProperty(CHECK_OUT, checkOut
		);
	}
	
	public List findBySpareTire(Object spareTire
	) {
		return findByProperty(SPARE_TIRE, spareTire
		);
	}
	
	public List findByYearceiling(Object yearceiling
	) {
		return findByProperty(YEARCEILING, yearceiling
		);
	}
	

	public List findAll() {
		log.debug("finding all TfundergraduateTutorGuidancePerformance instances");
		try {
			String queryString = "from TfundergraduateTutorGuidancePerformance";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public TfundergraduateTutorGuidancePerformance merge(TfundergraduateTutorGuidancePerformance detachedInstance) {
        log.debug("merging TfundergraduateTutorGuidancePerformance instance");
        try {
            TfundergraduateTutorGuidancePerformance result = (TfundergraduateTutorGuidancePerformance) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TfundergraduateTutorGuidancePerformance instance) {
        log.debug("attaching dirty TfundergraduateTutorGuidancePerformance instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TfundergraduateTutorGuidancePerformance instance) {
        log.debug("attaching clean TfundergraduateTutorGuidancePerformance instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}