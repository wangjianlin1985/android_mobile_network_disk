package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.FileClass;

@Service @Transactional
public class FileClassDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddFileClass(FileClass fileClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(fileClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<FileClass> QueryFileClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From FileClass fileClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List fileClassList = q.list();
    	return (ArrayList<FileClass>) fileClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<FileClass> QueryFileClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From FileClass fileClass where 1=1";
    	Query q = s.createQuery(hql);
    	List fileClassList = q.list();
    	return (ArrayList<FileClass>) fileClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<FileClass> QueryAllFileClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From FileClass";
        Query q = s.createQuery(hql);
        List fileClassList = q.list();
        return (ArrayList<FileClass>) fileClassList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From FileClass fileClass where 1=1";
        Query q = s.createQuery(hql);
        List fileClassList = q.list();
        recordNumber = fileClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public FileClass GetFileClassByClassId(int classId) {
        Session s = factory.getCurrentSession();
        FileClass fileClass = (FileClass)s.get(FileClass.class, classId);
        return fileClass;
    }

    /*更新FileClass信息*/
    public void UpdateFileClass(FileClass fileClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(fileClass);
    }

    /*删除FileClass信息*/
    public void DeleteFileClass (int classId) throws Exception {
        Session s = factory.getCurrentSession();
        Object fileClass = s.load(FileClass.class, classId);
        s.delete(fileClass);
    }

}
