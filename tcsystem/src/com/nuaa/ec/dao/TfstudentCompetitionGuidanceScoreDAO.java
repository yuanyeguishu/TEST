package com.nuaa.ec.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nuaa.ec.model.TfstudentCompetitionGuidanceCompetitionType;
import com.nuaa.ec.model.TfstudentCompetitionGuidanceRewardLevel;
import com.nuaa.ec.model.TfstudentCompetitionGuidanceScore;

/**
 * A data access object (DAO) providing persistence and search support for
 * TfstudentCompetitionGuidanceScore entities. Transaction control of the
 * save(), update() and delete() operations can directly support Spring
 * container-managed transactions or they can be augmented to handle
 * user-managed Spring transactions. Each of these methods provides additional
 * information for how to configure it for the desired type of transaction
 * control.
 * 
 * @see com.nuaa.ec.model.TfstudentCompetitionGuidanceScore
 * @author MyEclipse Persistence Tools
 */
public class TfstudentCompetitionGuidanceScoreDAO extends BaseHibernateDAO {
	private static final Logger log = LoggerFactory
			.getLogger(TfstudentCompetitionGuidanceScoreDAO.class);
	// property constants
	public static final String BASE_SCORE = "baseScore";
	public static final String SPARE_TIRE = "spareTire";

	/*
	 * 通过竞赛级别和获奖级别获取Score信息
	 */
	@SuppressWarnings("unchecked")
	public TfstudentCompetitionGuidanceScore findScoreWithCompetitionTypeAndRewardLevel(
			TfstudentCompetitionGuidanceCompetitionType competitionType,
			TfstudentCompetitionGuidanceRewardLevel rewardLevel) {
		List<TfstudentCompetitionGuidanceScore> list=new ArrayList<TfstudentCompetitionGuidanceScore>();
		String hql = "select SCG from TfstudentCompetitionGuidanceScore SCG where SCG.tfstudentCompetitionGuidanceCompetitionType=?"
				+ " and SCG.tfstudentCompetitionGuidanceRewardLevel=?";
		list=this.getSession().createQuery(hql).setParameter(0, competitionType).setParameter(1, rewardLevel).list();
		return list.get(0);
	}

	public void save(TfstudentCompetitionGuidanceScore transientInstance) {
		log.debug("saving TfstudentCompetitionGuidanceScore instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TfstudentCompetitionGuidanceScore persistentInstance) {
		log.debug("deleting TfstudentCompetitionGuidanceScore instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TfstudentCompetitionGuidanceScore findById(java.lang.String id) {
		log.debug("getting TfstudentCompetitionGuidanceScore instance with id: "
				+ id);
		try {
			TfstudentCompetitionGuidanceScore instance = (TfstudentCompetitionGuidanceScore) getSession()
					.get("com.nuaa.ec.model.TfstudentCompetitionGuidanceScore",
							id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TfstudentCompetitionGuidanceScore instance) {
		log.debug("finding TfstudentCompetitionGuidanceScore instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.nuaa.ec.model.TfstudentCompetitionGuidanceScore")
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
		log.debug("finding TfstudentCompetitionGuidanceScore instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TfstudentCompetitionGuidanceScore as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBaseScore(Object baseScore) {
		return findByProperty(BASE_SCORE, baseScore);
	}

	public List findBySpareTire(Object spareTire) {
		return findByProperty(SPARE_TIRE, spareTire);
	}

	public List findAll() {
		log.debug("finding all TfstudentCompetitionGuidanceScore instances");
		try {
			String queryString = "from TfstudentCompetitionGuidanceScore where spareTire=1";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TfstudentCompetitionGuidanceScore merge(
			TfstudentCompetitionGuidanceScore detachedInstance) {
		log.debug("merging TfstudentCompetitionGuidanceScore instance");
		try {
			TfstudentCompetitionGuidanceScore result = (TfstudentCompetitionGuidanceScore) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TfstudentCompetitionGuidanceScore instance) {
		log.debug("attaching dirty TfstudentCompetitionGuidanceScore instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TfstudentCompetitionGuidanceScore instance) {
		log.debug("attaching clean TfstudentCompetitionGuidanceScore instance");
		try {
			getSession().buildLockRequest(LockOptions.NONE).lock(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}