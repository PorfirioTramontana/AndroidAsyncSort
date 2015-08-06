package mimecast.com.recruitment.task;

import android.os.AsyncTask;

import mimecast.com.recruitment.model.EmailHeaderModel;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class DownloadEmailHeaderListTask extends AsyncTask<Void, Void, Void> {

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
        iResult = new ArrayList(50);
        for(int ii = 0 ; ii < 50 ; ++ii) {
            EmailHeaderModel header = new EmailHeaderModel("sender "+ (ii%10) , "subject "+(ii%1000), new Date().getTime());
            iResult.add(header);
        }
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
