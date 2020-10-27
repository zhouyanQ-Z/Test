package com.swufe.javalib;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MyClass {
    private static  final  String TAG="MainActivity3";
    public static void main(String[] args) {
        //使用jsoup解析HTML
        String url = "http://www.usd-cny.com/bankofchina.htm";
        try {
            Document doc = Jsoup.connect(url).get();
            System.out.println(doc.title());
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");
            for(Element tr : trs){
                Elements tds = tr.getElementsByTag("td");
                if(tds.size()>0){
                    Element td1 = tds.get(0);
                    Element td2 = tds.get(5);
                    System.out.println(td1.text()+"==>"+td2.text());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}