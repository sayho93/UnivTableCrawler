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
    private String trashValue="시간/월화수목금토";
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

    public void isVerified(SFCallback onConnect){       //로그인 시도시 실패나 성공에 따라 boolen isVerified 값 변화

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
        Elements table=document.select("table.bbs-table01");
        System.out.println(table.text());

        ArrayList<ClassInfo> clists = new ArrayList<>();
        String tmpString;
        int middleIndex=-1;
        String className;
        String locationString;

        for(Element trTags: table.select("tr")){

           String rawtime = trTags.child(0).text();
            for(int i=1;i<7;i++){
                if(!trashValue.contains(trTags.child(i).text())){
                    if(trTags.child(i).text()!= " ") {
                        tmpString = trTags.child(i).text();
                        System.out.println("[tmpString:" + tmpString + "]");
                        middleIndex = tmpString.indexOf("(");
                        ClassInfo tmpClass = new ClassInfo();
                        switch (i) {
                            case 1:
                                tmpClass.weekDay = 0;
                                break;
                            case 2:
                                tmpClass.weekDay = 1;
                                break;
                            case 3:
                                tmpClass.weekDay = 2;
                                break;
                            case 4:
                                tmpClass.weekDay = 3;
                                break;
                            case 5:
                                tmpClass.weekDay = 4;
                                break;
                            case 6:
                                break;
                        }
                        tmpClass.title = tmpString.substring(0, middleIndex - 1);
                        tmpClass.location = tmpString.substring(middleIndex, tmpString.length());

                        System.out.println("title: " + tmpClass.title + "location: " + tmpClass.location + "weekday: " + tmpClass.weekDay);
                    }
                }
            }
        }
    }
}