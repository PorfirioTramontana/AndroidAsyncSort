package mimecast.com.recruitment;


import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import mimecast.com.recruitment.task.DownloadEmailHeaderListTask;
import mimecast.com.recruitment.task.EmailHeaderListReverseChronologicalSortTask;
import mimecast.com.recruitment.utilityTest.GeneralEvent;
import mimecast.com.recruitment.utilityTest.SpecificUIEvent;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class TestZero {

    @BeforeClass
    public static void startup() {
        GeneralEvent.setTime(GeneralEvent.NORMAL);
    }

    @Test
    public void ZeroTest() throws InterruptedException {
        GeneralEvent.declareandSetSemaphore(DownloadEmailHeaderListTask.sem);
        GeneralEvent.declareandSetSemaphore(EmailHeaderListReverseChronologicalSortTask.sem);
        GeneralEvent.start(DownloadEmailHeaderListTask.sem);
        GeneralEvent.finish(DownloadEmailHeaderListTask.sem);
        GeneralEvent.start(EmailHeaderListReverseChronologicalSortTask.sem);
        GeneralEvent.finish(EmailHeaderListReverseChronologicalSortTask.sem);

        GeneralEvent.startApp("mimecast.com.recruitment");
        GeneralEvent.pause();
        GeneralEvent.resume();
        GeneralEvent.doubleRotation();
        SpecificUIEvent.execute(SpecificUIEvent.SORT);
    }



    @After
    public void tearDown() {
        Log.d("TEST", "End test");
    }


}
