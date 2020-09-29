package com.zjy.study.modules.utils;

import java.io.File;

public class ModifyFileName {

  public static void main(String[] args) {
    final String dir = "G:\\学习课程\\20200612-谷粒商城2019微服务分布式电商项目";
      File folder = new File(dir);
      getFile(folder);
  }

    private static void getFile(File folder) {
        if(folder.isDirectory()){
            File[] files = folder.listFiles();
            for (File file : files) {
              if(file.isDirectory()){
                  getFile(file);
              }else{
                  updateFileName(file);
              }
            }
        }
    }

    private static void updateFileName(File file) {
      String oldname = file.getName();
      String newname = oldname.replaceAll("【更多资源访问：www.jimeng365.cn】","");
      if (!oldname.equals(newname)) {
          String path = file.getParent();
          File newfile = new File(path + "/" + newname);
          if (newfile.exists()) {
              System.out.println(newname + "已经存在！");
          } else {
              file.renameTo(newfile);
          }
      }
  }
}
