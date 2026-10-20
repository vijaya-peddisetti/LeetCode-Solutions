class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        int sum = 0;

        for (int k = 1; k <= n; k++) {
            if (n % k == 0 && isSortable(nums, k)) {
                sum += k;
            }
        }

        return sum;
    }

    private boolean isSortable(int[] nums, int k) {
        int n = nums.length;
        int prevMax = Integer.MIN_VALUE;

        for (int start = 0; start < n; start += k) {
            int end = start + k;
            int min = nums[start];
            int max = nums[start];
            int count = 0;

            for (int i = start; i < end; i++) {
                min = Math.min(min, nums[i]);
                max = Math.max(max, nums[i]);

                if (i < end - 1 && nums[i] > nums[i + 1]) {
                    count++;
                }
            }

            if (nums[end - 1] > nums[start]) {
                count++;
            }

            if (count > 1 || prevMax > min) {
                return false;
            }

            prevMax = max;
        }

        return true;
    }
}