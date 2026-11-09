
import java.util.*;

class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {

        int n = arrival.length;

        int[] count = new int[k];

        TreeSet<Integer> available = new TreeSet<>();

        for (int i = 0; i < k; i++) {
            available.add(i);
        }

        PriorityQueue<int[]> busy = new PriorityQueue<>(
            (a, b) -> a[0] - b[0]
        );

        for (int i = 0; i < n; i++) {

            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                int[] server = busy.poll();
                available.add(server[1]);
            }

            if (available.isEmpty()) {
                continue;
            }

            int server = i % k;

            Integer chosen = available.ceiling(server);

            if (chosen == null) {
                chosen = available.first();
            }

            available.remove(chosen);

            count[chosen]++;

            busy.offer(new int[]{
                arrival[i] + load[i], chosen
            });
        }

        int max = 0;

        for (int c : count) {
            max = Math.max(max, c);
        }

        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            if (count[i] == max) {
                ans.add(i);
            }
        }

        return ans;
    }
}
