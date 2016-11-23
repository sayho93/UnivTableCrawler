import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 전세호 on 2016-11-14.
 */
public class KookminCrawler extends Crawler {
    /**
     * KOOKMIN UNIVERSITY
     */
    //ID = "20145160";
    //PW = "qkrtkddl2#";
    public KookminCrawler(final String userId, final String userPw, SFCallback onStart, SFCallback onConnect, SFCallback onFinish) {
        classList.clear();
        URL_AUTH = "http://ktis.kookmin.ac.kr/kmu/com.Login.do?";
        URL_TIME = "http://ktis.kookmin.ac.kr/kmu/ucb.Ucb0164rAGet01.do";
        URL_HOME = "http://ktis.kookmin.ac.kr/kmu/usa.Usa0209eFGet01.do";
        URL_HAND = "#";
        FORM_ID = "txt_user_id";
        FORM_PW = "txt_passwd";
        ID = userId;
        PW = userPw;
        this.onStart = onStart;
        this.onConnect = onConnect;
        this.onFinish = onFinish;
        this.onFail = onFail;
    }
    public void doInBackground() throws IOException {
        System.out.println("Start of kookminCrawler");
        /*
        Connection.Response response= Jsoup.connect(URL_AUTH)
                .followRedirects(true)
                .data(FORM_ID, ID)
                .data(FORM_PW, PW)
                .method(Connection.Method.POST)
                .timeout(TIMEOUT)
                .execute();

        document=Jsoup.connect(URL_HOME)
                .followRedirects(true)
                .cookies(response.cookies())
                .followRedirects(true)
                .method(Connection.Method.POST)
                .timeout(TIMEOUT)
                .post();

        document=Jsoup.connect(URL_TIME)
                .followRedirects(true)
                .cookies(response.cookies())
                .followRedirects(true)
                .method(Connection.Method.POST)
                .timeout(TIMEOUT)
                .post();
        */


        document=Jsoup.connect("http://yjham2002.woobi.co.kr/test.html")
                .followRedirects(true)
                .method(Connection.Method.POST)
                .timeout(TIMEOUT)
                .post();
        Elements table=document.select("table.table_bg");
        //System.out.println(table.text());

        ClassInfo ClistA[][]=new ClassInfo[15][6];
        ClassInfo ClistB[][]=new ClassInfo[11][6];

        for(Element trTags: table.select("tr")){
            for(int i=2;i<14;i++){
                if(trTags.child(0).text().contains("학년도") || trTags.child(0).text().equals("")){
                    continue;
                }
                String rawtime=trTags.child(0).text();
                System.out.println("rawtime: "+rawtime);
                if(i%2==0){
                    ClassInfo tmpClass=new ClassInfo();
                    if(!trashValue.contains(trTags.child(i).text()) && trTags.child(i).text().equals("")){

                    }
                }
                else if(i%2==1){
                    ClassInfo tmpClass=new ClassInfo();
                    if(!trashValue.contains(trTags.child(i).text()) && trTags.child(i).text().equals("")){

                    }
                    Element e;
                    Integer.parseInt(e.attr("rowspan"))
                }
            }
            System.out.println();
        }

        System.out.println("end of kookminCrawler");
    }
}