package mimecast.com.recruitment.task;

import android.os.AsyncTask;

import mimecast.com.recruitment.model.EmailHeaderModel;
import mimecast.com.recruitment.utils.CancellableCollectionOperations;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class EmailHeaderListReverseChronologicalSortTask extends AsyncTask<Void, Void, Void> {

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
                    return result;
                }
            });
        }
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