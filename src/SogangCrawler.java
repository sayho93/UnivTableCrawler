import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 전세호 on 2016-11-14.
 */
public class SogangCrawler extends Crawler {
    /**
     * SOGANG UNIVERSITY
     */
    //ID = "20131014";
    //PW = "wjsgytjd5+";
    public SogangCrawler(final String userId, final String userPw, SFCallback onStart, SFCallback onConnect, SFCallback onFinish) {
        classList.clear();
        URL_AUTH = "https://eclass.sogang.ac.kr/ilos/lo/login.acl";
        URL_TIME = "http://eclass.sogang.ac.kr/ilos/st/main/pop_academic_timetable_form.acl";
        URL_HOME = "http://eclass.sogang.ac.kr/ilos/main/main_form.acl";
        URL_HAND = "http://eclass.sogang.ac.kr/ilos/mp/mypage_main_form.acl";
        FORM_ID = "usr_id";
        FORM_PW = "usr_pwd";
        ID = userId;
        PW = userPw;
        this.onStart = onStart;
        this.onConnect = onConnect;
        this.onFinish = onFinish;
        this.onFail = onFail;
    }
    public void doInBackground() throws IOException {
        System.out.println("start of sogangCrawler");
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
        Elements name=document.select("strong.site-font-color");
        userName=name.text();
        System.out.println("name: "+userName);


        
        System.out.println("end of sogangCrawler");
    }
}