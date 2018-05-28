package mimecast.com.recruitment;

import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Semaphore;

import mimecast.com.recruitment.task.DownloadEmailHeaderListTask;
import mimecast.com.recruitment.task.EmailHeaderListReverseChronologicalSortTask;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class UIAutomatorAsyncTaskTestZero {

    private static final String BASIC_SAMPLE_PACKAGE
            = "mimecast.com.recruitment";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;
    private final int SLOW=1000;
    private final int FAST=10;
    private final int VERYSLOW=5000;

    @Test
    public void ZeroTest() throws InterruptedException, UiObjectNotFoundException, RemoteException {
        int time=SLOW;
        //Semaphore Declaration
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Start = new Semaphore(1);
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Finish = new Semaphore(1);
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Finish = new Semaphore(1);
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Start = new Semaphore(1);

        //Set Semaphores
        Log.d("TEST", "Il test prova ad acquisire i semafori");
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Start.acquire();
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Finish.acquire();
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Finish.acquire();
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Start.acquire();

        // Initialize UiDevice instance and Start application
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),LAUNCH_TIMEOUT);
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),LAUNCH_TIMEOUT * 100);


        //Start Download
        Thread.sleep(time);
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Start.release();
        Log.d("TEST", "TEST: Started Download Task");

        //Finish Download
        Thread.sleep(time);
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Finish.release();
        Log.d("TEST", "TEST: Finished Download Task");

        //Start Sort
        Thread.sleep(time);
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Start.release();
        Log.d("TEST", "TEST: Start Sort Task");

        //Finish Sort
        Thread.sleep(time);
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Finish.release();
        Log.d("TEST", "TEST: Finish Sort Task");

        //pause
        Thread.sleep(time);
        mDevice.pressHome();

        //resume
        Thread.sleep(time);
        mDevice.pressRecentApps();
        Thread.sleep(time);
        mDevice.pressRecentApps();

        //Click Sort
        Thread.sleep(time);
        (mDevice.findObject(new UiSelector().resourceId("mimecast.com.recruitment:id/sortButton"))).click();
        Log.d("TEST", "TEST: Click Sort");



    }

}