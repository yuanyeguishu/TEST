package com.nuaa.ec.teachingData.exportData;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import com.nuaa.ec.dao.TftermDAO;
import com.nuaa.ec.model.TfdegreeThesisGuidancePerformanceUnionTfterm;
import com.nuaa.ec.model.TfjoinStudentActivityPerformanceUnionTfterm;
import com.nuaa.ec.model.TfteachingCompetitionPerformanceUnionTfterm;

public class JoinStudentActivityExcel {
	/**
	 * function：导出参与学生活动数据
	 * @param ths 导出数据的字段
	 * @param departmentName 研究所名称
	 * @param foredate 下限日期
	 * @param afterdate 上限日期
	 * @return HSSFWorkbook
	 */
	@SuppressWarnings("deprecation")
	public static HSSFWorkbook  generateExcel(String[] ths,List<TfjoinStudentActivityPerformanceUnionTfterm> tfJoinStudentActivityPerfList,String researchLabName,String foredate,String afterdate){
		HSSFWorkbook wb = new HSSFWorkbook();
		TftermDAO tftermDAO=new TftermDAO();
		HSSFSheet sheet = wb.createSheet(tftermDAO.findById(foredate).getTerm()+"-"+tftermDAO.findById(afterdate).getTerm());
		//设置列宽
		for(int i=0;i<ths.length;i++){
			sheet.setColumnWidth((short) i, 5000);
		}
//  合并单元格  org.apache.poi.hssf.util.Region(short rowFrom,short colFrom,short rowTo,short colTo)
		org.apache.poi.hssf.util.Region reg = new org.apache.poi.hssf.util.Region((short)0,(short)0,(short)1,(short)(ths.length-1));
		sheet.addMergedRegion(reg);
		//创建预备居中格式
		HSSFCellStyle cellStyle = wb.createCellStyle();
		//创建预备居中大字体格式
		HSSFCellStyle cellStyleforFonts = wb.createCellStyle();
		//设置格式居中
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyleforFonts.setAlignment(CellStyle.ALIGN_CENTER);
		//创建字体
		HSSFFont font = wb.createFont(); 
		//设置字体大小
		font.setFontHeightInPoints((short)14);
		//合并字体到 居中大字体格式
		cellStyleforFonts.setFont(font);
		int rownum = 0;
		HSSFRow row = sheet.createRow((short) rownum++);
		HSSFCell cells[] = new HSSFCell[1];
		cells[0] = row.createCell(0);
		cells[0].setCellValue("参与学生活动["+researchLabName+"]");
		//应用格式
		cells[0].setCellStyle(cellStyleforFonts);
		rownum++;
		row = sheet.createRow((short) rownum++);
		HSSFCell cell[] = new HSSFCell[ths.length];
		for(int i=0;i<ths.length;i++){
			cell[i] = row.createCell(i);
			cell[i].setCellValue(ths[i]);
			cell[i].setCellStyle(cellStyle);
		}
		if(tfJoinStudentActivityPerfList!=null){
			for(int i=0;i<tfJoinStudentActivityPerfList.size();i++){
				row = sheet.createRow(rownum++);
				cell = new HSSFCell[ths.length];
				for(int p=0;p<ths.length;p++){
					cell[p] = row.createCell(p);
				}
/**
 * 导出字段：
 * {"参与活动编号","活动名称","参与时长/h","参与总时长","当前教师工号","当前教师姓名","教师绩效"};
 */
				cell[0].setCellValue(tfJoinStudentActivityPerfList.get(i).getJoinStudentActivityPerformance().getActivityId());
				cell[0].setCellStyle(cellStyle);
				cell[1].setCellValue(tfJoinStudentActivityPerfList.get(i).getJoinStudentActivityPerformance().getActivityName());
				cell[1].setCellStyle(cellStyle);
				cell[2].setCellValue(tfJoinStudentActivityPerfList.get(i).getJoinStudentActivityPerformance().getTfjoinStudentActivityTime().getTimeUnit());
				cell[2].setCellStyle(cellStyle);
				cell[3].setCellValue(tfJoinStudentActivityPerfList.get(i).getJoinStudentActivityPerformance().getSumhours());
				cell[3].setCellStyle(cellStyle);
				cell[4].setCellValue(tfJoinStudentActivityPerfList.get(i).getJoinStudentActivityPerformance().getTeacher().getTeacherId());
				cell[4].setCellStyle(cellStyle);
				cell[5].setCellValue(tfJoinStudentActivityPerfList.get(i).getJoinStudentActivityPerformance().getTeacher().getTeacherName());
				cell[5].setCellStyle(cellStyle);
				cell[6].setCellValue(tfJoinStudentActivityPerfList.get(i).getJoinStudentActivityPerformance().getFinalScore());
				cell[6].setCellStyle(cellStyle);
			}
		}
		return wb;
	}
}
