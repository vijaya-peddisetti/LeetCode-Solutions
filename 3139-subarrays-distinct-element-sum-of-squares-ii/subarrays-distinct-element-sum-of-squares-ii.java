class Solution {
    int mod = 1000000007;
    long[] sum, sq, lazy;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int l = last[x];

            update(1, 0, n - 1, l, i);
            ans = (ans + sq[1]) % mod;

            last[x] = i + 1;
        }

        return (int) ans;
    }

    void push(int node, int start, int end) {
        if (lazy[node] == 0) return;

        int mid = (start + end) / 2;
        apply(node * 2, mid - start + 1, lazy[node]);
        apply(node * 2 + 1, end - mid, lazy[node]);

        lazy[node] = 0;
    }

    void apply(int node, int len, long v) {
        sq[node] = (sq[node] + 2 * v * sum[node] 
                    + v * v * len) % mod;
        sum[node] = (sum[node] + v * len) % mod;
        lazy[node] = (lazy[node] + v) % mod;
    }

    void update(int node, int start, int end, int l, int r) {
        if (l > end || r < start) return;

        if (l <= start && end <= r) {
            apply(node, end - start + 1, 1);
            return;
        }

        push(node, start, end);

        int mid = (start + end) / 2;
        update(node * 2, start, mid, l, r);
        update(node * 2 + 1, mid + 1, end, l, r);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % mod;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % mod;
    }
}