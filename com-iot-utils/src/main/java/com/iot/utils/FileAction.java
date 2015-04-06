package com.iot.utils;

import java.io.*;

/**
 *文件操作工具类
 * @author RayLew
 */
public class FileAction {
    /**
     * 文件重命名
     * @param oldName 原名
     * @param newName 新名
     * @return
     */
    public static  boolean reName(String oldName, String newName) {
        boolean result = false;
        try {
            File myFile = new File(oldName);
            File NewFile = new File(newName);
            myFile.renameTo(NewFile);
            result = true;
        } catch (Exception e) {
            System.out.println("删除文件操作出错");
        }
        return result;
    }


    /**
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static String copyFile(String oldPath, String newPath) {
        String result = "成功！";
        try {
            int byteread;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);  //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            result = "复制单个文件操作出错";
            System.out.println("复制单个文件操作出错");
        }
        return result;

    }

    /**
     * 删除文件
     * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
     *
     * @return boolean
     */
    public static String delFile(String filePathAndName) {
        String result;
        try {
            File myDelFile = new File(filePathAndName);
            myDelFile.delete();
            result = "成功！";
        } catch (Exception e) {
            System.out.println("删除文件操作出错");
            result = "失败！";
        }
        return result;
    }

    /**
     * 删除文件夹
     * @param folderPath String 文件夹路径及名称 如c:/fqf
     * @return
     */
    public static String delFolder(String folderPath) {
        String result;
        try {
            delAllFile(folderPath);  //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();  //删除空文件夹
            result = "成功！";
        } catch (Exception e) {
            System.out.println("删除文件夹操作出错");
            result = "失败！";
        }
        return result;
    }
    /* 显示文件列表 */

    /**
     *
     * @param path
     * @param Base
     * @return
     */
    public static String[][] showFileList(String path, String Base) {
        if (path == null) {//无参数时为项目文件夹下的所有文件
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        if (!file.isDirectory()) {
            return null;
        }
        String[] tempList = file.list();
        int length = tempList.length;
        String list[][] = new String[4][length];
        for (int i = 0; i < length; i++) {
            list[0][i] = path + "/" + tempList[i];//文件地址
            list[1][i] = tempList[i];//文件名
            list[2][i] = Base + "/" + tempList[i];//文件网络地址
            file = new File(list[0][i]);
            list[3][i] = (file.length() / 1024) + "";//文件大小
        }
        return list;
    }

    /** */
    /**
     * 删除文件夹里面的所有文件
     * @param path String 文件夹路径 如 c:/fqf
     * @return
     */
    public static String delAllFile(String path) {
        File file = new File(path);
        String result = "失败";
        if (!file.exists()) {
            return result;
        }
        if (!file.isDirectory()) {
            return result;
        }
        String[] tempList = file.list();
        File temp;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                result = "成功！";
            }
        }
        return result;
    }

    /**
     *
     * @param folderPath
     */
    public static void newFolder(String folderPath) {
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        try {
            if (myFilePath.isDirectory()) {
                System.out.println("the directory is exists!");
            } else {
                myFilePath.mkdir();
                //System.out.println("新建目录成功");
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
        }
    }

    /**
     *将文件转化为字符串
     * @param filePath
     */
    public static String convertFileToString(String filePath,String charset) {
        File myFilePath = new File(filePath);
        Reader htmlReader=null;
        try {
            if (myFilePath.isDirectory()) {
                System.err.println("error:method:FileAction-convertFileToString,the directory is exists!");
            } else {
                htmlReader = new InputStreamReader(new FileInputStream(filePath), charset);
                StringBuilder sb=new StringBuilder();
                char[] chars=new char[1024];
                int len=0;
                while((len=htmlReader.read(chars))!=-1){
                    sb.append(chars,0,len);
                }
                htmlReader.close();
                return sb.toString();
            }
        } catch (Exception e) {
            System.err.println("error:method:FileAction-convertFileToString,filePath:"+filePath+",charset:"+charset);
            e.printStackTrace();
        }
        return null;
    }
}
