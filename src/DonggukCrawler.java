import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by 전세호 on 2016-11-14.
 */
public class DonggukCrawler extends Crawler {
    /**
     * DONGGUK UNIVERSITY
     */
    //ID = "2014112102 ";
    //PW = "fishcreek1!";
    public Document document;

    public DonggukCrawler(final String userId, final String userPw, SFCallback onStart, SFCallback onConnect, SFCallback onFinish) {
        classList.clear();
        URL_AUTH = "https://eclass.dongguk.edu/User.do?cmd=loginUser"; // Login Link
        URL_TIME = "https://eclass.dongguk.edu/Schedule.do?cmd=viewLessonSchedule"; // Timetable Link
        URL_HOME = "https://eclass.dongguk.edu/Main.do?cmd=viewEclassMain"; // Link for retrieving user Name
        URL_HAND = "https://eclass.dongguk.edu/Report.do?cmd=viewMainReportListLearner"; // Link for retrieving handin list
        FORM_ID = "userDTO.userId";
        FORM_PW = "userDTO.password";
        ID = userId;
        PW = userPw;
        this.onStart = onStart;
        this.onConnect = onConnect;
        this.onFinish = onFinish;
        this.onFail = onFail;
    }

    public void doInBackground() throws IOException{
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
        Elements name=document.select("span.user");
        userName=name.text();
        System.out.println(userName);

        document=Jsoup.connect(URL_HAND)
                .followRedirects(true)
                .cookies(response.cookies())
                .followRedirects(true)
                .method(Connection.Method.POST)
                .timeout(TIMEOUT)
                .post();
        Elements hwTable=document.select("table.list-table");
        for(Element trTags: hwTable.select("tr")) {
            handList.add(new HandInfo(trTags.child(0).text(), trTags.child(1).text(), trTags.child(2).text(),
                    trTags.child(3).text(), trTags.child(4).text(), trTags.child(5).text(), trTags.child(6).text()));
        }
        for(HandInfo node:handList){
            System.out.println(node.no+" "+node.subject+" "+node.name+" "+node.date+" "+node.handin+" "+
                    node.open+" "+node.score);
        }

        document=Jsoup.connect(URL_TIME)
                .followRedirects(true)
                .cookies(response.cookies())
                .followRedirects(true)
                .method(Connection.Method.POST)
                .timeout(TIMEOUT)
                .post();

    }
}