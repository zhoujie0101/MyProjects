package com.jay;

/**
 * Hello world!
 *
 */
public class LongestIncreasingSubsequence {

    public static void main( String[] args ) {
        int[] arr1 = new int[]{10, 9, 2, 5, 3, 7, 101, 18};
        int[] arr2 = new int[]{};
        int[] arr3 = new int[]{3};
        int[] arr4 = new int[]{2, 2};
        int[] arr5 = new int[]{-2, -1};
        int[] arr6 = new int[]{1, 2, 3};
        int[] arr7 = new int[]{3, 1, 2};
        int[] arr8 = new int[]{10,9,2,5,3,4};
        System.out.println(lengthOfLIS(arr1));
        System.out.println(lengthOfLIS(arr2));
        System.out.println(lengthOfLIS(arr3));
        System.out.println(lengthOfLIS(arr4));
        System.out.println(lengthOfLIS(arr5));
        System.out.println(lengthOfLIS(arr6));
        System.out.println(lengthOfLIS(arr7));
        System.out.println(lengthOfLIS(arr8));
    }

    public static int lengthOfLIS(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        } else if(nums.length == 1) {
            return 1;     }

        int[] lengthArray = new int[nums.length];
        int maxLength;
        for(int i = 0; i < nums.length; i++) {
            lengthArray[i] = 0;
        }
        lengthArray[0] = 1;
        // 10, 9, 2, 5, 3, 7, 101, 18
        int lastPos = 0;
        for(int i = 1; i < nums.length; i++) {
            maxLength = lengthArray[i];
            for(int j = 0; j < i; j++) {
                if(nums[i] > nums[j] && nums[j] > nums[lastPos]) {
                    maxLength++;
                    lastPos = j;
                }
            }
            lengthArray[i] = maxLength + 1;
        }
        maxLength = 0;
        for(int i = 0; i < lengthArray.length; i++) {
            if(lengthArray[i] > maxLength) {
                maxLength = lengthArray[i];
            }
        }

        return maxLength;
    }
}
