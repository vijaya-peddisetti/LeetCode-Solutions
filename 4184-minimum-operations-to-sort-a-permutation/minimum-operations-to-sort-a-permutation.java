class Solution {
    public int minOperations(int[] nums) {
        int n = nums.length;

        if (n <= 1) {
            return 0;
        }

        int zero = -1;
        int last = -1;

        for (int i = 0; i < n; i++) {
            if (nums[i] == 0) zero = i;
            if (nums[i] == n - 1) last = i;
        }

        boolean ascending = true;
        boolean descending = true;

        for (int i = 0; i < n; i++) {
            int next = (i + 1) % n;

            if (nums[next] != (nums[i] + 1) % n) {
                ascending = false;
            }

            if (nums[next] != (nums[i] - 1 + n) % n) {
                descending = false;
            }
        }

        if (ascending) {
            return Math.min(zero, n - zero + 2);
        }

        if (descending) {
            return Math.min(last + 1, n - last + 1);
        }

        return -1;
    }
}