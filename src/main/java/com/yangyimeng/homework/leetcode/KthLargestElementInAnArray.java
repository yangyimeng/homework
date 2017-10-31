package com.yangyimeng.homework.leetcode;


public class KthLargestElementInAnArray {

    public int findKthLargest(int[] nums, int k) {
        adjustHeap(nums, nums.length);
        for (int i = 1; i < k; i++) {
            nums[0] = nums[nums.length - i];
            adjustHeap(nums, nums.length - i);
        }
        return nums[0];
    }

    public void adjustHeap(int [] nums, int k) {
        for (int i = k / 2; i >= 0 && i < k; i--) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            if (left < k) {
                if (nums[left] > nums[i]) {
                    int tmp = nums[i];
                    nums[i] = nums[left];
                    nums[left] = tmp;
                }
            }
            if (right < k) {
                if (nums[right] > nums[i]) {
                    int tmp = nums[i];
                    nums[i] = nums[right];
                    nums[right] = tmp;
                }
            }
        }
    }


    public static void main(String [] args) {
        int [] nums = new int[] {-1, 2, 0};
        int k = 3;
        System.out.println(new KthLargestElementInAnArray().findKthLargest(nums, k));
    }

}
