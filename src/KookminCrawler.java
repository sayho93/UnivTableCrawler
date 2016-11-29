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
        //국민대 홈페이지 연결부분
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
        //테이블 전체 내용 로그
        ClassInfo ClistA[][]=new ClassInfo[15][6];      //ShortTime용 배열
        ClassInfo ClistB[][]=new ClassInfo[11][6];      //LongTime용 배열
        int lineIndicator=0;

        for(Element trTags: table.select("tr")){        //tr 단위로 읽는다
            int actualLine=-1;
            String rawtimeShort="";
            String rawtimeLong="";
            String startRawtimeShort="";
            String startRawtimeLong="";
            String endRawtimeShort="";
            String endRawtimeLong="";
            int swungDashIndicatorShort=-1;
            int swungDashIndicatorLong=-1;

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

            if(lineIndicator==0){               //first line: 안읽도록 처리하기로 함
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
            else if(lineIndicator%6==1 || lineIndicator==25){        //type 1: tr에 short, long 둘 다 있는 타입
                rawtimeShort=trTags.child(1).text();
                rawtimeLong=trTags.child(2).text();
                swungDashIndicatorShort=rawtimeShort.indexOf("~");
                swungDashIndicatorLong=rawtimeLong.indexOf("~");
                startRawtimeShort=rawtimeShort.substring(swungDashIndicatorShort-5, swungDashIndicatorShort);
                startRawtimeLong=rawtimeLong.substring(swungDashIndicatorLong-5, swungDashIndicatorLong);
                endRawtimeShort=rawtimeShort.substring(swungDashIndicatorShort+1, rawtimeShort.length());
                endRawtimeLong=rawtimeLong.substring(swungDashIndicatorLong+1, rawtimeLong.length());
                //time slice 로그
                /*
                System.out.println("startRTS: "+startRawtimeShort);
                System.out.println("endRTS: "+endRawtimeShort);
                System.out.println("startRTL: "+startRawtimeLong);
                System.out.println("endRTL: "+endRawtimeLong);
                */
                for(int i=3;i<=14;i++){     //필요한 모든 td 순회
                    if(!trTags.child(i).text().equals(" ")){
                        ClassInfo tmpClass=new ClassInfo();     //하나씩 쑤셔박을 객체
                        if(i%2==1){     //rawtimeShort 사용
                            //actualLine=Integer.parseInt(rawtimeShort.substring(1, 3));
                            String tmpline=rawtimeShort.substring(1, 3);
                            if(tmpline.contains(" ")) actualLine=Integer.parseInt(tmpline.substring(0,1));
                            else actualLine=Integer.parseInt(tmpline);
                            //System.out.println("actual Line:"+actualLine);        //실제 저장될 배열 줄 위치
                            tmpClass.weekDay=i/2;
                            tmpClass.categorizeKookminClass(trTags.child(i).text());
                            ClistA[actualLine][i/2]=tmpClass;
                            tmpClass.insertTime(startRawtimeShort, endRawtimeShort);
                            /*
                            System.out.println("ClistA["+(actualLine)+"]["+(i/2)+"] : " +
                                    "[title:"+ClistA[actualLine][i/2].title+"|location:"+ClistA[actualLine][i/2].location+"|weekday:"+ClistA[actualLine][i/2].weekDay+"]");
                            System.out.println("sHour:"+ClistA[actualLine][i/2].startHour+" sMin:"+ClistA[actualLine][i/2].startMin+" eHour:"+ClistA[actualLine][i/2].endHour+" endMin:"+ClistA[actualLine][i/2].endMin);
                            */
                            //배열 삽입시 로그
                        }
                        else if(i%2==0){      //rawtimdLong 사용
                            String tmpline=rawtimeLong.substring(1,2);
                            String orderTable = "ABCDEFGHI";
                            actualLine=orderTable.indexOf(tmpline)+1;

                            tmpClass.weekDay=i/2 - 1;
                            tmpClass.categorizeKookminClass(trTags.child(i).text());
                            ClistB[actualLine][i/2-1]=tmpClass;
                            tmpClass.insertTime(startRawtimeLong, endRawtimeLong);

                            System.out.println("ClistB["+(actualLine)+"]["+(i/2)+"] : " +
                                    "["+ClistB[actualLine][i/2].title+ClistB[actualLine][i/2].location+ClistB[actualLine][i/2].weekDay+"]");
                            //배열 삽입시 로그
                        }
                    }
                    //System.out.println("skip");
                }

            }

            else if(lineIndicator%2==1){        //left type
                rawtimeShort=trTags.child(1).text();
                swungDashIndicatorShort=rawtimeShort.indexOf("~");
                startRawtimeShort=rawtimeShort.substring(swungDashIndicatorShort-5, swungDashIndicatorShort);
                endRawtimeShort=rawtimeShort.substring(swungDashIndicatorShort+1, rawtimeShort.length());
                String tmpline=rawtimeShort.substring(1, 3);
                if(tmpline.contains(" ")) actualLine=Integer.parseInt(tmpline.substring(0,1));
                else actualLine=Integer.parseInt(tmpline);
                for(int i=2;i<=7;i++){
                    if(!trTags.child(i).text().equals("") && !trTags.child(i).text().equals(" ")){
                        ClassInfo tmpClass=new ClassInfo();
                        tmpClass.weekDay=i-1;
                        tmpClass.categorizeKookminClass(trTags.child(i).text());
                        ClistA[actualLine][i-1]=tmpClass;
                        tmpClass.insertTime(startRawtimeShort, endRawtimeShort);
                    }
                }
            }

            else if(lineIndicator==28){     //맨 마지막시간 처리(필요 없음)
                System.out.println("do nothing");
            }

            else if(lineIndicator%6==4 || lineIndicator==27){        //right type
                rawtimeLong=trTags.child(1).text();
                swungDashIndicatorLong=rawtimeLong.indexOf("~");
                startRawtimeLong=rawtimeLong.substring(swungDashIndicatorLong-5, swungDashIndicatorLong);
                endRawtimeLong=rawtimeLong.substring(swungDashIndicatorLong+1, rawtimeLong.length());
                System.out.println(startRawtimeLong+endRawtimeLong);
                String tmpline=rawtimeLong.substring(1,2);
                String orderTable = "ABCDEFGHI";
                actualLine=orderTable.indexOf(tmpline)+1;
                for(int i=2;i<=7;i++){
                    if(!trTags.child(i).text().equals("") && !trTags.child(i).text().equals(" ")){
                        ClassInfo tmpClass=new ClassInfo();
                        tmpClass.weekDay=i-1;
                        tmpClass.categorizeKookminClass(trTags.child(i).text());
                        ClistB[actualLine][i-1]=tmpClass;
                        tmpClass.insertTime(startRawtimeLong, endRawtimeLong);
                    }
                }
            }

            else{                               //none type

            }
            lineIndicator++;
        }

        categorizeTimeTable(ClistA, ClistB);
        /*
        for(int i=0;i< ClistA.length;i++)
            for(int j=0;j<ClistA[i].length;j++)
                if(ClistA[i][j]!=null) System.out.println("["+i+","+j+":"+ClistA[i][j].title+"]");
        */

        for(int i=0;i<ClistA[0].length;i++)
            for(int j=0;j< ClistA.length;j++)
                if(ClistA[j][i]!=null) System.out.println("["+j+","+i+":"+ClistA[j][i].title+"]");
        System.out.println("end of kookminCrawler");
    }

    private void categorizeTimeTable(ClassInfo listA[][], ClassInfo listB[][]){

    }
}