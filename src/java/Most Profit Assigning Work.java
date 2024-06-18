class Solution {
    public int _maxProfitAssignment(int[] difficulty, int[] profit, int[] worker) {
        int[][] dp= new int[difficulty.length][difficulty.length];
        int ans=0;

        for(int i=0; i<difficulty.length; i++){
            dp[i]= new int[]{difficulty[i], profit[i]};
        }

        Arrays.sort( dp, (a,b)-> b[1]-a[1]);

        for( int i : worker){
           int j=0, val=0;

           ans+=findTheMaxProfit(i, dp );
           /*
           while( j< dp.length && i < dp[j][0] ){  j++; }
           if( j<dp.length && i>=dp[j][0]) ans+=dp[j][1];
           */
        }
        return ans;
    }
    int findTheMaxProfit(int num, int[][] dp){
        for( int i=0; i< dp.length ; i++){
            if( num>= dp[i][0]) return dp[i][1];
        }
        return 0;
    }


    public int maxProfitAssignment(
        int[] difficulty,
        int[] profit,
        int[] worker
    ) {
        List<int[]> jobProfile = new ArrayList<>();
        jobProfile.add(new int[] { 0, 0 });
        for (int i = 0; i < difficulty.length; i++) {
            jobProfile.add(new int[] { difficulty[i], profit[i] });
        }

        // Sort by difficulty values in increasing order.
        Collections.sort(jobProfile, (a, b) -> Integer.compare(a[0], b[0]));
        for (int i = 0; i < jobProfile.size() - 1; i++) {
            jobProfile.get(i + 1)[1] = Math.max(
                jobProfile.get(i)[1],
                jobProfile.get(i + 1)[1]
            );
        }

        int netProfit = 0;
        for (int i = 0; i < worker.length; i++) {
            int ability = worker[i];

            // Find the job with just smaller or equal difficulty than ability.
            int l = 0, r = jobProfile.size() - 1, jobProfit = 0;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (jobProfile.get(mid)[0] <= ability) {
                    jobProfit = Math.max(jobProfit, jobProfile.get(mid)[1]);
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }

            // Increment profit of current worker to total profit.
            netProfit += jobProfit;
        }
        return netProfit;
    }
}

/**

826. Most Profit Assigning Work
Solved
Medium
Topics
Companies
You have n jobs and m workers. You are given three arrays: difficulty, profit, and worker where:

difficulty[i] and profit[i] are the difficulty and the profit of the ith job, and
worker[j] is the ability of jth worker (i.e., the jth worker can only complete a job with difficulty at most worker[j]).
Every worker can be assigned at most one job, but one job can be completed multiple times.

For example, if three workers attempt the same job that pays $1, then the total profit will be $3. If a worker cannot complete any job, their profit is $0.
Return the maximum profit we can achieve after assigning the workers to the jobs.

 

Example 1:

Input: difficulty = [2,4,6,8,10], profit = [10,20,30,40,50], worker = [4,5,6,7]
Output: 100
Explanation: Workers are assigned jobs of difficulty [4,4,6,6] and they get a profit of [20,20,30,30] separately.
Example 2:

Input: difficulty = [85,47,57], profit = [24,66,99], worker = [40,25,25]
Output: 0
 

Constraints:

n == difficulty.length
n == profit.length
m == worker.length
1 <= n, m <= 104
1 <= difficulty[i], profit[i], worker[i] <= 105
 */
