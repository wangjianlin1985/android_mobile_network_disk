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
import com.chengxusheji.dao.FileClassDAO;
import com.chengxusheji.domain.FileClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class FileClassAction extends BaseAction {

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

    private int classId;
    public void setClassId(int classId) {
        this.classId = classId;
    }
    public int getClassId() {
        return classId;
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

    /*��������FileClass����*/
    private FileClass fileClass;
    public void setFileClass(FileClass fileClass) {
        this.fileClass = fileClass;
    }
    public FileClass getFileClass() {
        return this.fileClass;
    }

    /*��ת�����FileClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���FileClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddFileClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            fileClassDAO.AddFileClass(fileClass);
            ctx.put("message",  java.net.URLEncoder.encode("FileClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯFileClass��Ϣ*/
    public String QueryFileClass() {
        if(currentPage == 0) currentPage = 1;
        List<FileClass> fileClassList = fileClassDAO.QueryFileClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        fileClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = fileClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = fileClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("fileClassList",  fileClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryFileClassOutputToExcel() { 
        List<FileClass> fileClassList = fileClassDAO.QueryFileClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "FileClass��Ϣ��¼"; 
        String[] headers = { "����id","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<fileClassList.size();i++) {
        	FileClass fileClass = fileClassList.get(i); 
        	dataset.add(new String[]{fileClass.getClassId() + "",fileClass.getClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"FileClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯFileClass��Ϣ*/
    public String FrontQueryFileClass() {
        if(currentPage == 0) currentPage = 1;
        List<FileClass> fileClassList = fileClassDAO.QueryFileClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        fileClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = fileClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = fileClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("fileClassList",  fileClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�FileClass��Ϣ*/
    public String ModifyFileClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡFileClass����*/
        FileClass fileClass = fileClassDAO.GetFileClassByClassId(classId);

        ctx.put("fileClass",  fileClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�FileClass��Ϣ*/
    public String FrontShowFileClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������classId��ȡFileClass����*/
        FileClass fileClass = fileClassDAO.GetFileClassByClassId(classId);

        ctx.put("fileClass",  fileClass);
        return "front_show_view";
    }

    /*�����޸�FileClass��Ϣ*/
    public String ModifyFileClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            fileClassDAO.UpdateFileClass(fileClass);
            ctx.put("message",  java.net.URLEncoder.encode("FileClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��FileClass��Ϣ*/
    public String DeleteFileClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            fileClassDAO.DeleteFileClass(classId);
            ctx.put("message",  java.net.URLEncoder.encode("FileClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("FileClassɾ��ʧ��!"));
            return "error";
        }
    }

}
