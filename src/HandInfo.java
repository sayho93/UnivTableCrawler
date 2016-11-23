/**
 * Created by 전세호 on 2016-11-14.
 */
public class HandInfo {

    public HandInfo(){}
    public HandInfo(String no, String subject, String name, String date, String handin, String open, String score){
        this.no = no;       //서강에는 #
        this.subject = subject;
        this.name = name;
        this.date = date;       //서강: 마감일: asd 통째로 긁어서 저장
        this.handin = handin;      //상태 자르고 저장
        this.open = open;       //제출방식 통째로
        this.score = score;
    }

    public String no, subject, name, date, handin, open, score;
}