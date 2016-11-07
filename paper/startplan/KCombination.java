package paper.startplan;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by gyk on 2016/10/31.
 * KCombination类得到数组arr中，k个元素的所有组合，如{1,2,3,4,5}中3个元素->{1,2,3},{1,2,4}...{1,4,5},{3,4,5}
 */
class KCombination {

    private List<int[]> result;

    private void combinationUtil(int arr[], int data[], int start,
                                int end, int index, int r)
    {
        // Current combination is ready to be printed, print it
        if (index == r)
        {
            int l = data.length;
            int[] temp = new int[l];
            System.arraycopy(data,0,temp,0,l);
            result.add(temp);
            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i=start; i<=end && end-i+1 >= r-index; i++)
        {
            data[index] = arr[i];
            combinationUtil(arr, data, i+1, end, index+1, r);
        }
    }

    // The main function that prints all combinations of size r
    // in arr[] of size n. This function mainly uses combinationUtil()
    private void printCombination(int arr[], int n, int r)
    {
        // A temporary array to store all combination one by one
        int data[]=new int[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n-1, 0, r);
    }

    public List<int[]> getCombinations(int[] arr, int r){
         result = new LinkedList<>();
        int n = arr.length;
        printCombination(arr,n,r);
//        int[][] combinations = new int[result.size()][r];
//        return  result.toArray(combinations);
        return  result;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1000,660,600,400,330};
        KCombination kc = new KCombination();
        List<int[]> r = kc.getCombinations(arr,3);
        for (int i=0;i<r.size();i++){
            for (int j=0;j<3;j++){
                System.out.print(r.get(i)[j]+" ");
            }
            System.out.println();
        }
    }
}
