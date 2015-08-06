package mimecast.com.recruitment.application;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mimecast.com.recruitment.R;
import mimecast.com.recruitment.model.EmailHeaderModel;
import mimecast.com.recruitment.task.DownloadEmailHeaderListTask;
import mimecast.com.recruitment.task.EmailHeaderListReverseChronologicalSortTask;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class CancellableSortActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancellable_sort);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cancellable_sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements DownloadEmailHeaderListTask.EmailHeaderListDownloadListener
            , EmailHeaderListReverseChronologicalSortTask.EmailHeaderListSortListener {

        private View iInflatedFragmentView;
        private AsyncTask<Void, Void, Void> iAsyncTask;
        private ArrayList<EmailHeaderModel> iEmailHeaderList;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            iInflatedFragmentView = inflater.inflate(R.layout.fragment_cancellable_sort, container, false);
            if(null != iInflatedFragmentView) {
                View sortButton = iInflatedFragmentView.findViewById(R.id.sortButton);
                if(null != sortButton) {
                    sortButton.setEnabled(false);
                    sortButton.setVisibility(View.VISIBLE);
                    sortButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setEnabled(false);
                            View cancelButton = iInflatedFragmentView.findViewById(R.id.cancelButton);
                            if(null != cancelButton) {
                                v.setVisibility(View.GONE);
                                cancelButton.setVisibility(View.VISIBLE);
                                cancelButton.setEnabled(true);
                                iAsyncTask = new EmailHeaderListReverseChronologicalSortTask(iEmailHeaderList, PlaceholderFragment.this);
                                iAsyncTask.execute();
                            }
                        }
                    });
                    new DownloadEmailHeaderListTask(this).execute();
                }
                View cancelButton = iInflatedFragmentView.findViewById(R.id.cancelButton);
                if(null != cancelButton) {
                    cancelButton.setEnabled(false);
                    cancelButton.setVisibility(View.GONE);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setEnabled(false);
                            v.setVisibility(View.INVISIBLE);
                            if (null != iAsyncTask) {
                                iAsyncTask.cancel(false); // DO NOT CHANGE THIS
                                iAsyncTask = null;
                            }
                        }
                    });
                }
            }
            return iInflatedFragmentView;
        }

        @Override
        public void onEmailHeaderListDownloaded(ArrayList<EmailHeaderModel> anEmailHeaderList) {
            Activity fragmentActivity = getActivity();
            if(null != fragmentActivity && !fragmentActivity.isFinishing() && null != iInflatedFragmentView) {
                iEmailHeaderList = anEmailHeaderList;
                ListView emailHeaderListView = (ListView) iInflatedFragmentView.findViewById(R.id.EmailHeaderListView);
                if (null != emailHeaderListView) {
                    ArrayAdapter<EmailHeaderModel> emailHeaderListAdapter = new EmailHeaderArrayAdapter(fragmentActivity, anEmailHeaderList);
                    emailHeaderListView.setAdapter(emailHeaderListAdapter);
                }
                View sortButton = iInflatedFragmentView.findViewById(R.id.sortButton);
                if (null != sortButton) {
                    sortButton.setEnabled(true);
                }
            }
        }

        @Override
        public void onEmailHeaderListSorted(ArrayList<EmailHeaderModel> anEmailHeaderList) {
            if(null != iInflatedFragmentView) {
                iEmailHeaderList = anEmailHeaderList;
                View cancelButton = iInflatedFragmentView.findViewById(R.id.cancelButton);
                View sortButton = iInflatedFragmentView.findViewById(R.id.sortButton);
                if(null != cancelButton && null != sortButton) {
                    cancelButton.setEnabled(false);
                    sortButton.setEnabled(true);
                    sortButton.setVisibility(View.VISIBLE);
                    cancelButton.setVisibility(View.GONE);
                }
                ListView emailHeaderListView = (ListView) iInflatedFragmentView.findViewById(R.id.EmailHeaderListView);
                if (null != emailHeaderListView) {
                    ListAdapter emailHeaderListAdapter = emailHeaderListView.getAdapter();
                    if(emailHeaderListAdapter instanceof BaseAdapter) {
                        ((BaseAdapter)emailHeaderListAdapter).notifyDataSetChanged();
                    }
                }
                Toast.makeText(getActivity(), "sorting AsyncTask completed or cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
