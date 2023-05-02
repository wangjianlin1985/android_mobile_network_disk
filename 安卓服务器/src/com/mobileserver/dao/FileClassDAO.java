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
	/* �����ĵ�������󣬽����ĵ���������ҵ�� */
	public String AddFileClass(FileClass fileClass) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в������ĵ����� */
			String sqlString = "insert into FileClass(className) values (";
			sqlString += "'" + fileClass.getClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�ĵ�������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ĵ��������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���ĵ����� */
	public String DeleteFileClass(int classId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from FileClass where classId=" + classId;
			db.executeUpdate(sqlString);
			result = "�ĵ�����ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ĵ�����ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݷ���id��ȡ���ĵ����� */
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
	/* �����ĵ����� */
	public String UpdateFileClass(FileClass fileClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update FileClass set ";
			sql += "className='" + fileClass.getClassName() + "'";
			sql += " where classId=" + fileClass.getClassId();
			db.executeUpdate(sql);
			result = "�ĵ�������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�ĵ��������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
