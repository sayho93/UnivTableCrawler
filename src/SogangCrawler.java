import org.jsoup.Connection;
        import org.jsoup.Jsoup;
        import org.jsoup.nodes.Element;
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
        URL_AUTH_MOBILE="http://eclass.sogang.ac.kr/ilos/m/main/login_form.acl";
        URL_TIME = "http://eclass.sogang.ac.kr/ilos/st/main/pop_academic_timetable_form.acl";
        URL_HOME = "http://eclass.sogang.ac.kr/ilos/main/main_form.acl";
        URL_HAND = "http://eclass.sogang.ac.kr/ilos/m/st/report_list_form.acl";
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

        document=Jsoup.connect(URL_TIME)
                .followRedirects(true)
                .cookies(response.cookies())
                .followRedirects(true)
                .method(Connection.Method.POST)
                .timeout(TIMEOUT)
                .post();
        Elements table=document.select("table.questionlist");
        // System.out.println(table.text());

        String Clist[][]=new String[56][8];
        int lineIndicator=-1;

        for(Element trTags: table.select("tr")){        //시간표 Clist에 긁어오는 반복문
            //System.out.println(trTags.text());
            if(lineIndicator%4 == 0) {
                for (int i = 1; i < 8; i++) {
                    if (!trashValue.contains(trTags.child(i).text())) {
                       // System.out.println("[" + trTags.child(i).text() + "]");
                        Clist[lineIndicator][i-1] = trTags.child(i).text();
                    }
                }
            }
            else if(lineIndicator != -1){
                for(int i=0;i<7;i++){
                    if (!trashValue.contains(trTags.child(i).text())) {
                       // System.out.println("[" + trTags.child(i).text() + "]");
                        Clist[lineIndicator][i] = trTags.child(i).text();
                    }
                }
            }

            lineIndicator++;
        }

        //Clist 로그
        /*
        for(int i=0;i<56;i++){
            for(int j=0;j<7;j++){
                System.out.printf("[%s]", Clist[i][j]);
            }
            System.out.println();
        }
        */

        for(int i=0;i<7;i++){
            for(int j=0;j<56;j++){
                if(!Clist[j][i].equals(" ")){
                    //System.out.println("title:"+Clist[j][i]);
                    ClassInfo tmpclass=new ClassInfo();
                    int TLindicator;
                    String tmpStime;
                    int timeIndicator;
                    int sIndicator;
                    String tmpEtime;
                    int eIndicator;
                    String tmpRawtime;
                    String tmpRawTL;

                    tmpclass.title=Clist[j][i];
                    tmpRawTL=Clist[j+2][i];
                    //System.out.println("tmpRawTL: "+tmpRawTL);
                    TLindicator=tmpRawTL.indexOf("(");
                    //System.out.println("TLindicator: "+TLindicator);
                    tmpRawtime=tmpRawTL.substring(0, TLindicator);
                    //System.out.println("tmpRawtime: "+tmpRawtime);
                    tmpclass.location=tmpRawTL.substring(TLindicator, tmpRawTL.length());
                    //System.out.println("tmplocation: "+tmpclass.location);
                    tmpclass.weekDay=i+1;
                    timeIndicator=tmpRawtime.indexOf("~");
                    //System.out.println("timeIndicator: "+timeIndicator);
                    tmpStime=tmpRawtime.substring(0, timeIndicator);
                    tmpEtime=tmpRawtime.substring(timeIndicator+1, tmpRawtime.length());
                    sIndicator=tmpStime.indexOf(":");
                    eIndicator=tmpEtime.indexOf(":");
                    tmpclass.startHour=Integer.parseInt(tmpStime.substring(0, sIndicator));
                    tmpclass.startMin=Integer.parseInt(tmpStime.substring(sIndicator+1, tmpStime.length()));
                    tmpclass.endHour=Integer.parseInt(tmpEtime.substring(0, eIndicator));
                    tmpclass.endMin=Integer.parseInt(tmpEtime.substring(eIndicator+1, tmpEtime.length()));
                    classList.add(tmpclass);
                    j+=2;
                }
            }
        }
        System.out.println("수업 목록");
        for(int i=0;i<classList.size();i++){
            System.out.println(" ["+classList.get(i).title+"] ["+classList.get(i).location+"] ["+classList.get(i).weekDay+"] ["+classList.get(i).startHour+":"+classList.get(i).startMin+"] ["+classList.get(i).endHour+":"+classList.get(i).endMin+"]");
        }
        System.out.println("end of sogangCrawler");
    }
}