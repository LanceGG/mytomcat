package com.lasse.util;

import com.lasse.entity.BiShun;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import scala.util.parsing.json.JSON;
import scala.util.parsing.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BiShunGet {

    private static String fileUrl = "http://www.zdic.net/z/jd/?u=";
    private static List<BiShun> biShuns = new ArrayList<>();
    private static Pattern pattern = Pattern.compile("[^0-9]");

    public static void main(String[] args) {
        try {
            FileReader fileReader = new FileReader(new File("E:\\work2\\hanzi2.txt"));
            HttpClient httpClient = new HttpClient();
            Integer fireChar = fileReader.read();
            while (fireChar != -1) {
                String realurl = fileUrl + intToHex(fireChar);
                GetMethod getMethod = new GetMethod(realurl);
                httpClient.executeMethod(getMethod);
                String response = getMethod.getResponseBodyAsString();

                Document document = Jsoup.parse(response);
                List<Element> elements = document.getElementsByClass("diczx6");
                String str = ((Element) elements.get(0).parentNode()).getElementsByTag("p").get(0).childNode(1).toString();

                str = filter(str);
                System.out.println(System.currentTimeMillis() + " " + str);
                BiShun biShun = new BiShun(fireChar, str);
                biShuns.add(biShun);

                fireChar = fileReader.read();
            }
            fileReader.close();

            File file = new File("E:\\work2\\bishun2.csv");
            if(!file.exists()) {
                file.createNewFile();
            }

            OutputStream outputStream = new FileOutputStream(file, true);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            Map<Integer, String> map = new HashMap<>();
            biShuns.forEach(biShun1 -> {
                map.put(biShun1.getUniCode(), biShun1.getBishun());
            });
            objectOutputStream.writeObject(map);

            outputStream.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String intToHex(Integer n) {
        StringBuffer s = new StringBuffer();
        String a;
        char []b = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        while(n != 0){
            s = s.append(b[n%16]);
            n = n/16;
        }
        a = s.reverse().toString();
        return a;
    }

    private static String filter(String str) {
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("");
    }

}
