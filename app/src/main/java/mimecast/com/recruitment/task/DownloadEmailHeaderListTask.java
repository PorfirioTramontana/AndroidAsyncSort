package mimecast.com.recruitment.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import mimecast.com.recruitment.model.EmailHeaderModel;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class DownloadEmailHeaderListTask extends AsyncTask<Void, Void, Void> {

    //ADDED
    public static Semaphore task_DownloadEmailHeaderList_Finish;
    public static Semaphore task_DownloadEmailHeaderList_Start;
    //END ADDED

    public interface EmailHeaderListDownloadListener {
        void onEmailHeaderListDownloaded(ArrayList<EmailHeaderModel> anEmailHeaderList);
    }

    private final EmailHeaderListDownloadListener iListener;
    private ArrayList<EmailHeaderModel> iResult;

    public DownloadEmailHeaderListTask(EmailHeaderListDownloadListener aListener) {
        iListener = aListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //ADDED
        if (task_DownloadEmailHeaderList_Start != null) {
            try {
                task_DownloadEmailHeaderList_Start.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task_DownloadEmailHeaderList_Start.release();
        }
        //END ADDED

        iResult = new ArrayList(50);
        for(int ii = 0 ; ii < 50 ; ++ii) {
            EmailHeaderModel header = new EmailHeaderModel("sender "+ (ii%10) , "subject "+(ii%1000), new Date().getTime());
            iResult.add(header);
        }

        //ADDED
        if (task_DownloadEmailHeaderList_Finish != null) {
            try {
                if (!task_DownloadEmailHeaderList_Finish.tryAcquire(15L, TimeUnit.SECONDS)) {
                    Log.d("TEST", "TASK: TIMEOUT task i");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d("TEST", "TASK: End task i");
            task_DownloadEmailHeaderList_Finish.release();
        }
        //END ADDED

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if(null != iListener) {
            iListener.onEmailHeaderListDownloaded(iResult);
        }
    }
}
