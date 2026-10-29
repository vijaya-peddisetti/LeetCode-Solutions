import java.util.*;

class Solution {
    int[] parent, depth, size, heavy, head, pos;
    int[] tree, nodeAtPositionArray;
    int timer;
    char[] chars;
    List<Integer>[] adj;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                        String s, String[] queries) {
        chars = s.toCharArray();
        adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];
        nodeAtPositionArray = new int[n];

        Arrays.fill(heavy, -1);

        dfs(0, -1);
        decompose(0, 0);

        tree = new int[4 * n];
        build(1, 0, n - 1);

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.split(" ");
            int u = Integer.parseInt(parts[1]);

            if (parts[0].equals("update")) {
                char c = parts[2].charAt(0);
                chars[u] = c;

                update(1, 0, n - 1, pos[u],
                       1 << (c - 'a'));
            } else {
                int v = Integer.parseInt(parts[2]);
                int mask = queryPath(u, v);

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    int dfs(int u, int p) {
        parent[u] = p;
        size[u] = 1;
        int maxSize = 0;

        for (int v : adj[u]) {
            if (v == p) continue;

            depth[v] = depth[u] + 1;
            int childSize = dfs(v, u);
            size[u] += childSize;

            if (childSize > maxSize) {
                maxSize = childSize;
                heavy[u] = v;
            }
        }

        return size[u];
    }

    void decompose(int u, int h) {
        head[u] = h;
        pos[u] = timer++;
        nodeAtPositionArray[pos[u]] = u;

        if (heavy[u] != -1) {
            decompose(heavy[u], h);
        }

        for (int v : adj[u]) {
            if (v != parent[u] && v != heavy[u]) {
                decompose(v, v);
            }
        }
    }

    int queryPath(int u, int v) {
        int mask = 0;

        while (head[u] != head[v]) {
            if (depth[head[u]] < depth[head[v]]) {
                int temp = u;
                u = v;
                v = temp;
            }

            mask ^= query(1, 0, chars.length - 1,
                          pos[head[u]], pos[u]);

            u = parent[head[u]];
        }

        if (depth[u] > depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        mask ^= query(1, 0, chars.length - 1,
                      pos[u], pos[v]);

        return mask;
    }

    void build(int node, int l, int r) {
        if (l == r) {
            int u = nodeAtPositionArray[l];
            tree[node] = 1 << (chars[u] - 'a');
            return;
        }

        int mid = (l + r) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);

        tree[node] = tree[node * 2] ^ tree[node * 2 + 1];
    }

    void update(int node, int l, int r, int idx, int value) {
        if (l == r) {
            tree[node] = value;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, value);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, value);
        }

        tree[node] = tree[node * 2] ^ tree[node * 2 + 1];
    }

    int query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return tree[node];
        }

        if (r < ql || l > qr) {
            return 0;
        }

        int mid = (l + r) / 2;

        return query(node * 2, l, mid, ql, qr)
             ^ query(node * 2 + 1, mid + 1, r, ql, qr);
    }
}