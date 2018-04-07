package com.lasse.util;

/**
 * Created by Lance on 2018/4/8.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CallTomcat {

    @Autowired
    DownloadWar downloadWar;

    static final String startCommand = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\bin\\startup.bat";//启动tomcat命令
    //String command = "E:\\apache-tomcat-7.0.76\\bin\\tomcat7w.exe";//启动Tomcat命令，仅限windows版本，无弹框
    static final String closecommand = "C:\\Program Files\\Apache Software Foundation\\Tomcat 8.0\\bin\\shutdown.bat";//关闭tomcat命令

    public void changeType(Boolean type, String url) {
        downloadWar.down(url);
        if(type) {
            try {
                callCommand(closecommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                callCommand(startCommand);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行命令
     *
     * @throws IOException
     */
    private void callCommand(String command) throws IOException{

        Runtime runtime = Runtime.getRuntime();//返回与当前的Java应用相关的运行时对象
        //指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例
        Process process = runtime.exec(command);
        runtime.gc();//运行垃圾回收器
//        String line = null;
//        StringBuilder content = new StringBuilder();
//        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        while ((line = br.readLine()) != null) {
//            content.append(line).append("\r\n");
//        }
//        System.out.println(content.toString());


    }

}