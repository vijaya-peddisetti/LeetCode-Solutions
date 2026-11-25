import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;

        long mod1 = 1000000007;
        long mod2 = 1000000009;
        long base = 233;

        long[] h1 = new long[n + 1];
        long[] h2 = new long[n + 1];
        long[] p1 = new long[n + 1];
        long[] p2 = new long[n + 1];

        p1[0] = 1;
        p2[0] = 1;

        for (int i = 0; i < n; i++) {
            p1[i + 1] = (p1[i] * base) % mod1;
            p2[i + 1] = (p2[i] * base) % mod2;

            h1[i + 1] = (h1[i] * base + nums[i]) % mod1;
            h2[i + 1] = (h2[i] * base + nums[i]) % mod2;
        }

        int low = 1;
        int high = n;
        int ans = n;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            HashMap<Long, Integer> map = new HashMap<>();

            for (int i = 0; i + mid <= n; i++) {
                int j = i + mid;

                long hash1 = (h1[j] - h1[i] * p1[mid] % mod1 + mod1) % mod1;
                long hash2 = (h2[j] - h2[i] * p2[mid] % mod2 + mod2) % mod2;

                long hash = (hash1 << 32) | hash2;

                map.put(hash, map.getOrDefault(hash, 0) + 1);
            }

            boolean found = false;

            for (int count : map.values()) {
                if (count == 1) {
                    found = true;
                    break;
                }
            }

            if (found) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return ans;
    }
}