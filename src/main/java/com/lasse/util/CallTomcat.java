package com.lasse.util;

/**
 * Created by Lance on 2018/4/8.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class CallTomcat {

    @Autowired
    DownloadWar downloadWar;

    static final String startCommand = System.getenv("CATALINA_HOME") + File.separator + "bin\\startup.bat";//启动tomcat命令
    //String command = "E:\\apache-tomcat-7.0.76\\bin\\tomcat7w.exe";//启动Tomcat命令，仅限windows版本，无弹框
    static final String closecommand = System.getenv("CATALINA_HOME") + File.separator + "bin\\shutdown.bat";//关闭tomcat命令

    static final String fileName = System.getenv("CATALINA_HOME") +File.separator + "webapps\\numas";

    public void changeType(String newUrl) {
        //关闭tomcat
        try {
            callCommand(closecommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //下载ci上面的最新的包
        downloadWar.down(newUrl);
        //删除生成的文件
        File file = new File(fileName);
        if(file.exists()) {
            deleteDir(file);
        }
        //打开tomcat
        try {
            callCommand(startCommand);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行命令
     *
     * @throws IOException
     */
    private void callCommand(String command) throws IOException {

        Runtime runtime = Runtime.getRuntime();//返回与当前的Java应用相关的运行时对象
        //指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例
        Process process = runtime.exec(command);
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runtime.gc();//运行垃圾回收器
//        String line = null;
//        StringBuilder content = new StringBuilder();
//        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        while ((line = br.readLine()) != null) {
//            content.append(line).append("\r\n");
//        }
//        System.out.println(content.toString());


    }

    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}