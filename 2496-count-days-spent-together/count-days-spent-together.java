class Solution {
    public int countDaysTogether(String arriveAlice, String leaveAlice,
                                 String arriveBob, String leaveBob) {

        int[] days = {31, 28, 31, 30, 31, 30,
                      31, 31, 30, 31, 30, 31};

        int[] startAlice = convert(arriveAlice);
        int[] endAlice = convert(leaveAlice);
        int[] startBob = convert(arriveBob);
        int[] endBob = convert(leaveBob);

        int start = Math.max(toDay(startAlice, days), toDay(startBob, days));
        int end = Math.min(toDay(endAlice, days), toDay(endBob, days));

        return Math.max(0, end - start + 1);
    }

    private int[] convert(String date) {
        String[] parts = date.split("-");
        return new int[] {
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1])
        };
    }

    private int toDay(int[] date, int[] days) {
        int total = date[1];

        for (int i = 0; i < date[0] - 1; i++) {
            total += days[i];
        }

        return total;
    }
}