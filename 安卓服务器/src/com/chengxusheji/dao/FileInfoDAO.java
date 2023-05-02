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
import com.chengxusheji.domain.YesOrNo;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.domain.FileInfo;

@Service @Transactional
public class FileInfoDAO {

	@Resource SessionFactory factory;
    /*ÿҳ��ʾ��¼��Ŀ*/
    private final int PAGE_SIZE = 10;

    /*�����ѯ���ܵ�ҳ��*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*�����ѯ�����ܼ�¼��*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*���ͼ����Ϣ*/
    public void AddFileInfo(FileInfo fileInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(fileInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<FileInfo> QueryFileInfoInfo(FileClass fileClassObj,String fileName,YesOrNo privateFlag,UserInfo userObj,String upTime,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From FileInfo fileInfo where 1=1";
    	if(null != fileClassObj && fileClassObj.getClassId()!=0) hql += " and fileInfo.fileClassObj.classId=" + fileClassObj.getClassId();
    	if(!fileName.equals("")) hql = hql + " and fileInfo.fileName like '%" + fileName + "%'";
    	if(null != privateFlag && privateFlag.getYesNoId()!=0) hql += " and fileInfo.privateFlag.yesNoId=" + privateFlag.getYesNoId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and fileInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!upTime.equals("")) hql = hql + " and fileInfo.upTime like '%" + upTime + "%'";
    	Query q = s.createQuery(hql);
    	/*���㵱ǰ��ʾҳ��Ŀ�ʼ��¼*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List fileInfoList = q.list();
    	return (ArrayList<FileInfo>) fileInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<FileInfo> QueryFileInfoInfo(FileClass fileClassObj,String fileName,YesOrNo privateFlag,UserInfo userObj,String upTime) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From FileInfo fileInfo where 1=1";
    	if(null != fileClassObj && fileClassObj.getClassId()!=0) hql += " and fileInfo.fileClassObj.classId=" + fileClassObj.getClassId();
    	if(!fileName.equals("")) hql = hql + " and fileInfo.fileName like '%" + fileName + "%'";
    	if(null != privateFlag && privateFlag.getYesNoId()!=0) hql += " and fileInfo.privateFlag.yesNoId=" + privateFlag.getYesNoId();
    	if(null != userObj && !userObj.getUser_name().equals("")) hql += " and fileInfo.userObj.user_name='" + userObj.getUser_name() + "'";
    	if(!upTime.equals("")) hql = hql + " and fileInfo.upTime like '%" + upTime + "%'";
    	Query q = s.createQuery(hql);
    	List fileInfoList = q.list();
    	return (ArrayList<FileInfo>) fileInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<FileInfo> QueryAllFileInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From FileInfo";
        Query q = s.createQuery(hql);
        List fileInfoList = q.list();
        return (ArrayList<FileInfo>) fileInfoList;
    }

    /*�����ܵ�ҳ���ͼ�¼��*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(FileClass fileClassObj,String fileName,YesOrNo privateFlag,UserInfo userObj,String upTime) {
        Session s = factory.getCurrentSession();
        String hql = "From FileInfo fileInfo where 1=1";
        if(null != fileClassObj && fileClassObj.getClassId()!=0) hql += " and fileInfo.fileClassObj.classId=" + fileClassObj.getClassId();
        if(!fileName.equals("")) hql = hql + " and fileInfo.fileName like '%" + fileName + "%'";
        if(null != privateFlag && privateFlag.getYesNoId()!=0) hql += " and fileInfo.privateFlag.yesNoId=" + privateFlag.getYesNoId();
        if(null != userObj && !userObj.getUser_name().equals("")) hql += " and fileInfo.userObj.user_name='" + userObj.getUser_name() + "'";
        if(!upTime.equals("")) hql = hql + " and fileInfo.upTime like '%" + upTime + "%'";
        Query q = s.createQuery(hql);
        List fileInfoList = q.list();
        recordNumber = fileInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*����������ȡ����*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public FileInfo GetFileInfoByFileId(int fileId) {
        Session s = factory.getCurrentSession();
        FileInfo fileInfo = (FileInfo)s.get(FileInfo.class, fileId);
        return fileInfo;
    }

    /*����FileInfo��Ϣ*/
    public void UpdateFileInfo(FileInfo fileInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(fileInfo);
    }

    /*ɾ��FileInfo��Ϣ*/
    public void DeleteFileInfo (int fileId) throws Exception {
        Session s = factory.getCurrentSession();
        Object fileInfo = s.load(FileInfo.class, fileId);
        s.delete(fileInfo);
    }

}
