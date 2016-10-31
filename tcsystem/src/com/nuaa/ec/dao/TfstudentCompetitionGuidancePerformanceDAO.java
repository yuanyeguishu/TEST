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
import com.nuaa.ec.model.TfpracticeInnovationGuidePerformance;
import com.nuaa.ec.model.TfstudentCompetitionGuidancePerformance;
import com.opensymphony.xwork2.ActionContext;

/**
 	* A data access object (DAO) providing persistence and search support for TfstudentCompetitionGuidancePerformance entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.TfstudentCompetitionGuidancePerformance
  * @author MyEclipse Persistence Tools 
 */
public class TfstudentCompetitionGuidancePerformanceDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(TfstudentCompetitionGuidancePerformanceDAO.class);
		//property constants
	public static final String FINAL_SCORE = "finalScore";
	public static final String SPARE_TIRE = "spareTire";
	public static final String COMPETITION_ID = "competitionId";
	public static final String COMPETITION_NAME = "competitionName";
	public static final String CHECK_OUT = "checkOut";

	private Map<String,Object> session=ActionContext.getContext().getSession();

	private List<TfstudentCompetitionGuidancePerformance> tfStudentCompetitionGuidancePerformanceList = null;
	public boolean updateCheckoutStatus(List<TfstudentCompetitionGuidancePerformance> tfStudentCompetitionGuidancePerformance){
		Session session=this.getSession();
		Transaction tx=null;
		boolean updateFlag=false;
		try{
			for(int i=0;i<tfStudentCompetitionGuidancePerformance.size();i++){
				session.update(tfStudentCompetitionGuidancePerformance.get(i));
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
	public List getTfStudentCompetitionGuidancePerformanceListToBeAudited(int pageIndex, int pageSize, String termId,
			Department department, String checkOut, boolean isDivided) {
		try{
			StringBuffer hqlBuffer = null;
			if (department.getDepartmentId() == null
					|| department.getDepartmentId().length() == 0) {
				/*
				 * 第一次进入的时候，不显示记录
				 */
				session.put("pageCount_SCG", 0);
				session.put("recordNumber_SCG", 0);
				return tfStudentCompetitionGuidancePerformanceList = new ArrayList<TfstudentCompetitionGuidancePerformance>();
			} else {
				// 查出符合条件的全部的记录
				hqlBuffer = new StringBuffer(
						"select SCG from TfstudentCompetitionGuidancePerformance SCG,Tfterm TERM where TERM.termId=SCG.termId"
								+ " and SCG.spareTire='1'"
								+ " and TERM.spareTire='1'"
								+ " and SCG.checkOut='" + checkOut + "'"
								+ " and SCG.tfstudentCompetitionGuidanceScore.spareTire='1'"
								+ " and SCG.tfstudentCompetitionGuidanceScore.tfstudentCompetitionGuidanceCompetitionType.spareTire='1'"
								+ " and SCG.tfstudentCompetitionGuidanceScore.tfstudentCompetitionGuidanceRewardLevel.spareTire='1'"
								+ " and SCG.teacher.spareTire='1'"
								+ " and SCG.teacher.department.spareTire='1'"
								+ " and SCG.teacher.department.departmentId='"+department.getDepartmentId()+"'"
								+ " and SCG.termId='"+termId+"'"
								+ " order by SCG.competitionId asc");
				// 判断是否为分页操作
				if (!isDivided) {
					//如果不是分页操作，取出所有符合条件的记录
					tfStudentCompetitionGuidancePerformanceList = this.getSession()
							.createQuery(hqlBuffer.toString()).list();
					int recordNumber=tfStudentCompetitionGuidancePerformanceList.size();
					session.put("pageCount_SCG", recordNumber%pageSize==0?(recordNumber/pageSize):(recordNumber/pageSize+1));
					session.put("recordNumber_SCG", tfStudentCompetitionGuidancePerformanceList.size());
				} 
				//无论是不是分页查询，都在后台进行分页操作。
				tfStudentCompetitionGuidancePerformanceList = this.getSession()
						.createQuery(hqlBuffer.toString())
						.setFirstResult((pageIndex - 1) * pageSize)
						.setMaxResults(pageSize).list();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return tfStudentCompetitionGuidancePerformanceList;
	}

    
    public void save(TfstudentCompetitionGuidancePerformance transientInstance) {
        log.debug("saving TfstudentCompetitionGuidancePerformance instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(TfstudentCompetitionGuidancePerformance persistentInstance) {
        log.debug("deleting TfstudentCompetitionGuidancePerformance instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TfstudentCompetitionGuidancePerformance findById( java.lang.Integer id) {
        log.debug("getting TfstudentCompetitionGuidancePerformance instance with id: " + id);
        try {
            TfstudentCompetitionGuidancePerformance instance = (TfstudentCompetitionGuidancePerformance) getSession()
                    .get("com.nuaa.ec.model.TfstudentCompetitionGuidancePerformance", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TfstudentCompetitionGuidancePerformance instance) {
        log.debug("finding TfstudentCompetitionGuidancePerformance instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.TfstudentCompetitionGuidancePerformance")
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
      log.debug("finding TfstudentCompetitionGuidancePerformance instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TfstudentCompetitionGuidancePerformance as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByFinalScore(Object finalScore
	) {
		return findByProperty(FINAL_SCORE, finalScore
		);
	}
	
	public List findBySpareTire(Object spareTire
	) {
		return findByProperty(SPARE_TIRE, spareTire
		);
	}
	
	public List findByCompetitionId(Object competitionId
	) {
		return findByProperty(COMPETITION_ID, competitionId
		);
	}
	
	public List findByCompetitionName(Object competitionName
	) {
		return findByProperty(COMPETITION_NAME, competitionName
		);
	}
	
	public List findByCheckOut(Object checkOut
	) {
		return findByProperty(CHECK_OUT, checkOut
		);
	}
	

	public List findAll() {
		log.debug("finding all TfstudentCompetitionGuidancePerformance instances");
		try {
			String queryString = "from TfstudentCompetitionGuidancePerformance";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public TfstudentCompetitionGuidancePerformance merge(TfstudentCompetitionGuidancePerformance detachedInstance) {
        log.debug("merging TfstudentCompetitionGuidancePerformance instance");
        try {
            TfstudentCompetitionGuidancePerformance result = (TfstudentCompetitionGuidancePerformance) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TfstudentCompetitionGuidancePerformance instance) {
        log.debug("attaching dirty TfstudentCompetitionGuidancePerformance instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TfstudentCompetitionGuidancePerformance instance) {
        log.debug("attaching clean TfstudentCompetitionGuidancePerformance instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}