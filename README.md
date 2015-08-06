# AndroidAsyncSort
Cancellable Collection sorting for Android AsyncTask

Intro:

In Android Studio 1.3, import the project.

It may ask to download the Gradle wrapper, which you should probably do.

Run the application in an Android emulator or on an Android device.

It shows a simple ListView with mocked data.

Each item in the list is supposed to represent a message: the name of the sender, the subject of the message, the time the message was received.

Messages are initially displayed in the order they were created, so the time increases as you scroll down the list.

The “Sort” button should change the order messages are displayed in the list. They should be displayed in reverse chronological order, then alphabetically by sender, then alphabetically by subject.

If you click on Sort, the Button will become a “Cancel” button. After several seconds (less than 1 minute on a decent computer), sorting will finish and you will notice that the order of the item has not changed.

Tasks:

1) Your first task is to make sorting work.

Read mimecast.com.recruitment.task.EmailHeaderListReverseChronologicalSortTask

You will find a java.util.Comparator that defines the order we want after sorting.

In order to make sorting work correctly, all you have to do is implement mimecast.com.recruitment.utils.CancellableCollectionOperations.swap().

You can then run the application again and check that your implementation is working.

However, if you click on “Cancel” while sorting, you will notice that the operation still takes just as much time as sorting.

2) Your second task is to allow sorting to be cancelled

Notice that CancellableCollectionOperations.recursiveQuickSort() is a simple implementation of https://en.wikipedia.org/wiki/Quicksort and is called from EmailHeaderListReverseChronologicalSortTask.doInBackground().

You should implement CancellableCollectionOperations.cancellableRecursiveQuickSort()

Update the Quicksort implementation so that it regularly checks whether the android.os.AsyncTask has been cancelled.

When you’re done, run the application. Click Sort. Click Cancel. You should be able to click on Sort again very quickly, without having to wait several seconds for the Button to be visible again.

If sorting is cancelled, the ListView can display the items without any order.


3) Your third task is implement an iterative version of the sorting algorithm

The basic implementation of Quicksort is recursive.

Unfortunately, we may want to sort hundreds or thousands of items even on a mobile phone.

Therefore, we want to de-recurse the sorting algorithm.

You should implement CancellableCollectionOperations.iterativeQuickSort()

Notice that it doesn’t need to be cancellable, which will change the User Experience of the application.

4) Finally, send us your CancellableCollectionOperations.java file for review.

Background for this exercise can be found at http://stackoverflow.com/questions/25429263/android-cancellable-java-collection-sort-in-asynctask-doinbackground
