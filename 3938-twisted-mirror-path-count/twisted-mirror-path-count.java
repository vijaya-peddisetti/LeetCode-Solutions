class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        long MOD = 1000000007L;

        long[] down = new long[n];
        down[0] = 1;

        for (int i = 0; i < m; i++) {
            long right = 0;

            for (int j = 0; j < n; j++) {

                if (grid[i][j] == 1) {
                    long temp = right;
                    right = down[j];
                    down[j] = temp;
                } 
                else {
                    long total = (right + down[j]) % MOD;

                    right = total;
                    down[j] = total;
                }
            }
        }

        return (int) down[n-1];
    }
}