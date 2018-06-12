package mimecast.com.recruitment.task;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import it.unina.ptramont.TaskTestUtility;
import mimecast.com.recruitment.model.EmailHeaderModel;
import mimecast.com.recruitment.utils.CancellableCollectionOperations;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class EmailHeaderListReverseChronologicalSortTask extends AsyncTask<Void, Void, Void> {
    //ADDED
    public static Semaphore[] sem = new Semaphore[2];
    //END ADDED

    public interface EmailHeaderListSortListener {
        void onEmailHeaderListSorted(ArrayList<EmailHeaderModel> anEmailHeaderList);
    }

    private ArrayList<EmailHeaderModel> iEmailHeaderList;
    private final EmailHeaderListSortListener iListener;

    public EmailHeaderListReverseChronologicalSortTask(ArrayList<EmailHeaderModel> anEmailHeaderList, EmailHeaderListSortListener aListener) {
        iEmailHeaderList = anEmailHeaderList;
        iListener = aListener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        //ADDED
        TaskTestUtility.startTask(sem);
        //END ADDED

        if(null != iEmailHeaderList) {
            // the next line is the only one you should need to modify in this file
            CancellableCollectionOperations.recursiveQuickSort(iEmailHeaderList, new Comparator<EmailHeaderModel>() {
                @Override
                public int compare(EmailHeaderModel lhs, EmailHeaderModel rhs) {
                    long difference = lhs.getReceivedTime() - rhs.getReceivedTime();
                    int result;

                    if (0L < difference) {
                        result = -1;
                    } else if (0L > difference) {
                        result = 1;
                    } else {
                        result = lhs.getSender().compareToIgnoreCase(rhs.getSender());
                        if(0 == result) {
                            result = lhs.getSubject().compareTo(rhs.getSubject());
                        }
                    }
                    try {
                        Thread.sleep(2L); // force sorting to take too long so user wants to cancel it
                    } catch (InterruptedException iex) {

                    }
                    //ADDED
                    TaskTestUtility.finishTask(sem);
                    //END ADDED
                    return result;
                }
            });
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
            iListener.onEmailHeaderListSorted(iEmailHeaderList);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if(null != iListener) {
            iListener.onEmailHeaderListSorted(iEmailHeaderList);
        }
    }
}