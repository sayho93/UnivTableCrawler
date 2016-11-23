import java.io.IOException;

/**
 * Created by 전세호 on 2016-11-14.
 */
public class main {


    public static void main(String[] args) {
        DonggukCrawler donggukCrawler = new DonggukCrawler("2014112021", "gpswpf12!!", new SFCallback() {
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

        SogangCrawler sogangCrawler = new SogangCrawler("20131014", "wjsgytjd5+", new SFCallback() {
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

        KookminCrawler kookminCrawler = new KookminCrawler("20145160", "qkrtkddl2#", new SFCallback() {
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
                try{
                    sogangCrawler.doInBackground();
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    kookminCrawler.doInBackground();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
