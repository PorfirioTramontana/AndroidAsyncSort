package mimecast.com.recruitment.utils;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by Michael Aubert on 27/02/2015.
 */

public class CancellableCollectionOperations {

    /**
     *
     * Java implementation of the recursive QuickSort on Wikipedia
     *
     * @param aList
     * @param aComparator
     * @param <T>
     */

    public static <T> void recursiveQuickSort(ArrayList<T> aList, Comparator<? super T> aComparator) {
        recursiveQuickSort(aList, aComparator, 0, aList.size() - 1);
    }

    public static <T> void recursiveQuickSort(ArrayList<T> aList
                            , Comparator<? super T> aComparator, int aLeftIndex, int aRightIndex) {

        if(aLeftIndex < aRightIndex) {
            int middle = findQuickSortPartition(aList, aComparator, aLeftIndex, aRightIndex);
            recursiveQuickSort(aList, aComparator, aLeftIndex, middle -1);
            recursiveQuickSort(aList, aComparator, middle + 1, aRightIndex);
        }
    }

    private static <T> int findQuickSortPartition(ArrayList<T> aList
                            , Comparator<? super T> aComparator, int aLeftIndex, int aRightIndex) {

        int pivotIndex = aLeftIndex + ((aRightIndex - aLeftIndex) / 2);
        T pivot = aList.get(pivotIndex);
        swap(aList, pivotIndex, aRightIndex);
        int storeIndex = aLeftIndex;
        for(int ii = aLeftIndex ; ii < aRightIndex ; ++ii) {
            if(0 > aComparator.compare(aList.get(ii), pivot)) {
                swap(aList, storeIndex, ii);
                ++storeIndex;
            }
        }
        swap(aList, storeIndex, aRightIndex);
        return storeIndex;
    }

    /**
     *
     * Minimal coding test
     * Implement the swapping method
     *
     * Running the application allows manual testing
     *
     * Do not be surprised if you need to debug a little before sorting works.
     *
     * @param aList
     * @param aLeftIndex
     * @param aRightIndex
     * @param <T>
     */

    private static <T> void swap(ArrayList<T> aList, int aLeftIndex, int aRightIndex) {
    }

    /**
     *
     * Intermediary coding test.
     * Abandon sorting if the AsyncTask gets cancelled.
     * It could happen at any time.
     *
     * Modify EmailHeaderListReverseChronologicalSortTask to call this method
     *
     * Running the application allows manual testing
     *
     * @param aList
     * @param aComparator
     * @param anAsyncTask
     * @param <T>
     */

    public static <T> void cancellableRecursiveQuickSort(ArrayList<T> aList
                    , Comparator<? super T> aComparator, AsyncTask<Void,Void,Void> anAsyncTask) {

    }

    /**
     * Final coding test
     * Implement iterative QuickSort.
     *
     * De-recurse the algorithm.
     *
     * Use this version instead of the recursive one above.
     *
     *
     *
     * @param aList
     * @param aComparator
     * @param <T>
     */


    public static <T> void iterativeQuickSort(ArrayList<T> aList, Comparator<? super T> aComparator) {

    }


}
