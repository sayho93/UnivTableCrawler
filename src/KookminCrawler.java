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
        /*
        this.onStart = onStart;
        this.onConnect = onConnect;
        this.onFinish = onFinish;
        this.onFail = onFail;
        */
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
        int lineIndicator=0;

        for(Element trTags: table.select("tr")){
            String rawtimeShort="";
            String rawtimeLong="";
            String startRawtimeShort="";
            String startRawtimeLong="";
            String endRawtimeShort="";
            String endRawtimeLong="";
            int swungDashIndicatorShort=-1;
            int swungDashIndicatorLong=-1;
            int ScolonIndicatorShort=-1;
            int ScolonIndicatorLong=-1;
            int EcolonIndicatorShort=-1;
            int EcolonIndicatorLoong=-1;

            if(trTags.child(0).text().contains("학년도")){    //같은 클래스명 가진 테이블 건너뛰기
                continue;
            }
            /*
            if(trTags.text().equals(" ")){
                continue;
            }
            */
            if(trTags.attr("class").equals("table_header_center")){     //요일 행 건너뛰기
                continue;
            }

            if(lineIndicator==0){               //first line
                lineIndicator++;
                continue;
                /*
                String rawtime=trTags.child(1).text();
                swungDashIndicator=rawtime.indexOf("~");
                startRawtime=rawtime.substring(swungDashIndicator-5, swungDashIndicator);
                endRawtime=rawtime.substring(swungDashIndicator+1, rawtime.length());
                System.out.println("startRawtime: "+startRawtime);
                System.out.println("endRawtime: "+endRawtime);
                */
            }

            else if(lineIndicator%6==1){        //type 1
                rawtimeShort=trTags.child(1).text();
                rawtimeLong=trTags.child(2).text();
                swungDashIndicatorShort=rawtimeShort.indexOf("~");
                swungDashIndicatorLong=rawtimeLong.indexOf("~");
                startRawtimeShort=rawtimeShort.substring(swungDashIndicatorShort-5, swungDashIndicatorShort);
                startRawtimeLong=rawtimeLong.substring(swungDashIndicatorLong-5, swungDashIndicatorLong);
                endRawtimeShort=rawtimeShort.substring(swungDashIndicatorShort+1, rawtimeShort.length());
                endRawtimeLong=rawtimeLong.substring(swungDashIndicatorLong+1, rawtimeLong.length());
                System.out.println("startRTS: "+startRawtimeShort);
                System.out.println("endRTS: "+endRawtimeShort);
                System.out.println("startRTL: "+startRawtimeLong);
                System.out.println("endRTL: "+endRawtimeLong);


            }

            else if(lineIndicator%2==1){        //left type

            }

            else if(lineIndicator%6==4){        //right type

            }

            else{                               //none type

            }
            lineIndicator++;
        }
        System.out.println("end of kookminCrawler");
    }
}