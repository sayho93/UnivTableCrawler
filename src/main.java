import java.io.IOException;

/**
 * Created by 전세호 on 2016-11-14.
 */
public class main {


    public static void main(String[] args) {
        DonggukCrawler donggukCrawler = new DonggukCrawler("2014112102", "fishcreek1!", new SFCallback() {
            @Override
            public void callback() {

            }
        }, new SFCallback() {
            @Override
            public void callback() {

            }
        }, new SFCallback() {
            @Override
            public void callback() {

            }
        });

        new Thread(){
            @Override
            public void run(){
                try {
                    donggukCrawler.doInBackground();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
