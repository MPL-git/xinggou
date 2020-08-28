

package com.jm.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 表数据
 *
 *
 */
public class TableEntity {
	//表的名称
	private String tableName;
	//表的备注
	private String comments;
	//表的主键
	private ColumnEntity pk;
	//表的列名(不包含主键)
	private List<ColumnEntity> columns;
	
	//类名(第一个字母大写)，如：sys_user => SysUser
	private String className;
	//类名(第一个字母小写)，如：sys_user => sysUser
	private String classname;

	private String moduleName;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public ColumnEntity getPk() {
		return pk;
	}
	public void setPk(ColumnEntity pk) {
		this.pk = pk;
	}
	public List<ColumnEntity> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnEntity> columns) {
		this.columns = columns;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}


	public List<ColumnEntity> getListColumns() {
		List<ColumnEntity> list = new ArrayList<>();
		for(ColumnEntity column: columns){
			if(!column.getColumnName().equals("del_flag")
					&& !column.getColumnName().equals("id")
					&& !column.getColumnName().equals("create_time")
					&& !column.getColumnName().equals("update_time")
					&& !column.getColumnName().equals("remark")){
				list.add(column);
			}
		}
		return list;
	}

	public List<ColumnEntity> getEditColumns() {
		List<ColumnEntity> list = new ArrayList<>();
		for(ColumnEntity column: columns){
			if(!column.getColumnName().equals("id")
					&& !column.getColumnName().equals("del_flag")
					&& !column.getColumnName().equals("create_time")
					&& !column.getColumnName().equals("update_time")
					&& !column.getColumnName().equals("remark")){
				list.add(column);
			}
		}
		return list;
	}
}
