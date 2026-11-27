class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;

        int[] dp = new int[budget + 1];

        for (int[] item : items) {
            minPrice = Math.min(minPrice, item[1]);
        }

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            int free = 0;

            for (int j = 0; j < n; j++) {
                if (items[j][0] % factor == 0) {
                    free++;
                }
            }

            for (int j = budget; j >= price; j--) {
                dp[j] = Math.max(dp[j],
                        dp[j - price] + free);
            }
        }

        int ans = 0;

        for (int i = 0; i <= budget; i++) {
            ans = Math.max(ans,
                    dp[i] + (budget - i) / minPrice);
        }

        return ans;
    }
}