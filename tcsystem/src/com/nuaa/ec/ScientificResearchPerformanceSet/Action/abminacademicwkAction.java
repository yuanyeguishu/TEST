package com.nuaa.ec.ScientificResearchPerformanceSet.Action;

import java.util.Map;



import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.hibernate.Transaction;

import com.nuaa.ec.dao.AcademicWorkDAO;
import com.nuaa.ec.dao.AcademicWorkScoreDAO;
import com.nuaa.ec.dao.PublishClubDAO;
import com.nuaa.ec.dao.SelfUndertakeTaskDAO;
import com.nuaa.ec.dao.TeacherAndacademicWorkDAO;
import com.nuaa.ec.dao.WordsNumberDAO;
import com.nuaa.ec.model.AcademicWork;
import com.nuaa.ec.model.AcademicWorkScore;
import com.nuaa.ec.model.SelfUndertakeTask;
import com.nuaa.ec.model.Teacher;
import com.nuaa.ec.model.TeacherAndacademicWork;
import com.nuaa.ec.utils.EntityUtil;

public class abminacademicwkAction implements RequestAware, SessionAware {
	private Map<String, Object> session;
	private Map<String, Object> request;
	private String foredate;
	private String afterdate;
	private Integer operstatus;
	private AcademicWork academicwk;
	private TeacherAndacademicWork teacherandaw;
	private SelfUndertakeTask selftask;

	private AcademicWorkDAO academicwkdao = new AcademicWorkDAO();
	private TeacherAndacademicWorkDAO teacherandawdao = new TeacherAndacademicWorkDAO();
	private AcademicWorkScoreDAO academicscoredao = new AcademicWorkScoreDAO();
	private WordsNumberDAO wordnumdao = new WordsNumberDAO();
	private PublishClubDAO publishdao = new PublishClubDAO();
	private SelfUndertakeTaskDAO selftaskdao = new SelfUndertakeTaskDAO();

	// default method
	public String execute() {
		return "success";
	}

	// TODO: 学术著作设置
	public String getWorkall() throws Exception {
		int pagenum = 1;
		int limitrow = 5;
		String limit = (String) ServletActionContext.getRequest().getParameter("limit");
		String pagenumber = (String) ServletActionContext.getRequest().getParameter("pagenum");
		if (pagenumber != null) {
			pagenum = !"".equals(pagenumber.trim()) ? Integer.parseInt(pagenumber) : 1;
		}
		if (limit != null) {
			limitrow = !"".equals(limit.trim()) ? Integer.parseInt(limit) : 5;
		}
		request.put("academicwk", academicwkdao.findAll((pagenum - 1)* limitrow, limitrow, EntityUtil.generateQueryCondition(foredate, afterdate, "aw.publishDate")));
		request.put("publishclubli", publishdao.findAll());
		request.put("wordnum", wordnumdao.findAll());
		request.put("selfdown", selftaskdao.findAll());
		int li = academicwkdao.getRows(EntityUtil.generateQueryCondition(foredate, afterdate, "aw.publishDate"));
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

	//更新academicwork
		public void updateAcademicWork() throws Exception {
			Transaction tx = null;
			try {
				academicwk.setPublishClub(publishdao.findById(academicwk.getPublishClub().getPublishClubId()));
				academicwk.setSpareTire("1");
				academicwk.setWordsNumber(wordnumdao.findById(academicwk.getWordsNumber().getWordId()));
				academicwk.setResearchLabId(EntityUtil.findReasearchLabIdByTeacherId(((Teacher)session.get("teacher")).getTeacherId(), academicscoredao.getSession()));
				AcademicWorkScore awscore = academicscoredao.findByWordNum(academicwk.getWordsNumber());
				if(awscore!=null){
					teacherandawdao.updateRefTeacher(academicwk, awscore, awscore.getScore());
					academicwkdao.merge(academicwk);
					ServletActionContext.getResponse().getWriter().write("succ");
				}else{
					ServletActionContext.getResponse().setCharacterEncoding("utf-8");
					ServletActionContext.getResponse().getWriter().write("字数信息选择项-对应评分不存在");
					return;
				}
				tx = academicwkdao.getSession().beginTransaction();
				tx.commit();
			} catch (Exception e) {
				// TODO: handle exception
				tx.rollback();
				throw e;
			}
		}

		public void deleteAcademicWork() throws Exception{
			Transaction tx = null;
			try {
				this.setAcademicwk(academicwkdao.findById(academicwk.getAcaworkId()));
				academicwk.setSpareTire("0");
				academicwkdao.merge(academicwk);
				teacherandawdao.deleteRefTeacher(academicwk);
				tx = academicwkdao.getSession().beginTransaction();
				tx.commit();
				ServletActionContext.getResponse().getWriter().write("succ");
			} catch (Exception e) {
				// TODO: handle exception
				tx.rollback();
				throw e;
			}
		}
		
		public void getMember() throws Exception{
			try {
//				JsonConfig config = new JsonConfig();
//				config.setExcludes(new String[]{"teacher","periodicalPapersScore","periodical"});
				JSONArray jary = JSONArray.fromObject(academicwkdao.findMembersano(academicwk.getAcaworkId()));
				ServletActionContext.getResponse().setCharacterEncoding("utf-8");
				ServletActionContext.getResponse().getWriter().write(jary.toString());
			} catch (Exception e) {
				// TODO: handle exception
				throw e;
			}
		}
		
	public void deleteMember()throws Exception{
		Transaction tx = null;
		try {
			TeacherAndacademicWork tmptawk = teacherandawdao.findByTeacherAndProject(academicwk, teacherandaw.getTeacher());
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			if(tmptawk.getSelfUndertakeTask().getUndertakeTaskName().trim().indexOf("负责")>=0){
				ServletActionContext.getResponse().getWriter().write("不能移除负责人");
				return;
			}else{
				teacherandawdao.quitAcademicWork(teacherandaw.getTeacher(), academicwkdao.findById(academicwk.getAcaworkId()));
			}
			tx = teacherandawdao.getSession().beginTransaction();
			tx.commit();
			ServletActionContext.getResponse().getWriter().write("succ");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			tx.rollback();
			throw e;
		}
	}
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

	public AcademicWork getAcademicwk() {
		return academicwk;
	}

	public void setAcademicwk(AcademicWork academicwk) {
		this.academicwk = academicwk;
	}

	public TeacherAndacademicWork getTeacherandaw() {
		return teacherandaw;
	}

	public void setTeacherandaw(TeacherAndacademicWork teacherandaw) {
		this.teacherandaw = teacherandaw;
	}

	public SelfUndertakeTask getSelftask() {
		return selftask;
	}

	public void setSelftask(SelfUndertakeTask selftask) {
		this.selftask = selftask;
	}

}
