package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.FileInfoDAO;
import com.chengxusheji.domain.FileInfo;
import com.chengxusheji.dao.FileClassDAO;
import com.chengxusheji.domain.FileClass;
import com.chengxusheji.dao.YesOrNoDAO;
import com.chengxusheji.domain.YesOrNo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class FileInfoAction extends BaseAction {

	/*ͼƬ���ļ��ֶ�filePhoto��������*/
	private File filePhotoFile;
	private String filePhotoFileFileName;
	private String filePhotoFileContentType;
	public File getFilePhotoFile() {
		return filePhotoFile;
	}
	public void setFilePhotoFile(File filePhotoFile) {
		this.filePhotoFile = filePhotoFile;
	}
	public String getFilePhotoFileFileName() {
		return filePhotoFileFileName;
	}
	public void setFilePhotoFileFileName(String filePhotoFileFileName) {
		this.filePhotoFileFileName = filePhotoFileFileName;
	}
	public String getFilePhotoFileContentType() {
		return filePhotoFileContentType;
	}
	public void setFilePhotoFileContentType(String filePhotoFileContentType) {
		this.filePhotoFileContentType = filePhotoFileContentType;
	}
	/*ͼƬ���ļ��ֶ�docFile��������*/
	private File docFileFile;
	private String docFileFileFileName;
	private String docFileFileContentType;
	public File getDocFileFile() {
		return docFileFile;
	}
	public void setDocFileFile(File docFileFile) {
		this.docFileFile = docFileFile;
	}
	public String getDocFileFileFileName() {
		return docFileFileFileName;
	}
	public void setDocFileFileFileName(String docFileFileFileName) {
		this.docFileFileFileName = docFileFileFileName;
	}
	public String getDocFileFileContentType() {
		return docFileFileContentType;
	}
	public void setDocFileFileContentType(String docFileFileContentType) {
		this.docFileFileContentType = docFileFileContentType;
	}
    /*�������Ҫ��ѯ������: �ĵ�����*/
    private FileClass fileClassObj;
    public void setFileClassObj(FileClass fileClassObj) {
        this.fileClassObj = fileClassObj;
    }
    public FileClass getFileClassObj() {
        return this.fileClassObj;
    }

    /*�������Ҫ��ѯ������: �ĵ�����*/
    private String fileName;
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return this.fileName;
    }

    /*�������Ҫ��ѯ������: �Ƿ񹫿�*/
    private YesOrNo privateFlag;
    public void setPrivateFlag(YesOrNo privateFlag) {
        this.privateFlag = privateFlag;
    }
    public YesOrNo getPrivateFlag() {
        return this.privateFlag;
    }

    /*�������Ҫ��ѯ������: �ϴ��û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: �ϴ�ʱ��*/
    private String upTime;
    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }
    public String getUpTime() {
        return this.upTime;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int fileId;
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    public int getFileId() {
        return fileId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource FileClassDAO fileClassDAO;
    @Resource YesOrNoDAO yesOrNoDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource FileInfoDAO fileInfoDAO;

    /*��������FileInfo����*/
    private FileInfo fileInfo;
    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
    public FileInfo getFileInfo() {
        return this.fileInfo;
    }

    /*��ת�����FileInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�FileClass��Ϣ*/
        List<FileClass> fileClassList = fileClassDAO.QueryAllFileClassInfo();
        ctx.put("fileClassList", fileClassList);
        /*��ѯ���е�YesOrNo��Ϣ*/
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���FileInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddFileInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            FileClass fileClassObj = fileClassDAO.GetFileClassByClassId(fileInfo.getFileClassObj().getClassId());
            fileInfo.setFileClassObj(fileClassObj);
            YesOrNo privateFlag = yesOrNoDAO.GetYesOrNoByYesNoId(fileInfo.getPrivateFlag().getYesNoId());
            fileInfo.setPrivateFlag(privateFlag);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(fileInfo.getUserObj().getUser_name());
            fileInfo.setUserObj(userObj);
            /*�����ĵ�ͼƬ�ϴ�*/
            String filePhotoPath = "upload/noimage.jpg"; 
       	 	if(filePhotoFile != null)
       	 		filePhotoPath = photoUpload(filePhotoFile,filePhotoFileContentType);
       	 	fileInfo.setFilePhoto(filePhotoPath);
            /*�����ĵ��ļ��ϴ�*/
            String docFilePath = ""; 
       	 	if(docFileFile != null)
       	 		docFilePath = fileUpload(docFileFile, docFileFileFileName);
       	 	fileInfo.setDocFile(docFilePath);
            fileInfoDAO.AddFileInfo(fileInfo);
            ctx.put("message",  java.net.URLEncoder.encode("FileInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯFileInfo��Ϣ*/
    public String QueryFileInfo() {
        if(currentPage == 0) currentPage = 1;
        if(fileName == null) fileName = "";
        if(upTime == null) upTime = "";
        List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfoInfo(fileClassObj, fileName, privateFlag, userObj, upTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        fileInfoDAO.CalculateTotalPageAndRecordNumber(fileClassObj, fileName, privateFlag, userObj, upTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = fileInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = fileInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("fileInfoList",  fileInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("fileClassObj", fileClassObj);
        List<FileClass> fileClassList = fileClassDAO.QueryAllFileClassInfo();
        ctx.put("fileClassList", fileClassList);
        ctx.put("fileName", fileName);
        ctx.put("privateFlag", privateFlag);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("upTime", upTime);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryFileInfoOutputToExcel() { 
        if(fileName == null) fileName = "";
        if(upTime == null) upTime = "";
        List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfoInfo(fileClassObj,fileName,privateFlag,userObj,upTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "FileInfo��Ϣ��¼"; 
        String[] headers = { "�ĵ�id","�ĵ�����","�ĵ�����","�ĵ�ͼƬ","�Ƿ񹫿�","�ϴ��û�","�ϴ�ʱ��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<fileInfoList.size();i++) {
        	FileInfo fileInfo = fileInfoList.get(i); 
        	dataset.add(new String[]{fileInfo.getFileId() + "",fileInfo.getFileClassObj().getClassName(),
fileInfo.getFileName(),fileInfo.getFilePhoto(),fileInfo.getPrivateFlag().getYesNoName(),
fileInfo.getUserObj().getName(),
fileInfo.getUpTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"FileInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯFileInfo��Ϣ*/
    public String FrontQueryFileInfo() {
        if(currentPage == 0) currentPage = 1;
        if(fileName == null) fileName = "";
        if(upTime == null) upTime = "";
        List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfoInfo(fileClassObj, fileName, privateFlag, userObj, upTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        fileInfoDAO.CalculateTotalPageAndRecordNumber(fileClassObj, fileName, privateFlag, userObj, upTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = fileInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = fileInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("fileInfoList",  fileInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("fileClassObj", fileClassObj);
        List<FileClass> fileClassList = fileClassDAO.QueryAllFileClassInfo();
        ctx.put("fileClassList", fileClassList);
        ctx.put("fileName", fileName);
        ctx.put("privateFlag", privateFlag);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("upTime", upTime);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�FileInfo��Ϣ*/
    public String ModifyFileInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������fileId��ȡFileInfo����*/
        FileInfo fileInfo = fileInfoDAO.GetFileInfoByFileId(fileId);

        List<FileClass> fileClassList = fileClassDAO.QueryAllFileClassInfo();
        ctx.put("fileClassList", fileClassList);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("fileInfo",  fileInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�FileInfo��Ϣ*/
    public String FrontShowFileInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������fileId��ȡFileInfo����*/
        FileInfo fileInfo = fileInfoDAO.GetFileInfoByFileId(fileId);

        List<FileClass> fileClassList = fileClassDAO.QueryAllFileClassInfo();
        ctx.put("fileClassList", fileClassList);
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("fileInfo",  fileInfo);
        return "front_show_view";
    }

    /*�����޸�FileInfo��Ϣ*/
    public String ModifyFileInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            FileClass fileClassObj = fileClassDAO.GetFileClassByClassId(fileInfo.getFileClassObj().getClassId());
            fileInfo.setFileClassObj(fileClassObj);
            YesOrNo privateFlag = yesOrNoDAO.GetYesOrNoByYesNoId(fileInfo.getPrivateFlag().getYesNoId());
            fileInfo.setPrivateFlag(privateFlag);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(fileInfo.getUserObj().getUser_name());
            fileInfo.setUserObj(userObj);
            /*�����ĵ�ͼƬ�ϴ�*/
            if(filePhotoFile != null) {
            	String filePhotoPath = photoUpload(filePhotoFile,filePhotoFileContentType);
            	fileInfo.setFilePhoto(filePhotoPath);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*�����ĵ��ļ��ϴ�*/
            if(docFileFile != null)
       	 		fileInfo.setDocFile(fileUpload(docFileFile, docFileFileFileName));
            fileInfoDAO.UpdateFileInfo(fileInfo);
            ctx.put("message",  java.net.URLEncoder.encode("FileInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��FileInfo��Ϣ*/
    public String DeleteFileInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            fileInfoDAO.DeleteFileInfo(fileId);
            ctx.put("message",  java.net.URLEncoder.encode("FileInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
