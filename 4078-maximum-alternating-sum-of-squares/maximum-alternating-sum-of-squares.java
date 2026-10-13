import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;

        int[] squares = new int[n];

        for (int i = 0; i < n; i++) {
            squares[i] = nums[i] * nums[i];
        }

        Arrays.sort(squares);

        long ans = 0;
        int m = n / 2;

        for (int i = 0; i < m; i++) {
            ans -= squares[i];
        }

        for (int i = m; i < n; i++) {
            ans += squares[i];
        }

        return ans;
    }
}