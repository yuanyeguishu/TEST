package com.nuaa.ec.utils;

import javax.persistence.Entity;

import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.PersistentClass;

import com.nuaa.ec.dao.SessionFactory;

public class EntityUtil {

	private static Configuration hibernateconfig = SessionFactory
			.getConfiguration();

	private static PersistentClass getPersistentclass(Class clazz) {
		synchronized (Entity.class) {
			PersistentClass pc = hibernateconfig.getClassMapping(clazz
					.getName());
			if (pc == null) {
				hibernateconfig = hibernateconfig.addClass(clazz);
				pc = hibernateconfig.getClassMapping(clazz.getName());
			}
			return pc;
		}
	}

	/***
	 * get table - name
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getTableName(Class clazz) {
		return getPersistentclass(clazz).getTable().getName();
	}

	/***
	 * get pk - column - name
	 * 
	 * @param clazz
	 * @return
	 */
	public static String getPkColumnName(Class clazz) {
		return getPersistentclass(clazz).getTable().getPrimaryKey()
				.getColumn(0).getName();
	}

	//TODO: Utils meth0d
		public static String generateQueryCondition(String foredate,String afterdate,String colname){
			StringBuffer condition = new StringBuffer();
			condition.append("AND");
			if(foredate!=null){
				if(!"".equals(foredate.trim()))
					condition.append(" "+colname+">='"+foredate+"' AND");
				if(afterdate!=null){
					if(!"".equals(afterdate.trim()))
						condition.append(" "+colname+"<='"+afterdate+"' AND");
				}
			}else{
				if(afterdate!=null){
					if(!"".equals(afterdate.trim()))
						condition.append(" "+colname+"<='"+afterdate+"' AND");
				}
			}
			return condition.substring(0, condition.length()-3);
		}
}