<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.FileInfo" %>
<%@ page import="com.chengxusheji.domain.FileClass" %>
<%@ page import="com.chengxusheji.domain.YesOrNo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�FileClass��Ϣ
    List<FileClass> fileClassList = (List<FileClass>)request.getAttribute("fileClassList");
    //��ȡ���е�YesOrNo��Ϣ
    List<YesOrNo> yesOrNoList = (List<YesOrNo>)request.getAttribute("yesOrNoList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    FileInfo fileInfo = (FileInfo)request.getAttribute("fileInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸��ĵ�</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    var fileName = document.getElementById("fileInfo.fileName").value;
    if(fileName=="") {
        alert('�������ĵ�����!');
        return false;
    }
    var fileDesc = document.getElementById("fileInfo.fileDesc").value;
    if(fileDesc=="") {
        alert('�������ĵ�����!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="FileInfo/FileInfo_ModifyFileInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>�ĵ�id:</td>
    <td width=70%><input id="fileInfo.fileId" name="fileInfo.fileId" type="text" value="<%=fileInfo.getFileId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�ĵ�����:</td>
    <td width=70%>
      <select name="fileInfo.fileClassObj.classId">
      <%
        for(FileClass fileClass:fileClassList) {
          String selected = "";
          if(fileClass.getClassId() == fileInfo.getFileClassObj().getClassId())
            selected = "selected";
      %>
          <option value='<%=fileClass.getClassId() %>' <%=selected %>><%=fileClass.getClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ĵ�����:</td>
    <td width=70%><input id="fileInfo.fileName" name="fileInfo.fileName" type="text" size="20" value='<%=fileInfo.getFileName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�ĵ�ͼƬ:</td>
    <td width=70%><img src="<%=basePath %><%=fileInfo.getFilePhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="fileInfo.filePhoto" value="<%=fileInfo.getFilePhoto() %>" />
    <input id="filePhotoFile" name="filePhotoFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>�ĵ�����:</td>
    <td width=70%><textarea id="fileInfo.fileDesc" name="fileInfo.fileDesc" rows=5 cols=50><%=fileInfo.getFileDesc() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>�Ƿ񹫿�:</td>
    <td width=70%>
      <select name="fileInfo.privateFlag.yesNoId">
      <%
        for(YesOrNo yesOrNo:yesOrNoList) {
          String selected = "";
          if(yesOrNo.getYesNoId() == fileInfo.getPrivateFlag().getYesNoId())
            selected = "selected";
      %>
          <option value='<%=yesOrNo.getYesNoId() %>' <%=selected %>><%=yesOrNo.getYesNoName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ĵ��ļ�:</td>
    <td width=70%>
    	<% if(fileInfo.getDocFile()==null || fileInfo.getDocFile().equals("")) { %>
    	�����ļ�
    	<% } else { %>
    	<a href="<%=basePath %><%=fileInfo.getDocFile() %>" target="_blank"><%=fileInfo.getDocFile() %></a><br/>
    	<% } %> 
    <input type=hidden name="fileInfo.docFile" value="<%=fileInfo.getDocFile() %>" />
    <input id="docFileFile" name="docFileFile" type="file" size="50" /></td>
  </tr>
  <tr>
    <td width=30%>�ϴ��û�:</td>
    <td width=70%>
      <select name="fileInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(fileInfo.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�ϴ�ʱ��:</td>
    <td width=70%><input id="fileInfo.upTime" name="fileInfo.upTime" type="text" size="20" value='<%=fileInfo.getUpTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
