class Solution {
    public int minOperations(String s) {
        int n = s.length();

        // Check if the string is already sorted
        boolean sorted = true;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }

        if (sorted) {
            return 0;
        }

        // A string of length 2 cannot be sorted
        // if it is not already sorted
        if (n == 2) {
            return -1;
        }

        char min = 'z';
        char max = 'a';

        // Find minimum and maximum characters
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            min = (char) Math.min(min, c);
            max = (char) Math.max(max, c);
        }

        // One operation is enough
        if (s.charAt(0) == min || s.charAt(n - 1) == max) {
            return 1;
        }

        // Check whether min or max occurs in the middle
        for (int i = 1; i < n - 1; i++) {
            char c = s.charAt(i);

            if (c == min || c == max) {
                return 2;
            }
        }

        // Otherwise, three operations are needed
        return 3;
    }
}