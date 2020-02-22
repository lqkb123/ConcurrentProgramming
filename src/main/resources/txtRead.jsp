<%@page import="java.io.BufferedReader" %>
<%@page import="java.io.File" %>
<%@page import="java.io.FileReader" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    //String path = request.getContextPath();
    //System.out.println("path=="+path);
    //String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>实例9：使用jsp读取TXT格式文件</title>
</head>
<body>
<%
    File file = new File("D://jsp.txt");
    FileReader fr = new FileReader(file);  //字符输入流
    BufferedReader br = new BufferedReader(fr);  //使文件可按行读取并具有缓冲功能
    StringBuffer strB = new StringBuffer();   //strB用来存储jsp.txt文件里的内容
    String str = br.readLine();
    while (str != null) {
        strB.append(str).append("<br>");   //将读取的内容放入strB
        str = br.readLine();
    }
    Thread.currentThread().wait();
    br.close();    //关闭输入流
    %>
    <center>
    <%=strB %>
</center>
</body>
</html>