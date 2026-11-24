class Solution {
    public int maxLength(int[] nums) {
        int max = 0;

        for (int i = 0; i < nums.length; i++) {
            int product = 1;

            for (int j = i; j < nums.length; j++) {
                product *= nums[j];

                int gcd = 0;
                for (int k = i; k <= j; k++) {
                    gcd = findGCD(gcd, nums[k]);
                }

                int lcm = 1;
                for (int k = i; k <= j; k++) {
                    lcm = (lcm * nums[k]) / findGCD(lcm, nums[k]);
                }

                if (product == gcd * lcm) {
                    max = Math.max(max, j - i + 1);
                }
            }
        }

        return max;
    }

    public int findGCD(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}