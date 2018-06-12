package mimecast.com.recruitment.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import it.unina.ptramont.TaskTestUtility;
import mimecast.com.recruitment.model.EmailHeaderModel;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class DownloadEmailHeaderListTask extends AsyncTask<Void, Void, Void> {

    //ADDED
    public static Semaphore[] sem = new Semaphore[2];
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
        TaskTestUtility.startTask(sem);
        //END ADDED

        iResult = new ArrayList(50);
        for(int ii = 0 ; ii < 50 ; ++ii) {
            EmailHeaderModel header = new EmailHeaderModel("sender "+ (ii%10) , "subject "+(ii%1000), new Date().getTime());
            iResult.add(header);
        }

        //ADDED
        TaskTestUtility.finishTask(sem);
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
