
import java.util.*;

class Solution {

    class DualHeap {
        TreeMap<Integer, Integer> lower = new TreeMap<>();
        TreeMap<Integer, Integer> upper = new TreeMap<>();

        int lowerSize = 0, upperSize = 0;
        long lowerSum = 0, upperSum = 0;

        void add(int val) {
            if (lower.isEmpty() || val <= lower.lastKey()) {
                lower.merge(val, 1, Integer::sum);
                lowerSize++;
                lowerSum += val;
            } else {
                upper.merge(val, 1, Integer::sum);
                upperSize++;
                upperSum += val;
            }
            balance();
        }

        void remove(int val) {
            if (lower.containsKey(val)) {
                removeFrom(lower, val);
                lowerSize--;
                lowerSum -= val;
            } else {
                removeFrom(upper, val);
                upperSize--;
                upperSum -= val;
            }
            balance();
        }

        void removeFrom(TreeMap<Integer, Integer> map, int val) {
            int count = map.get(val);
            if (count == 1) {
                map.remove(val);
            } else {
                map.put(val, count - 1);
            }
        }

        void balance() {
            int target = (lowerSize + upperSize + 1) / 2;

            while (lowerSize > target) {
                int val = lower.lastKey();
                removeFrom(lower, val);
                lowerSize--;
                lowerSum -= val;

                upper.merge(val, 1, Integer::sum);
                upperSize++;
                upperSum += val;
            }

            while (lowerSize < target) {
                int val = upper.firstKey();
                removeFrom(upper, val);
                upperSize--;
                upperSum -= val;

                lower.merge(val, 1, Integer::sum);
                lowerSize++;
                lowerSum += val;
            }
        }

        long getCost() {
            long median = lower.lastKey();

            return median * lowerSize - lowerSum
                 + upperSum - median * upperSize;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        // Step 1: Calculate cost of every window
        long[] cost = new long[n - x + 1];

        DualHeap dh = new DualHeap();

        for (int i = 0; i < n; i++) {

            dh.add(nums[i]);

            if (i >= x) {
                dh.remove(nums[i - x]);
            }

            if (i >= x - 1) {
                cost[i - x + 1] = dh.getCost();
            }
        }

        // Step 2: Dynamic Programming
        long INF = Long.MAX_VALUE / 2;

        long[][] dp = new long[k + 1][n + 1];

        for (int j = 0; j <= k; j++) {
            Arrays.fill(dp[j], INF);
        }

        // Zero subarrays require zero operations
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 0;
        }

        // Step 3: Find minimum operations
        for (int j = 1; j <= k; j++) {

            for (int i = 1; i <= n; i++) {

                // Skip current position
                dp[j][i] = dp[j][i - 1];

                // Select a window of size x
                if (i >= x) {

                    long take = dp[j - 1][i - x]
                              + cost[i - x];

                    dp[j][i] = Math.min(
                        dp[j][i],
                        take
                    );
                }
            }
        }

        return dp[k][n];
    }
}
