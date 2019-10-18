package com.spring.boot.model;

import java.util.ArrayList;

public class DataBeanMarksList {
	public ArrayList<DataMarksBean> getDataBeanList() {
		ArrayList<DataMarksBean> dataBeanList = new ArrayList<DataMarksBean>();
		dataBeanList.add(produce("English", 58));
		dataBeanList.add(produce("SocialStudies", 68));
		dataBeanList.add(produce("Maths", 38));
		dataBeanList.add(produce("Hindi", 88));
		dataBeanList.add(produce("Scince", 78));
		return dataBeanList;
	}
 
	/*
	 * This method returns a DataBean object, with subjectName and marks set
	 * in it.
	 */
	private DataMarksBean produce(String subjectName, Integer marks) {
		DataMarksBean dataBean = new DataMarksBean();
 
		dataBean.setSubjectName(subjectName);
		dataBean.setMarks(marks);
 
		return dataBean;
	}
}
