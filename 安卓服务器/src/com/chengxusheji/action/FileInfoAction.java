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

	/*图片或文件字段filePhoto参数接收*/
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
	/*图片或文件字段docFile参数接收*/
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
    /*界面层需要查询的属性: 文档分类*/
    private FileClass fileClassObj;
    public void setFileClassObj(FileClass fileClassObj) {
        this.fileClassObj = fileClassObj;
    }
    public FileClass getFileClassObj() {
        return this.fileClassObj;
    }

    /*界面层需要查询的属性: 文档名称*/
    private String fileName;
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public String getFileName() {
        return this.fileName;
    }

    /*界面层需要查询的属性: 是否公开*/
    private YesOrNo privateFlag;
    public void setPrivateFlag(YesOrNo privateFlag) {
        this.privateFlag = privateFlag;
    }
    public YesOrNo getPrivateFlag() {
        return this.privateFlag;
    }

    /*界面层需要查询的属性: 上传用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 上传时间*/
    private String upTime;
    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }
    public String getUpTime() {
        return this.upTime;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource FileClassDAO fileClassDAO;
    @Resource YesOrNoDAO yesOrNoDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource FileInfoDAO fileInfoDAO;

    /*待操作的FileInfo对象*/
    private FileInfo fileInfo;
    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
    public FileInfo getFileInfo() {
        return this.fileInfo;
    }

    /*跳转到添加FileInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的FileClass信息*/
        List<FileClass> fileClassList = fileClassDAO.QueryAllFileClassInfo();
        ctx.put("fileClassList", fileClassList);
        /*查询所有的YesOrNo信息*/
        List<YesOrNo> yesOrNoList = yesOrNoDAO.QueryAllYesOrNoInfo();
        ctx.put("yesOrNoList", yesOrNoList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加FileInfo信息*/
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
            /*处理文档图片上传*/
            String filePhotoPath = "upload/noimage.jpg"; 
       	 	if(filePhotoFile != null)
       	 		filePhotoPath = photoUpload(filePhotoFile,filePhotoFileContentType);
       	 	fileInfo.setFilePhoto(filePhotoPath);
            /*处理文档文件上传*/
            String docFilePath = ""; 
       	 	if(docFileFile != null)
       	 		docFilePath = fileUpload(docFileFile, docFileFileFileName);
       	 	fileInfo.setDocFile(docFilePath);
            fileInfoDAO.AddFileInfo(fileInfo);
            ctx.put("message",  java.net.URLEncoder.encode("FileInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileInfo添加失败!"));
            return "error";
        }
    }

    /*查询FileInfo信息*/
    public String QueryFileInfo() {
        if(currentPage == 0) currentPage = 1;
        if(fileName == null) fileName = "";
        if(upTime == null) upTime = "";
        List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfoInfo(fileClassObj, fileName, privateFlag, userObj, upTime, currentPage);
        /*计算总的页数和总的记录数*/
        fileInfoDAO.CalculateTotalPageAndRecordNumber(fileClassObj, fileName, privateFlag, userObj, upTime);
        /*获取到总的页码数目*/
        totalPage = fileInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryFileInfoOutputToExcel() { 
        if(fileName == null) fileName = "";
        if(upTime == null) upTime = "";
        List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfoInfo(fileClassObj,fileName,privateFlag,userObj,upTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "FileInfo信息记录"; 
        String[] headers = { "文档id","文档分类","文档名称","文档图片","是否公开","上传用户","上传时间"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"FileInfo.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询FileInfo信息*/
    public String FrontQueryFileInfo() {
        if(currentPage == 0) currentPage = 1;
        if(fileName == null) fileName = "";
        if(upTime == null) upTime = "";
        List<FileInfo> fileInfoList = fileInfoDAO.QueryFileInfoInfo(fileClassObj, fileName, privateFlag, userObj, upTime, currentPage);
        /*计算总的页数和总的记录数*/
        fileInfoDAO.CalculateTotalPageAndRecordNumber(fileClassObj, fileName, privateFlag, userObj, upTime);
        /*获取到总的页码数目*/
        totalPage = fileInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的FileInfo信息*/
    public String ModifyFileInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键fileId获取FileInfo对象*/
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

    /*查询要修改的FileInfo信息*/
    public String FrontShowFileInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键fileId获取FileInfo对象*/
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

    /*更新修改FileInfo信息*/
    public String ModifyFileInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            FileClass fileClassObj = fileClassDAO.GetFileClassByClassId(fileInfo.getFileClassObj().getClassId());
            fileInfo.setFileClassObj(fileClassObj);
            YesOrNo privateFlag = yesOrNoDAO.GetYesOrNoByYesNoId(fileInfo.getPrivateFlag().getYesNoId());
            fileInfo.setPrivateFlag(privateFlag);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(fileInfo.getUserObj().getUser_name());
            fileInfo.setUserObj(userObj);
            /*处理文档图片上传*/
            if(filePhotoFile != null) {
            	String filePhotoPath = photoUpload(filePhotoFile,filePhotoFileContentType);
            	fileInfo.setFilePhoto(filePhotoPath);
            }
            String path = ServletActionContext.getServletContext().getRealPath("/upload"); 
            /*处理文档文件上传*/
            if(docFileFile != null)
       	 		fileInfo.setDocFile(fileUpload(docFileFile, docFileFileFileName));
            fileInfoDAO.UpdateFileInfo(fileInfo);
            ctx.put("message",  java.net.URLEncoder.encode("FileInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除FileInfo信息*/
    public String DeleteFileInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            fileInfoDAO.DeleteFileInfo(fileId);
            ctx.put("message",  java.net.URLEncoder.encode("FileInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileInfo删除失败!"));
            return "error";
        }
    }

}
