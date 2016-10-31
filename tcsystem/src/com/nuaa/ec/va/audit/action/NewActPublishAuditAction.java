package com.nuaa.ec.va.audit.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.hibernate.Transaction;

import com.nuaa.ec.dao.TeacherDAO;
import com.nuaa.ec.dao.VacollectiveActivitiesPublishDAO;
import com.nuaa.ec.model.ResearchLab;
import com.nuaa.ec.model.VacollectiveActivitiesPublish;
import com.nuaa.ec.model.VateacherAndCollectiveAct;
import com.nuaa.ec.model.VateacherAndCollectiveActId;
import com.opensymphony.xwork2.ActionContext;

public class NewActPublishAuditAction implements RequestAware{
	public void doCheckOutTask(){
		String[] ids = this.checkOutIDs.split(",");
		String[] idsNot=this.checkOutIDsNot.split(",");
		List<VacollectiveActivitiesPublish> checkoutList = new ArrayList<VacollectiveActivitiesPublish>();
		VacollectiveActivitiesPublish vacollectiveActivitiesPublish = null;
		for (int i = 0; i < ids.length; i++) {
			if (ids[i]!=null && ids[i].length()!=0) {
				vacollectiveActivitiesPublish = this.vacollectiveActivitiesPublishDAO.findById(ids[i]);
				
				// 修改checkout 标志
				if (vacollectiveActivitiesPublish!=null) {
					vacollectiveActivitiesPublish.setAspareTire("1");
					checkoutList.add(vacollectiveActivitiesPublish);
				}
			}
		}
		for (int i = 0; i < idsNot.length; i++) {
			if (idsNot[i]!=null && idsNot[i].length()!=0 ) {
				
				vacollectiveActivitiesPublish=this.vacollectiveActivitiesPublishDAO.findById(idsNot[i]);
				if(vacollectiveActivitiesPublish!=null){
					vacollectiveActivitiesPublish.setAspareTire("2");
					checkoutList.add(vacollectiveActivitiesPublish);
			}
		}
		
	}
		try {
			if (vacollectiveActivitiesPublishDAO.updateASparetire(checkoutList)) {
				ServletActionContext.getResponse().getWriter().write("succ");
			}else {
				ServletActionContext.getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public String getNewActPublishList(){
		Transaction tx = this.vacollectiveActivitiesPublishDAO.getSession()
				.beginTransaction();
		if ((ResearchLab) session.get("researchLab_CT") == null) {
			session.put("researchLab_CT", new ResearchLab());
		}
		if ((Integer) session.get("pageSize_CT") == null) {
			session.put("pageSize_CT", 1);
		}
		
		try {
			this.request.put("newActPulishList", this.vacollectiveActivitiesPublishDAO
					.getNewActPublishAct(pageIndex,
							(Integer) session.get("pageSize_CT"),
							(ResearchLab) session.get("researchLab_CT"),
							(String)session.get("checkOutStatus_CT"),
							false));
			tx.commit();
			this.setOperstatus(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.setOperstatus(-1);
			tx.rollback();
		}
		return "success";
	}
	
	public String getNewActPublishListAfterDivided(){
		Transaction tx = this.vacollectiveActivitiesPublishDAO.getSession()
				.beginTransaction();
		if ((ResearchLab) session.get("researchLab_CT") == null) {
			session.put("researchLab_CT", new ResearchLab());
		}
		if ((Integer) session.get("pageSize_CT") == null) {
			session.put("pageSize_CT", 1);
		}
		
		try {
			this.request.put("newActPulishList", this.vacollectiveActivitiesPublishDAO
					.getNewActPublishAct(pageIndex,
							(Integer) session.get("pageSize_CT"),
							(ResearchLab) session.get("researchLab_CT"),
							(String)session.get("checkOutStatus_CT"),
							true));
			tx.commit();
			this.setOperstatus(1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			this.setOperstatus(-1);
			tx.rollback();
		}
		return "success";
	}
	
	private int pageIndex = 1;
	private int pageSize_CT;
	private ResearchLab researchLab_CT;
	private int operstatus;
	private Map<String, Object> session = ActionContext.getContext()
			.getSession();
	private Map<String, Object> request;
	private String checkOutStatus_CT;
	private String checkOutIDs;
	private String checkOutIDsNot;
	private VacollectiveActivitiesPublishDAO vacollectiveActivitiesPublishDAO = new VacollectiveActivitiesPublishDAO();
	
	public void setRequest(Map<String, Object> request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize_CT() {
		return pageSize_CT;
	}

	public void setPageSize_CT(int pageSize_CT) {
		this.pageSize_CT = pageSize_CT;
		session.put("pageSize_CT", pageSize_CT);
	}

	public ResearchLab getResearchLab_CT() {
		return researchLab_CT;
	}

	public void setResearchLab_CT(ResearchLab researchLab_CT) {
		this.researchLab_CT = researchLab_CT;
		session.put("researchLab_CT", researchLab_CT);
	}

	public int getOperstatus() {
		return operstatus;
	}

	public void setOperstatus(int operstatus) {
		this.operstatus = operstatus;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public String getCheckOutStatus_CT() {
		return checkOutStatus_CT;
	}

	public void setCheckOutStatus_CT(String checkOutStatus_CT) {
		this.checkOutStatus_CT = checkOutStatus_CT;
		session.put("checkOutStatus_CT", checkOutStatus_CT);
	}

	public String getCheckOutIDs() {
		return checkOutIDs;
	}

	public void setCheckOutIDs(String checkOutIDs) {
		this.checkOutIDs = checkOutIDs;
	}

	public String getCheckOutIDsNot() {
		return checkOutIDsNot;
	}

	public void setCheckOutIDsNot(String checkOutIDsNot) {
		this.checkOutIDsNot = checkOutIDsNot;
	}

	public VacollectiveActivitiesPublishDAO getVacollectiveActivitiesPublishDAO() {
		return vacollectiveActivitiesPublishDAO;
	}

	public void setVacollectiveActivitiesPublishDAO(
			VacollectiveActivitiesPublishDAO vacollectiveActivitiesPublishDAO) {
		this.vacollectiveActivitiesPublishDAO = vacollectiveActivitiesPublishDAO;
	}

	public Map<String, Object> getRequest() {
		return request;
	}
	
	
}