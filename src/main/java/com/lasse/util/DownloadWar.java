package com.lasse.util;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Created by Lance on 2018/4/8.
 */
@Component
public class DownloadWar {

    private static final String savePath = System.getenv("CATALINA_HOME") + File.separator + "webapps";

    private static final String fileName = "numas.war";

    private static final String j_username = "dev";

    private static final String j_passward = "dev";

    private static final String loginUrl = "http://ci.lianfan.net/j_acegi_security_check?j_username=dev&j_password=dev&" +
            "from=%2F&Jenkins-Crumb=9d79e6715d51f65f5ae463f62da06291&json=%7B%22j_username%22%3A+%22dev%22%2C+%22j_password" +
            "%22%3A+%22dev%22%2C+%22remember_me%22%3A+false%2C+%22from%22%3A+%22%2F%22%2C+%22" +
            "Jenkins-Crumb%22%3A+%229d79e6715d51f65f5ae463f62da06291%22%7D&Submit=%E7%99%BB%E5%BD%95";

    private static final String loginUrl2 = "http://ci.lianfan.net/login?from=%2F";

    private static final String dataUrl = "http://ci.lianfan.net/job/numas/job/numas-branch-end/lastSuccessfulBuild/artifact/numas.war";

    public void down(String url) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(loginUrl);
        try {
            // 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
            httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
            int statusCode=httpClient.executeMethod(postMethod);

            // 获得登陆后的 Cookie
            Cookie[] cookies = httpClient.getState().getCookies();
            StringBuffer tmpcookies = new StringBuffer();
            for (Cookie c : cookies) {
                tmpcookies.append(c.toString() + ";");
                System.out.println("cookies = "+c.toString());
            }
            if(statusCode==302){//重定向到新的URL
                System.out.println("模拟登录成功");
                // 进行登陆后的操作
                GetMethod getMethod = new GetMethod(dataUrl);
                // 每次访问需授权的网址时需带上前面的 cookie 作为通行证
                getMethod.setRequestHeader("cookie", tmpcookies.toString());
                // 你还可以通过 PostMethod/GetMethod 设置更多的请求后数据
                // 例如，referer 从哪里来的，UA 像搜索引擎都会表名自己是谁，无良搜索引擎除外
                postMethod.setRequestHeader("Referer", "http://passport.mop.com/");
                postMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
                httpClient.executeMethod(getMethod);
                // 打印出返回数据，检验一下是否成功
                InputStream inputStream = getMethod.getResponseBodyAsStream();
                byte[] data = readInputStream(inputStream);

                File saveDir = new File(savePath);
                if (!saveDir.exists()) {
                    saveDir.mkdir();
                }
                File file = new File(saveDir + File.separator + fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                if (fos != null) {
                    fos.close();
                }
            }
            else {
                System.out.println("登录失败");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


}
