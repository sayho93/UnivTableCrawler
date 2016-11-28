/**
 * Created by 전세호 on 2016-11-14.
 */
public class ClassInfo {
    public static final int NULLPTR = -1;

    public String title = ""; // title of Lecture

    public String location = ""; // Location of Lecture
    public String rawtime = ""; // time in raw state
    public int weekDay = NULLPTR;
    public int startHour = NULLPTR;
    public int startMin = NULLPTR;
    public int endHour = NULLPTR;
    public int endMin = NULLPTR;

    public ClassInfo(){}

    public ClassInfo(String title, String location, String rawtime, int weekDay, int startHour, int startMin, int endHour, int endMin) {
        this.title = title;
        this.location = location;
        this.rawtime = rawtime;
        this.weekDay = weekDay;
        this.startHour = startHour;
        this.startMin = startMin;
        this.endHour = endHour;
        this.endMin = endMin;
    }

    public ClassInfo clone(){
        return new ClassInfo(title, location, rawtime, weekDay, startHour, startMin, endHour, endMin);
    }

    public void categorizeKookminClass(String rawdata){     //국민대 수업정보 문자열 분류
        int titleIndex;
        int locationIndex;
        titleIndex=rawdata.indexOf("(");
        locationIndex=rawdata.indexOf(")");
        this.title=rawdata.substring(0, titleIndex-1);
        this.location=rawdata.substring(locationIndex+1, rawdata.length());
    }
}