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
import com.nuaa.ec.model.Teacher;
import com.nuaa.ec.model.TfpracticeInnovationGuidePerformance;
import com.nuaa.ec.model.TfpracticeInnovationGuidePerformanceUnionTfterm;
import com.nuaa.ec.model.TfsummerCourseInternationalConstructionPerformance;
import com.opensymphony.xwork2.ActionContext;

/**
 	* A data access object (DAO) providing persistence and search support for TfpracticeInnovationGuidePerformance entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see com.nuaa.ec.model.TfpracticeInnovationGuidePerformance
  * @author MyEclipse Persistence Tools 
 */
public class TfpracticeInnovationGuidePerformanceDAO extends BaseHibernateDAO  {
	     private static final Logger log = LoggerFactory.getLogger(TfpracticeInnovationGuidePerformanceDAO.class);
		//property constants
	public static final String PROJECT_ID = "projectId";
	public static final String PROJECT_NAME = "projectName";
	public static final String FINAL_SCORE = "finalScore";
	public static final String CHECK_OUT = "checkOut";
	public static final String SPARE_TIRE = "spareTire";

	private Map<String,Object> session=ActionContext.getContext().getSession();

	private List<TfpracticeInnovationGuidePerformance> tfPracticeInnovationGuidePerformanceList = null;
	/**
	 * 获得所有的记录信息 但是顺便实现了分页(管理员操作权限)
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findAllWithDivided_adm(int pageIndex,int pageSize,String termId,String searchCondition,boolean isDivided){
		List<TfpracticeInnovationGuidePerformanceUnionTfterm> list=new ArrayList<TfpracticeInnovationGuidePerformanceUnionTfterm>();
		StringBuffer hql = null;
		/*
		 * 第一次进来的时候 TermID应该为空，默认取出当前教师所有的数据
		 */
		if (termId != null && termId.length() != 0 && searchCondition!=null && searchCondition.trim().length()!=0) {
			hql=new StringBuffer("select new com.nuaa.ec.model.TfpracticeInnovationGuidePerformanceUnionTfterm(PIG,TERM) from TfpracticeInnovationGuidePerformance PIG,Tfterm TERM where TERM.termId=PIG.termId"
					+ " and PIG.spareTire='1'"
					+ " and TERM.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideLevel.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideGraduationThesisGuideEvalution.spareTire='1'"
					+ " and PIG.teacher.spareTire='1'"
					+ " and PIG.termId='"+termId+"'"
					+ " and PIG.projectName like '%"+searchCondition+"%'"
					+ " order by PIG.projectId desc");
		} else {
			hql=new StringBuffer("select new com.nuaa.ec.model.TfpracticeInnovationGuidePerformanceUnionTfterm(PIG,TERM) from TfpracticeInnovationGuidePerformance PIG,Tfterm TERM where TERM.termId=PIG.termId"
					+ " and PIG.spareTire='1'"
					+ " and TERM.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideLevel.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideGraduationThesisGuideEvalution.spareTire='1'"
					+ " and PIG.teacher.spareTire='1'");
			//有学期但是没有查询条件的情况
			if(termId != null && termId.length() != 0 &&(searchCondition==null || searchCondition.trim().length()==0)){
				hql.append(" and PIG.termId='"+termId+"'");
			}else if(searchCondition!=null && searchCondition.length()!=0 &&(termId == null || termId.length() == 0)){
				//有查询条件 但是没有学期的情况
				hql.append(" and PIG.projectName like '%"+searchCondition+"%'");
			}else{//学期和查询条件都没有的情况
				//这块没有业务逻辑，只是为了使得逻辑清楚
			}
			hql.append(" order by PIG.projectId desc");
		}
		try{
			if(!isDivided){
				list=this.getSession().createQuery(hql.toString()).list();
				int listSize=list.size();
				session.put("recordNumber_ATPIG",list.size());
				session.put("pageCount_ATPIG", listSize%pageSize==0?(listSize/pageSize):(listSize/pageSize+1));
			}
			/*
			 * 分页 pageIndex 默认是1，显示第一页，
			 * 但以后会随着前台的分页操作同步更新。
			 */
			list=this.getSession().createQuery(hql.toString()).setFirstResult((pageIndex-1)*pageSize).setMaxResults(pageSize).list();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	/**
	 * 获得所有的记录信息 但是顺便实现了分页
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List findAllWithDivided(int pageIndex,int pageSize,String termId,boolean isDivided){
		Teacher teacherHaveLogin=(Teacher) session.get("teacher");
		List<TfpracticeInnovationGuidePerformanceUnionTfterm> list=new ArrayList<TfpracticeInnovationGuidePerformanceUnionTfterm>();
		String hql = null;
		/*
		 * 第一次进来的时候 TermID应该为空，默认取出当前教师所有的数据
		 */
		if (termId == null || termId.length() == 0) {
			hql="select new com.nuaa.ec.model.TfpracticeInnovationGuidePerformanceUnionTfterm(PIG,TERM) from TfpracticeInnovationGuidePerformance PIG,Tfterm TERM where TERM.termId=PIG.termId"
					+ " and PIG.spareTire='1'"
					+ " and TERM.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideLevel.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideGraduationThesisGuideEvalution.spareTire='1'"
					+ " and PIG.teacher.spareTire='1'"
					+ " and PIG.teacher=?"
					+ " order by PIG.projectId desc";
		} else {
			hql="select new com.nuaa.ec.model.TfpracticeInnovationGuidePerformanceUnionTfterm(PIG,TERM) from TfpracticeInnovationGuidePerformance PIG,Tfterm TERM where TERM.termId=PIG.termId"
					+ " and PIG.spareTire='1'"
					+ " and TERM.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideLevel.spareTire='1'"
					+ " and PIG.tfpracticeInnovationGuideGraduationThesisGuideEvalution.spareTire='1'"
					+ " and PIG.teacher.spareTire='1'"
					+ " and PIG.teacher=?"
					+ " and PIG.termId='"+termId+"'"
					+ " order by PIG.projectId desc";
		}
		try{
			if(!isDivided){
				list=this.getSession().createQuery(hql).setParameter(0, teacherHaveLogin).list();
				int listSize=list.size();
				session.put("recordNumber_GTPIG",list.size());
				session.put("pageCount_GTPIG", listSize%pageSize==0?(listSize/pageSize):(listSize/pageSize+1));
			}
			/*
			 * 分页 pageIndex 默认是1，显示第一页，
			 * 但以后会随着前台的分页操作同步更新。
			 */
			list=this.getSession().createQuery(hql).setFirstResult((pageIndex-1)*pageSize).setMaxResults(pageSize).setParameter(0, teacherHaveLogin).list();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return list;
	}
	public boolean updateCheckoutStatus(List<TfpracticeInnovationGuidePerformance> tfPracticeInnovationGuidePerformance){
		Session session=this.getSession();
		Transaction tx=null;
		boolean updateFlag=false;
		try{
			for(int i=0;i<tfPracticeInnovationGuidePerformance.size();i++){
				session.update(tfPracticeInnovationGuidePerformance.get(i));
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
	public List getTfPracticeInnovationGuidePerformanceListToBeAudited(int pageIndex, int pageSize, String termId,
			Department department, String checkOut, boolean isDivided) {
		try{
			StringBuffer hqlBuffer = null;
			if (department.getDepartmentId() == null
					|| department.getDepartmentId().length() == 0) {
				/*
				 * 第一次进入的时候，不显示记录
				 */
				session.put("pageCount_PIG", 0);
				session.put("recordNumber_PIG", 0);
				return tfPracticeInnovationGuidePerformanceList = new ArrayList<TfpracticeInnovationGuidePerformance>();
			} else {
				// 查出符合条件的全部的记录
				hqlBuffer = new StringBuffer(
						"select PIG from TfpracticeInnovationGuidePerformance PIG,Tfterm TERM where TERM.termId=PIG.termId"
								+ " and PIG.spareTire='1'"
								+ " and TERM.spareTire='1'"
								+ " and PIG.checkOut='" + checkOut + "'"
								+ " and PIG.tfpracticeInnovationGuideLevel.spareTire='1'"
								+ " and PIG.tfpracticeInnovationGuideGraduationThesisGuideEvalution.spareTire='1'"
								+ " and PIG.teacher.spareTire='1'"
								+ " and PIG.teacher.department.spareTire='1'"
								+ " and PIG.teacher.department.departmentId='"+department.getDepartmentId()+"'"
								+ " and PIG.termId='"+termId+"'"
								+ " order by PIG.projectId asc");
				// 判断是否为分页操作
				if (!isDivided) {
					//如果不是分页操作，取出所有符合条件的记录
					tfPracticeInnovationGuidePerformanceList = this.getSession()
							.createQuery(hqlBuffer.toString()).list();
					int recordNumber=tfPracticeInnovationGuidePerformanceList.size();
					session.put("pageCount_PIG", recordNumber%pageSize==0?(recordNumber/pageSize):(recordNumber/pageSize+1));
					session.put("recordNumber_PIG", tfPracticeInnovationGuidePerformanceList.size());
				} 
				//无论是不是分页查询，都在后台进行分页操作。
				tfPracticeInnovationGuidePerformanceList = this.getSession()
						.createQuery(hqlBuffer.toString())
						.setFirstResult((pageIndex - 1) * pageSize)
						.setMaxResults(pageSize).list();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return tfPracticeInnovationGuidePerformanceList;
	}

    
    public void save(TfpracticeInnovationGuidePerformance transientInstance) {
        log.debug("saving TfpracticeInnovationGuidePerformance instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
	public void delete(TfpracticeInnovationGuidePerformance persistentInstance) {
        log.debug("deleting TfpracticeInnovationGuidePerformance instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public TfpracticeInnovationGuidePerformance findById( java.lang.Integer id) {
        log.debug("getting TfpracticeInnovationGuidePerformance instance with id: " + id);
        try {
            TfpracticeInnovationGuidePerformance instance = (TfpracticeInnovationGuidePerformance) getSession()
                    .get("com.nuaa.ec.model.TfpracticeInnovationGuidePerformance", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    
    public List findByExample(TfpracticeInnovationGuidePerformance instance) {
        log.debug("finding TfpracticeInnovationGuidePerformance instance by example");
        try {
            List results = getSession()
                    .createCriteria("com.nuaa.ec.model.TfpracticeInnovationGuidePerformance")
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
      log.debug("finding TfpracticeInnovationGuidePerformance instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from TfpracticeInnovationGuidePerformance as model where model." 
         						+ propertyName + "= ?";
         Query queryObject = getSession().createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
         log.error("find by property name failed", re);
         throw re;
      }
	}

	public List findByProjectId(Object projectId
	) {
		return findByProperty(PROJECT_ID, projectId
		);
	}
	
	public List findByProjectName(Object projectName
	) {
		return findByProperty(PROJECT_NAME, projectName
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
	

	public List findAll() {
		log.debug("finding all TfpracticeInnovationGuidePerformance instances");
		try {
			String queryString = "from TfpracticeInnovationGuidePerformance";
	         Query queryObject = getSession().createQuery(queryString);
			 return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
    public TfpracticeInnovationGuidePerformance merge(TfpracticeInnovationGuidePerformance detachedInstance) {
        log.debug("merging TfpracticeInnovationGuidePerformance instance");
        try {
            TfpracticeInnovationGuidePerformance result = (TfpracticeInnovationGuidePerformance) getSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TfpracticeInnovationGuidePerformance instance) {
        log.debug("attaching dirty TfpracticeInnovationGuidePerformance instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(TfpracticeInnovationGuidePerformance instance) {
        log.debug("attaching clean TfpracticeInnovationGuidePerformance instance");
        try {
                      	getSession().buildLockRequest(LockOptions.NONE).lock(instance);
          	            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}