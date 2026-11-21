import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;
        List<Integer>[] tree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
        }

        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] ans = new int[n];
        Arrays.fill(ans, 1);

        int[] last = new int[26];
        Arrays.fill(last, -1);

        dfs(0, -1, tree, s, ans, last);

        return ans;
    }

    void dfs(int node, int par, List<Integer>[] tree,
             String s, int[] ans, int[] last) {

        int c = s.charAt(node) - 'a';
        int old = last[c];
        last[c] = node;

        for (int child : tree[node]) {
            dfs(child, node, tree, s, ans, last);
        }

        int p = old == -1 ? par : old;

        if (p != -1) {
            ans[p] += ans[node];
        }

        last[c] = old;
    }
}