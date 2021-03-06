package com.nuaa.ec.ScientificResearchPerformanceSet.Action;

import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Transaction;

import com.nuaa.ec.dao.ProjectTypeDAO;
import com.nuaa.ec.dao.ScientificResearchProjectDAO;
import com.nuaa.ec.dao.ScientificResearchProjectScoreDAO;
import com.nuaa.ec.dao.SelfUndertakeTaskDAO;
import com.nuaa.ec.dao.TeacherAndscientificResearchProjectDAO;
import com.nuaa.ec.model.ProjectType;
import com.nuaa.ec.model.ScientificResearchProject;
import com.nuaa.ec.model.ScientificResearchProjectScore;
import com.nuaa.ec.model.SelfUndertakeTask;
import com.nuaa.ec.model.Teacher;
import com.nuaa.ec.model.TeacherAndscientificResearchProject;
import com.nuaa.ec.utils.EntityUtil;

public class abminscientificresearchproAction implements RequestAware,
		SessionAware {

	private Map<String, Object> session;
	private Map<String, Object> request;
	private String foredate;
	private String afterdate;
	private Integer operstatus;
	private ScientificResearchProject scienrhprojec;
	private ScientificResearchProjectScore scienrhprojecscore;
	private ProjectType projectype;
	private TeacherAndscientificResearchProject teacherandsrp;
	private SelfUndertakeTask selftask;

	private ScientificResearchProjectDAO scienrhprojecdao = new ScientificResearchProjectDAO();
	private ScientificResearchProjectScoreDAO scienrhprojecscoredao = new ScientificResearchProjectScoreDAO();
	private ProjectTypeDAO projectypedao = new ProjectTypeDAO();
	private TeacherAndscientificResearchProjectDAO teacherandsrpdao = new TeacherAndscientificResearchProjectDAO();
	private SelfUndertakeTaskDAO selftaskdao = new SelfUndertakeTaskDAO();
	// default method
	public String execute() {
		return "success";
	}

	// 项目设置
	public String gainAllscienpro() throws Exception {
		int pagenum = 1;
		int limitrow = 5;
		String limit = (String) ServletActionContext.getRequest().getParameter(
				"limit");
		String pagenumber = (String) ServletActionContext.getRequest()
				.getParameter("pagenum");
		if (pagenumber != null) {
			pagenum = !"".equals(pagenumber.trim()) ? Integer
					.parseInt(pagenumber) : 1;
		}
		if (limit != null) {
			limitrow = !"".equals(limit.trim()) ? Integer.parseInt(limit) : 5;
		}
		request.put("scienrproject", scienrhprojecdao.findPageing((pagenum - 1)
				* limitrow, limitrow, EntityUtil.generateQueryCondition(
				foredate, afterdate, "admitedProjectYear")));
		request.put("projectType", projectypedao.findAll());
		request.put("selfdown", selftaskdao.findAll());
		int li = scienrhprojecdao.getRows(EntityUtil.generateQueryCondition(
				foredate, afterdate, "admitedProjectYear"));
		int sumpage = 1;
		if (li % limitrow == 0) {
			sumpage = li / limitrow;
		} else {
			sumpage = li / limitrow + 1;
		}
		request.put("sumrow", li);
		request.put("sumpage", sumpage);
		if (pagenum < sumpage) {
			request.put("nextpage", 1 + pagenum);
		} else {
			request.put("nextpage", pagenum);
		}
		if (pagenum > 1) {
			request.put("prepage", pagenum - 1);
		} else {
			request.put("prepage", 1);
		}
		request.put("pagenum", pagenum);
		return "success";
	}

	public void updateSRProject()throws Exception{
		Transaction tx =null;
		try {
			scienrhprojecscore = scienrhprojecscoredao.findByProType(projectype);
			if(scienrhprojecscore!=null){
				scienrhprojec.setProjectType(projectypedao.findById(projectype.getProjectTypeId()));
				scienrhprojec.setSpareTire("1");
				scienrhprojec.setResearchLabId(EntityUtil.findReasearchLabIdByTeacherId(((Teacher)session.get("teacher")).getTeacherId(),
						scienrhprojecdao.getSession()));
				scienrhprojecdao.merge(scienrhprojec);
				teacherandsrpdao.updateRef(scienrhprojecscore, scienrhprojec);
			}else{
				ServletActionContext.getResponse().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().getWriter().write("项目类型无对应评分项");
				return ;
			}
			tx = scienrhprojecdao.getSession().beginTransaction();
			tx.commit();
			ServletActionContext.getResponse().getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw e;
		}
	}
	
	public void deleteSRProject()throws Exception{
		Transaction tx = null;
		try {
			scienrhprojecdao.deleteBylogic(scienrhprojec.getSrprojectId());
			teacherandsrpdao.deleteRef(scienrhprojec);
			tx = scienrhprojecdao.getSession().beginTransaction();
			tx.commit();
			ServletActionContext.getResponse().getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw e;
		}
	}
	
	public void getMember()throws Exception{
		try {
			JSONArray jsay = JSONArray.fromObject(scienrhprojecdao.findMember(scienrhprojec));
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().getWriter().write(jsay.toString());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw e;
		}
	}
	
	public void deleteMember()throws Exception{
		Transaction tx = null;
		try {
			this.setScienrhprojec(scienrhprojecdao.findById(scienrhprojec.getSrprojectId()));
			if(!scienrhprojec.getChargePersonId().trim().equals(teacherandsrp.getTeacher().getTeacherId())){
				teacherandsrpdao.quitProject(scienrhprojec, teacherandsrp.getTeacher());
			}else{
				ServletActionContext.getResponse().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().getWriter().write("负责人不能移除");
				return;
			}
			tx = teacherandsrpdao.getSession().beginTransaction();
			tx.commit();
			ServletActionContext.getResponse().getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw e;
		}
	}
	// Getter & Setter
	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public String getForedate() {
		return foredate;
	}

	public void setForedate(String foredate) {
		this.foredate = foredate;
	}

	public String getAfterdate() {
		return afterdate;
	}

	public void setAfterdate(String afterdate) {
		this.afterdate = afterdate;
	}

	public Integer getOperstatus() {
		return operstatus;
	}

	public void setOperstatus(Integer operstatus) {
		this.operstatus = operstatus;
	}

	public ScientificResearchProject getScienrhprojec() {
		return scienrhprojec;
	}

	public void setScienrhprojec(ScientificResearchProject scienrhprojec) {
		this.scienrhprojec = scienrhprojec;
	}

	public ScientificResearchProjectScore getScienrhprojecscore() {
		return scienrhprojecscore;
	}

	public void setScienrhprojecscore(
			ScientificResearchProjectScore scienrhprojecscore) {
		this.scienrhprojecscore = scienrhprojecscore;
	}

	public ProjectType getProjectype() {
		return projectype;
	}

	public void setProjectype(ProjectType projectype) {
		this.projectype = projectype;
	}

	public TeacherAndscientificResearchProject getTeacherandsrp() {
		return teacherandsrp;
	}

	public void setTeacherandsrp(
			TeacherAndscientificResearchProject teacherandsrp) {
		this.teacherandsrp = teacherandsrp;
	}

	public SelfUndertakeTask getSelftask() {
		return selftask;
	}

	public void setSelftask(SelfUndertakeTask selftask) {
		this.selftask = selftask;
	}

}
