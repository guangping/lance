/*     */ package com.ztesoft.common.util.file;
/*     */ 
/*     */ import com.ztesoft.common.util.StringUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class FileUtil
/*     */ {
/*     */   public static boolean moveA_File(String sourceFileName, String targetFileName)
/*     */   {
/*  15 */     return moveA_File(sourceFileName, targetFileName, false);
/*     */   }
/*     */ 
/*     */   public static boolean moveA_File(String sourceFileName, String targetFileName, boolean isOverlay)
/*     */   {
/*  22 */     File sourceFile = new File(sourceFileName);
/*  23 */     if (!sourceFile.exists()) {
/*  24 */       System.out.println("移动文件失败：原文件" + sourceFileName + "不存在！");
/*  25 */       return false;
/*  26 */     }if (!sourceFile.isFile()) {
/*  27 */       System.out.println("移动文件失败：" + sourceFileName + "不是一个文件！");
/*  28 */       return false;
/*     */     }
/*  30 */     File targetFile = new File(targetFileName);
/*  31 */     if (targetFile.exists()) {
/*  32 */       if (isOverlay)
/*     */       {
/*  34 */         System.out.println("该文件已存在，准备删除它！");
/*  35 */         if (!targetFile.delete()) {
/*  36 */           System.out.println("文件移动失败，删除文件" + targetFileName + "失败！");
/*  37 */           return false;
/*     */         }
/*     */       } else {
/*  40 */         System.out.println("文件移动失败，文件" + targetFileName + "已存在！");
/*  41 */         return false;
/*     */       }
/*     */     }
/*  44 */     else if (!targetFile.getParentFile().exists())
/*     */     {
/*  46 */       System.out.println("该文件所在目录不存在，正在创建！");
/*  47 */       if (!targetFile.getParentFile().mkdirs()) {
/*  48 */         System.out.println("移动文件失败，创建文件所在的目录失败！");
/*  49 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  54 */     if (sourceFile.renameTo(targetFile)) {
/*  55 */       System.out.println("移动文件" + sourceFileName + "到" + targetFileName + "成功！");
/*     */ 
/*  57 */       return true;
/*     */     }
/*  59 */     System.out.println("移动文件" + sourceFileName + "到" + targetFileName + "失败！");
/*     */ 
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   public static boolean moveDir(String sourceDirName, String targetDirName)
/*     */   {
/*  67 */     return moveDir(sourceDirName, targetDirName, false);
/*     */   }
/*     */ 
/*     */   public static boolean moveDir(String sourceDirName, String targetDirName, boolean isOverlay)
/*     */   {
/*  74 */     File sourceDir = new File(sourceDirName);
/*  75 */     if (!sourceDir.exists()) {
/*  76 */       System.out.println("移动目录失败，原始目录" + sourceDirName + "不存在！");
/*  77 */       return false;
/*  78 */     }if (!sourceDir.isDirectory()) {
/*  79 */       System.out.println("移动目录失败，" + sourceDirName + "不是一个目录！");
/*  80 */       return false;
/*     */     }
/*     */ 
/*  83 */     if (!targetDirName.endsWith(File.separator)) {
/*  84 */       targetDirName = targetDirName + File.separator;
/*     */     }
/*  86 */     File targetDir = new File(targetDirName);
/*     */ 
/*  88 */     if (targetDir.exists()) {
/*  89 */       if (isOverlay)
/*     */       {
/*  91 */         System.out.println("该目录已存在，准备删除它！");
/*  92 */         if (!delFolder(targetDirName))
/*  93 */           System.out.println("移动目录失败：删除目录" + targetDirName + "失败！");
/*     */       }
/*     */       else {
/*  96 */         System.out.println("移动目录失败：该目录" + targetDirName + "已存在！");
/*  97 */         return false;
/*     */       }
/*     */     }
/*     */     else {
/* 101 */       System.out.println("该目录不存在，正在创建！");
/* 102 */       if (!targetDir.mkdirs()) {
/* 103 */         System.out.println("移动目录失败：创建该目录失败！");
/* 104 */         return false;
/*     */       }
/*     */     }
/* 107 */     boolean flag = true;
/*     */ 
/* 109 */     File[] files = sourceDir.listFiles();
/* 110 */     for (int i = 0; i < files.length; i++)
/*     */     {
/* 112 */       if (files[i].isFile()) {
/* 113 */         flag = moveA_File(files[i].getAbsolutePath(), targetDirName + files[i].getName(), isOverlay);
/*     */ 
/* 115 */         if (!flag) {
/* 116 */           break;
/*     */         }
/*     */ 
/*     */       }
/* 120 */       else if (files[i].isDirectory()) {
/* 121 */         flag = moveDir(files[i].getAbsolutePath(), targetDirName + files[i].getName(), isOverlay);
/*     */ 
/* 123 */         if (!flag) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 128 */     if (!flag) {
/* 129 */       System.out.println("目录" + sourceDirName + "移动到" + targetDirName + "失败！");
/*     */ 
/* 131 */       return false;
/*     */     }
/*     */ 
/* 134 */     if (delFolder(sourceDirName)) {
/* 135 */       System.out.println("目录" + sourceDirName + "移动到" + targetDirName + "成功！");
/*     */ 
/* 137 */       return true;
/*     */     }
/* 139 */     System.out.println("目录" + sourceDirName + "移动到" + targetDirName + "失败！");
/*     */ 
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 147 */     System.out.println("调用moveA_File方法的结果如下：");
/* 148 */     String sourceFileName = "D:\\temp\\keytoolcmd.txt";
/* 149 */     String targetFileName = "D:\\test\\temp\\keytoolcmd.txt.";
/* 150 */     moveA_File(sourceFileName, targetFileName, true);
/*     */ 
/* 152 */     System.out.println("\n调用moveDir方法的结果如下：");
/* 153 */     String sourceDirName = "D:\\temp\\aa";
/* 154 */     String targetDirName = "F:\\abc";
/* 155 */     moveDir(sourceDirName, targetDirName);
/*     */ 
/* 157 */     File f = new File("d:\\myHomework\\Work");
/* 158 */     File[] fileList = f.listFiles();
/* 159 */     for (int i = 0; i < fileList.length; i++)
/* 160 */       fileList[i].renameTo(new File("d:\\myHomework\\Backup\\" + fileList[i].getName()));
/*     */   }
/*     */ 
/*     */   public static void delAllFile(String path)
/*     */   {
/* 172 */     File file = new File(path);
/* 173 */     if (!file.exists()) {
/* 174 */       return;
/*     */     }
/* 176 */     if (!file.isDirectory()) {
/* 177 */       return;
/*     */     }
/* 179 */     String[] tempList = file.list();
/* 180 */     File temp = null;
/* 181 */     for (int i = 0; i < tempList.length; i++) {
/* 182 */       if (path.endsWith(File.separator))
/* 183 */         temp = new File(path + tempList[i]);
/*     */       else {
/* 185 */         temp = new File(path + File.separator + tempList[i]);
/*     */       }
/* 187 */       if (temp.isFile()) {
/* 188 */         temp.delete();
/*     */       }
/* 190 */       if (temp.isDirectory()) {
/* 191 */         delAllFile(path + "/" + tempList[i]);
/* 192 */         delFolder(path + "/" + tempList[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void delFile(String filePathAndName)
/*     */   {
/*     */     try
/*     */     {
/* 208 */       String filePath = filePathAndName;
/* 209 */       filePath = filePath.toString();
/* 210 */       File myDelFile = new File(filePath);
/* 211 */       myDelFile.delete();
/*     */     } catch (Exception e) {
/* 213 */       System.out.println("删除文件操作出错");
/* 214 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean delFolder(String folderPath)
/*     */   {
/* 228 */     boolean isDel = false;
/*     */     try {
/* 230 */       delAllFile(folderPath);
/*     */ 
/* 232 */       String filePath = folderPath;
/* 233 */       filePath = filePath.toString();
/* 234 */       File myFilePath = new File(filePath);
/* 235 */       isDel = myFilePath.delete();
/*     */     } catch (Exception e) {
/* 237 */       System.out.println("删除文件夹操作出错");
/* 238 */       e.printStackTrace();
/*     */     }
/* 240 */     return isDel;
/*     */   }
/*     */ 
/*     */   public static void copyFile(String oldPath, String newPath)
/*     */   {
/*     */     try
/*     */     {
/* 254 */       int bytesum = 0;
/* 255 */       int byteread = 0;
/* 256 */       File oldfile = new File(oldPath);
/* 257 */       if (oldfile.exists()) {
/* 258 */         InputStream inStream = new FileInputStream(oldPath);
/* 259 */         FileOutputStream fs = new FileOutputStream(newPath);
/* 260 */         byte[] buffer = new byte[1444];
/*     */ 
/* 262 */         while ((byteread = inStream.read(buffer)) != -1) {
/* 263 */           bytesum += byteread;
/*     */ 
/* 265 */           fs.write(buffer, 0, byteread);
/*     */         }
/* 267 */         inStream.close();
/*     */       }
/*     */     } catch (Exception e) {
/* 270 */       System.out.println("复制单个文件操作出错");
/* 271 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void copyFolder(String oldPath, String newPath)
/*     */   {
/*     */     try
/*     */     {
/* 286 */       File newFile = new File(newPath);
/* 287 */       if (!newFile.exists()) {
/* 288 */         new File(newPath).mkdirs();
/*     */       }
/* 290 */       File a = new File(oldPath);
/* 291 */       String[] file = a.list();
/* 292 */       File temp = null;
/* 293 */       for (int i = 0; i < file.length; i++) {
/* 294 */         if (oldPath.endsWith(File.separator))
/* 295 */           temp = new File(oldPath + file[i]);
/*     */         else {
/* 297 */           temp = new File(oldPath + File.separator + file[i]);
/*     */         }
/* 299 */         if (temp.isFile()) {
/* 300 */           FileInputStream input = new FileInputStream(temp);
/* 301 */           FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
/*     */ 
/* 303 */           byte[] b = new byte[5120];
/*     */           int len;
/* 305 */           while ((len = input.read(b)) != -1) {
/* 306 */             output.write(b, 0, len);
/*     */           }
/* 308 */           output.flush();
/* 309 */           output.close();
/* 310 */           input.close();
/*     */         }
/* 312 */         if (temp.isDirectory())
/* 313 */           copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 317 */       System.out.println("复制整个文件夹内容操作出错");
/* 318 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void moveFile(String oldPath, String newPath)
/*     */   {
/* 331 */     copyFile(oldPath, newPath);
/* 332 */     delFile(oldPath);
/*     */   }
/*     */ 
/*     */   public static void moveFolder(String oldPath, String newPath)
/*     */   {
/* 344 */     copyFolder(oldPath, newPath);
/* 345 */     delFolder(oldPath);
/*     */   }
/*     */ 
/*     */   public static boolean isWindowsOS()
/*     */   {
/* 352 */     boolean isWindowsOS = false;
/* 353 */     String osName = System.getProperty("os.name");
/* 354 */     if (osName.toLowerCase().indexOf("windows") > -1) {
/* 355 */       isWindowsOS = true;
/*     */     }
/* 357 */     return isWindowsOS;
/*     */   }
/*     */ 
/*     */   public static String readFileContent(String filePath) throws IOException
/*     */   {
/* 362 */     InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(filePath);
/*     */ 
/* 364 */     if (in == null) {
/* 365 */       return "";
/*     */     }
/* 367 */     String result = StringUtils.convertStreamToString(in);
/*     */ 
/* 369 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\guangping\Desktop\inf_server-0.0.1-20140414.050308-5.jar
 * Qualified Name:     com.ztesoft.common.util.file.FileUtil
 * JD-Core Version:    0.6.2
 */