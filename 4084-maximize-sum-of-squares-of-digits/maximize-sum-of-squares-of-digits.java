class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        while (num > 0) {
            int digit = Math.min(9, sum);
            result.append(digit);
            sum -= digit;
            num--;
        }

        if (sum > 0) {
            return "";
        }

        return result.toString();
    }
}