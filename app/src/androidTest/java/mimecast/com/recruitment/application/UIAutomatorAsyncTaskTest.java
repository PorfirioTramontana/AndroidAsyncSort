package mimecast.com.recruitment.application;

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
public class UIAutomatorAsyncTaskTest {

    private static final String BASIC_SAMPLE_PACKAGE
            = "mimecast.com.recruitment";
    private static final int LAUNCH_TIMEOUT = 5000;
    private static final String STRING_TO_BE_TYPED = "UiAutomator";
    private UiDevice mDevice;


    @Test
    public void PrimoTest() throws InterruptedException, UiObjectNotFoundException, RemoteException {
        // SEQUENZA UI->TASK1->TASK2->Sort


        // Definisco i semafori, uno per ogni task, eventualmente settando il numero di task possibili
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Start = new Semaphore(1);
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Finish = new Semaphore(1);
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Finish = new Semaphore(1);
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Start = new Semaphore(1);


        //Il test mette rosso i semafori, in modo da poterne determinare autonomamente lo sblocco
        Log.d("TEST", "Il test prova ad acquisire i semafori");
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Start.acquire();
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Finish.acquire();
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Finish.acquire();
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Start.acquire();

        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
                LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        Log.d("TEST", "TEST: Avvio la activity");
        //Log.d("TEST","TEST: valori dei semafori: Download="+DownloadMezziTask.taskDownload.availablePermits()+" meteo="+LeggiMeteoTask.taskMeteo.availablePermits());
        //Thread.sleep(5000);
        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
                LAUNCH_TIMEOUT * 100);
        Log.d("TEST", "TEST: Fine before");
        //La app è stata avviata


        Thread.sleep(2000);
        Log.d("TEST", "TEST: Il test sblocca l'avvio del task meteo");
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Start.release();
        Log.d("TEST", "TEST: Rilasciato il semaforo meteo");

        Thread.sleep(1000);
        Log.d("TEST", "TEST: Il test sblocca la terminazione del task meteo");
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Finish.release();
        //Log.d("TEST", "TEST: Rilasciato il semaforo meteo");

        Thread.sleep(2000);
        Log.d("TEST", "TEST: Il test sblocca l'avvio del task download");
        EmailHeaderListReverseChronologicalSortTask.task_EmailHeaderListReverseChronologicalSort_Start.release();
        //Log.d("TEST", "TEST: Rilasciato il semaforo download");

        Thread.sleep(1000);
        Log.d("TEST", "TEST: Il test sblocca la terminazione del task download");
        DownloadEmailHeaderListTask.task_DownloadEmailHeaderList_Finish.release();

        mDevice.pressHome();
        Thread.sleep(2000);
        mDevice.pressRecentApps();
        Thread.sleep(1000);
        mDevice.pressRecentApps();

        Thread.sleep(1000);

        UiObject ui = mDevice.findObject(new UiSelector().text("SORT"));
        ui.click();


        Thread.sleep(2000);
    }

}