package com.nuaa.ec.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Transaction;

import com.nuaa.ec.dao.DepartmentDAO;
import com.nuaa.ec.dao.NationalityDAO;
import com.nuaa.ec.dao.ResearchLabDAO;
import com.nuaa.ec.dao.TeacherDAO;
import com.nuaa.ec.model.Department;
import com.nuaa.ec.model.Nationality;
import com.nuaa.ec.model.ResearchLab;
import com.nuaa.ec.model.Teacher;
import com.nuaa.ec.utils.PrimaryKMaker;
import com.nuaa.ec.utils.StoreData;
import com.opensymphony.xwork2.ActionContext;

public class BaseSetAction implements SessionAware{

	private Department depart;
	private ResearchLab research;
	private Nationality nation;
	private Map<String, Object>session;
	private DepartmentDAO departdao = new DepartmentDAO();
	private ResearchLabDAO researchdao = new ResearchLabDAO();
	private NationalityDAO nationdao = new NationalityDAO();
	private TeacherDAO teacherdao = new TeacherDAO();
	private PrimaryKMaker pk = new PrimaryKMaker();

	// default method
	public String execute() {
		return "success";
	}
/**************************系设置*******departSet*****************************/
	public String getDepartinfo(){
		try {
			ServletActionContext.getRequest().setAttribute("Department", departdao.findAll());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "success";
	}
	
	public String adddepart(){
		depart.setDepartmentId(pk.mkpk("DepartmentID", "Department", "D"));
		depart.setSpareTire("1");
		Transaction tx = null;
		try {
			departdao.save(depart);
			tx = departdao.getSession().beginTransaction();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}finally{
			departdao.closeSession();
			try {
				ServletActionContext.getRequest().setAttribute("Department", departdao.findAll());
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			getDepartinfo();
		}
		return "success";
	}
	
	public void updateDepart(){
		Transaction tx = null;
		depart.setDepartAdminId(depart.getDepartAdmin().substring(0, 9));
		depart.setSpareTire("1");
		if(teacherdao.chekTeacherInDepart(depart.getDepartmentId(), depart.getDepartAdminId())==1){
			try {
				departdao.update(depart);
				teacherdao.updateDepartStatus(depart.getDepartAdminId(),depart.getDepartmentId());
				tx = departdao.getSession().beginTransaction();
				tx.commit();
				ServletActionContext.getResponse().getWriter().write("succ");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tx.rollback();
			}finally{
				departdao.closeSession();
			}
		}else{
			try {
				ServletActionContext.getResponse().getWriter().write("notin");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteDepart(){
		this.setDepart(departdao.findById(depart.getDepartmentId()));
		depart.setSpareTire("0");
		Transaction tx = null;
		try {
			departdao.merge(depart);
			tx = departdao.getSession().beginTransaction();
			tx.commit();
			ServletActionContext.getResponse().getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			try {
				ServletActionContext.getResponse().getWriter().write("del_fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			departdao.closeSession();
		}
	}
/**************************研究所置*******ResearchLabSet*****************************/
	public String getResearchLabinfo(){
		try {
			ServletActionContext.getRequest().setAttribute("ResearchLab", researchdao.findAll());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "success";
	}
	
	public String addResearchLab(){
		research.setResearchLabId(pk.mkpk("ResearchLabID", "ResearchLab", "R"));
		research.setSpareTire("1");
		Transaction tx = null;
		try {
			researchdao.save(research);
			tx = researchdao.getSession().beginTransaction();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}finally{
			researchdao.closeSession();
			getResearchLabinfo();
		}
		return "success";
	}
	
	public void updateResearchLab(){
		Transaction tx = null;
		research.setResearchLabAdminId(research.getResearchLabAdmin().substring(0, 9));
		research.setSpareTire("1");
		if(teacherdao.chekTeacherInResearchLab(research.getResearchLabId(), research.getResearchLabAdminId())==1){
			try {
				researchdao.merge(research);
				teacherdao.updateResearchStatus(research.getResearchLabId(),research.getResearchLabAdminId());
				tx = researchdao.getSession().beginTransaction();
				tx.commit();
				ServletActionContext.getResponse().getWriter().write("succ");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				tx.rollback();
			}finally{
				researchdao.closeSession();
			}
		}else{
			try {
				ServletActionContext.getResponse().getWriter().write("notin");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void deleteResearch(){
		this.setResearch(researchdao.findById(research.getResearchLabId()));
		research.setSpareTire("0");
		Transaction tx = null;
		try {
			researchdao.merge(research);
			tx = researchdao.getSession().beginTransaction();
			tx.commit();
			ServletActionContext.getResponse().getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			try {
				ServletActionContext.getResponse().getWriter().write("del_fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			researchdao.closeSession();
		}
	}
	/**************************国籍所置*******NationalitySet*****************************/
	public String getNationalityinfo(){
		try {
			ServletActionContext.getRequest().setAttribute("Nationality", nationdao.findAll());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "success";
	}
	
	public String addNationality(){
		nation.setCountryId(pk.mkpk("CountryID", "Nationality", "N"));
		nation.setSpareTire("1");
		Transaction tx = null;
		try {
			nationdao.save(nation);
			tx = nationdao.getSession().beginTransaction();
			tx.commit();
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
		}finally{
			nationdao.closeSession();
			getNationalityinfo();
		}
		return "success";
	}
	
	public void updateNationality(){
		Transaction tx = null;
		nation.setSpareTire("1");
		HttpServletResponse resp = (HttpServletResponse) ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
		try {
			nationdao.merge(nation);
			tx = nationdao.getSession().beginTransaction();
			tx.commit();
			resp.getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			tx.rollback();
			e.printStackTrace();
			try {
				resp.getWriter().write("fail");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			nationdao.closeSession();
		}
	}
	
	public void deletenation(){
		Transaction tx = null;
		nation.setSpareTire("0");
		HttpServletResponse resp = (HttpServletResponse)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_RESPONSE);
		try {
			nationdao.merge(nation);
			tx = nationdao.getSession().beginTransaction();
			tx.commit();
			resp.getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
		}finally{
			nationdao.closeSession();
		}
	}
	/**************************Getter&Setter*****************************/
	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public ResearchLab getResearch() {
		return research;
	}

	public void setResearch(ResearchLab research) {
		this.research = research;
	}

	public Nationality getNation() {
		return nation;
	}

	public void setNation(Nationality nation) {
		this.nation = nation;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
