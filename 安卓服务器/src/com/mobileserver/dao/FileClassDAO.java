package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.FileClass;
import com.mobileserver.util.DB;

public class FileClassDAO {

	public List<FileClass> QueryFileClass() {
		List<FileClass> fileClassList = new ArrayList<FileClass>();
		DB db = new DB();
		String sql = "select * from FileClass where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				FileClass fileClass = new FileClass();
				fileClass.setClassId(rs.getInt("classId"));
				fileClass.setClassName(rs.getString("className"));
				fileClassList.add(fileClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return fileClassList;
	}
	/* 传入文档分类对象，进行文档分类的添加业务 */
	public String AddFileClass(FileClass fileClass) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新文档分类 */
			String sqlString = "insert into FileClass(className) values (";
			sqlString += "'" + fileClass.getClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "文档分类添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文档分类添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除文档分类 */
	public String DeleteFileClass(int classId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from FileClass where classId=" + classId;
			db.executeUpdate(sqlString);
			result = "文档分类删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文档分类删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据分类id获取到文档分类 */
	public FileClass GetFileClass(int classId) {
		FileClass fileClass = null;
		DB db = new DB();
		String sql = "select * from FileClass where classId=" + classId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				fileClass = new FileClass();
				fileClass.setClassId(rs.getInt("classId"));
				fileClass.setClassName(rs.getString("className"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return fileClass;
	}
	/* 更新文档分类 */
	public String UpdateFileClass(FileClass fileClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update FileClass set ";
			sql += "className='" + fileClass.getClassName() + "'";
			sql += " where classId=" + fileClass.getClassId();
			db.executeUpdate(sql);
			result = "文档分类更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "文档分类更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
