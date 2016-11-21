import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by 전세호 on 2016-11-14.
 */
public abstract class Crawler {
    public static final int UCODE_DONGGUK = 0;
    public static final int UCODE_KOOKMIN = 1;
    public static final int UCODE_SOGANG = 2;
    protected String userName="";
    protected ArrayList<ClassInfo> classList = new ArrayList<>();
    protected ArrayList<HandInfo> handList = new ArrayList<>();
    protected Document document;

    protected SFCallback onStart, onConnect, onFinish, onFail;

    protected String URL_AUTH;
    protected String URL_TIME;
    protected String URL_HOME;
    protected String URL_HAND;
    protected String FORM_ID;
    protected String FORM_PW;
    protected String ID;
    protected String PW;

    public boolean isVerified=false;


    protected static final int TIMEOUT = 15000;

}