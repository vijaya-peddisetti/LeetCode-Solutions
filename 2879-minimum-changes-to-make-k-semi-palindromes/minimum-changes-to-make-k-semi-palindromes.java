import java.util.*;

class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int[][] cost = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int len = j - i + 1;
                cost[i][j] = len;

                for (int d = 1; d < len; d++) {
                    if (len % d == 0) {
                        int changes = 0;

                        for (int x = 0; x < d; x++) {
                            int l = i + x;
                            int r = j - d + 1 + x;

                            while (l < r) {
                                if (s.charAt(l) != s.charAt(r)) {
                                    changes++;
                                }
                                l += d;
                                r -= d;
                            }
                        }

                        cost[i][j] = Math.min(cost[i][j], changes);
                    }
                }
            }
        }

        int[][] dp = new int[n + 1][k + 1];

        for (int[] row : dp) {
            Arrays.fill(row, 1000);
        }

        dp[0][0] = 0;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                for (int h = 0; h < i - 1; h++) {
                    dp[i][j] = Math.min(dp[i][j],
                            dp[h][j - 1] + cost[h][i - 1]);
                }
            }
        }

        return dp[n][k];
    }
}