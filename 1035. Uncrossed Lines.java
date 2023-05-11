
/*

1035. Uncrossed Lines
Medium
2.9K
36
Companies

You are given two integer arrays nums1 and nums2. We write the integers of nums1 and nums2 (in the order they are given) on two separate horizontal lines.

We may draw connecting lines: a straight line connecting two numbers nums1[i] and nums2[j] such that:

    nums1[i] == nums2[j], and
    the line we draw does not intersect any other connecting (non-horizontal) line.

Note that a connecting line cannot intersect even at the endpoints (i.e., each number can only belong to one connecting line).

Return the maximum number of connecting lines we can draw in this way.

 

Example 1:

Input: nums1 = [1,4,2], nums2 = [1,2,4]
Output: 2
Explanation: We can draw 2 uncrossed lines as in the diagram.
We cannot draw 3 uncrossed lines, because the line from nums1[1] = 4 to nums2[2] = 4 will intersect the line from nums1[2]=2 to nums2[1]=2.

Example 2:

Input: nums1 = [2,5,1,2,5], nums2 = [10,5,2,1,5,2]
Output: 3

Example 3:

Input: nums1 = [1,3,7,1,7,5], nums2 = [1,9,2,5,1]
Output: 2

 

Constraints:

    1 <= nums1.length, nums2.length <= 500
    1 <= nums1[i], nums2[j] <= 2000



Solution
Overview

We are given two integer arrays num1 and num2. The numbers from both the arrays are placed horziontally on two separate lines. We can draw a line between a number from num1 and the same number, if present, in num2.

Our task is to return the maximum number of lines we can draw without intersection.
Approach 1: Recursive Dynamic Programming
Intuition

If you are new to Dynamic Programming, please see our Leetcode Explore Card for more information on it!

Starting with the last (or first) number in both arrays is an intuitive way to solve this problem. If the last number of num1 equals the last number of num2, we will undoubtedly draw a line between these two numbers. This line will be included in the solution because it will not intersect with any other line drawn between the remaining numbers. The remaining numbers will then be examined, while the last number in both arrays will be ignored.

We can't draw a line between two arrays if their last numbers don't match. We now have two options to explore: drop the last number of num1 while keeping num2 as is, or drop the last number of num2 while keeping num1 as is. We choose the option in which we can draw more lines.

To solve this problem, we can use a recursion to generate all the possible cases. The recursive relation can be written as follows:

        If nums1[i - 1] == nums2[j - 1], perform answer = 1 + solve(i - 1, j - 1).
        Else, perform answer = max(solve(i, j - 1), solve(i - 1, j).

where solve(int i, int j) is a recursive method that returns the maximum number of lines we can draw by choosing the first i numbers from nums1 and the first j numbers from nums2. The solution is solve(n1, n2), where n1 and n2 are the lengths of num1 and num2 respectively.

Note that the above recursive relation is exactly the same as in the classical problem, Longest Common Subsequence (LCS). We are basically finding the LCS from the given integer arrays.

The recursion tree of the above relation would look something like this:

img

Several subproblems, such as solve(n1 - 1, n2 - 2), solve(n1 - 2, n2 - 1), etc., are solved twice in the partial recursion tree shown above. If we draw the entire recursion tree, we can see that there are many subproblems that are solved repeatedly.

To avoid this issue, we store the solution of each sub-problem and when we encounter the same subproblem again, we simply refer to the stored result. This is called memoization. As we know the current state of a sub-problem depends on the number of elements from nums1 and nums2 under consideration, we use a 2D array here to store the answer of a sub-problem.
Algorithm

    Create two integer variables n1 and n2. Intialize them to the size of nums1 and nums2.
    Create a 2D-array called memo having n1 + 1 rows and n2 + 1 columns where memo[i][j] contains the maximum number of lines we can draw by choosing the first i numbers from nums1 and the first j numbers from nums2. Initialize it to -1.
    Return solve(n1, n2, nums1, nums2, memo) where solve is a recursive method with five parameters: the first i numbers from nums1 under consideration, the first j numbers from nums2 under consideration, nums1, nums2 annd memo. We perform the following in this method:
        If i <= 0 || j <= 0, it indicates that we don't have any number in one of two arrays under consideration. We return 0.
        If memo[i][j] != -1, it indicates that we have already solved this subproblem, so we return memo[i][j].
        If nums[i - 1] == nums[j - 1], we add 1 to include the line between these numbers and recursively solve the problem ignoring the last number of both the arrays. We perform memo[i][j] = 1 + solve(i - 1, j - 1, nums1, nums2, memo).
        Otherwise, if the last numbers do not match, we recursively search for the maximum number of lines that can be drawn ignoring the last number from both the arrays. We pick the maximum of these two. We perform memo[i][j] = max(solve(i, j - 1, nums1, nums2, memo), solve(i - 1, j, nums1, nums2, memo)).
        Return memo[i][j].

Implementation
Complexity Analysis

Here, n1n1n1 is the length of nums1 and n2n2n2 is the length of nums2.

    Time complexity: O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2)
        Initializing the memo array takes O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) time.
        It will take O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) because there are O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) states to iterate over. The recursive function may be called multiple times for a given state, but due to memoization, each state is only computed once.

    Space complexity: O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2)
        The memo array consumes O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) space.
        The recursion stack used in the solution can grow to a maximum size of O(n1+n2)O(n1 + n2)O(n1+n2). When we try to form the recursion tree, we see that after each node two branches are formed (when the last numbers aren't equal). In one branch, we decrement 1 from nums1 and in other branch we decrement 1 from nums2. The recursion stack would only have one call out of the two branches. The height of such a tree will be max(n1,n2))max(n1, n2))max(n1,n2)) because at each level we are decrementing the number of elements under consideration by 1. Hence, the recursion stack will have a maximum of O(max(n1,n2))=O(n1+n2)O(max(n1, n2)) = O(n1 + n2)O(max(n1,n2))=O(n1+n2) elements.

Approach 2: Iterative Dynamic Programming
Intuition

We used memoization in the preceding approach to store the answers to subproblems in order to solve a larger problem. We can also use a bottom-up approach to solve such problems without using recursion. We build answers to subproblems iteratively first, then use them to build answers to larger problems.

Using the same method as before, we create a 2D-array dp, where dp[i][j] contains the maximum number of lines we can draw by choosing the first i numbers from nums1 and the first j numbers from nums2. Our answer would be dp[n1][n2], n1n1n1 is the length of nums1 and n2n2n2 is the length of nums2. The state transition would be as follows:

        If nums1[i - 1] == nums2[j - 1], perform dp[i][j] = 1 + dp[i - 1][j - 1].
        Otherwise, perform dp[i][j] = max(dp[i][j - 1], dp[i - 1][j].

Algorithm

    Create two integer variables n1 and n2. Intialize them to the size of nums1 and nums2.
    Create a 2D-array called dp having n1 + 1 rows and n2 + 1 columns where dp[i][j] contains the maximum number of lines we can draw by choosing the first i numbers from nums1 and the first j numbers from nums2. It is initialized to 0.
    We iterate using two loops. The outer loop iterates from i = 1 to i = n1 incrementing i by 1 after each iteration. We start an inner loop that iterates from j = 1 to j = n2 and perform the following:
        If the last number from both the arrays under consideration are equal, i.e., nums1[i - 1] == nums2[j - 1], we draw a line between two numbers and add it to the maximum number of lines that can be drawn ignoring the last number from both the arrays. We perform dp[i][j] = 1 + dp[i - 1][j - 1]. We already have the answer for dp[i - 1][j - 1] which was computed in the previous iteration of outer loop.
        Otherwise, if the last numbers do not match, we look for the maximum number of lines that can be drawn ignoring the last number from both the arrays. We pick the maximum of these two. We perform dp[i][j] = max(dp[i][j - 1], dp[i - 1][j]).
        It is important to realize that since we initialized dp with 0 and started iterations from i = 1 and j = 1, all dp states considering 0 elements from any of the arrays will be 0 which is as expected and forms the base case for the solution.
    Return dp[n1][n2].

Implementation
Complexity Analysis

Here, n1n1n1 is the length of nums1 and n2n2n2 is the length of nums2.

    Time complexity: O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2)
        Initializing the dp array takes O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) time.
        We fill the dp array which takes O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) time.

    Space complexity: O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2)
        The dp array consumes O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) space.

Approach 3: Dynamic Programming with Space Optimization
Intuition

The state transition, as we discussed in previous approaches, is:

        If nums1[i - 1] == nums2[j - 1], perform dp[i][j] = 1 + dp[i - 1][j - 1].
        Otherwise, perform dp[i][j] = max(dp[i][j - 1], dp[i - 1][j].

If we look closely at this transition, to fill dp[i][j] for a particular i and all possible values of j, we only need the values from the current and previous rows. To fill row i in the dp grid, we need the values from row i (dp[i][j - 1]) and previously computed value in the (i−1)th(i - 1)^{th}(i−1)th row (dp[i - 1][j - 1] and dp[i - 1][j]). Values in rows i - 2, i- 3, and so on are no longer needed.

Our task is complete if we can store the values of the previous iteration, i.e., for row i - 1 after each iteration of the outer loop.

We can solve this by using two 1D arrays of size n2, dp and dpPrev, where n2 is the size of nums2. We repeat the previous approach by running two loops. The outer loop runs from i = 1 to i = n1, and the inner loop runs from j = 1 to j = n2.

Now, on iteration i, dp[j] stores the maximum number of lines we can draw by choosing the first i numbers from nums1 and the first j numbers from nums2. It is similar to what dp[i][j] stored in the previous approach.

The other array dpPrev is important to understand. It helps us by remembering the previous state that we completed previously. On iteration i, dpPrev[j] stores the maximum number of lines we can draw by choosing the first i - 1 numbers from nums1 and the first j numbers from nums2. It is analogous to dp[i - 1][j] in the previous approach.

Because dpPrev stores the maximum number of lines we can draw by choosing the first i - 1 numbers from nums1 and dp stores the the maximum number of lines we can draw by choosing the first i numbers, we must copy the elements of dp to dpPrev after iterating over all the numbers in nums2 while considering the first i numbers from nums1 to prepare for the next iteration. After we copy dp to dpPrev, for the next iteration which considers the first i + 1 from nums1, dpPrev will hold values when we choose the first i numbers from nums1 which is exactly what we want.
Algorithm

    Create two integer variables n1 and n2. Intialize them to the size of nums1 and nums2.
    Create two arrays called dp and dpPrev of size n2 + 1.
    We iterate using two loops. The outer loop iterates from i = 1 to i = n1 incrementing i by 1 after each iteration. We start an inner loop that iterates from j = 1 to j = n2 and perform the following:
        If the last number from both the arrays under consideration are equal, i.e., nums1[i - 1] == nums2[j - 1], we draw a line between two numbers and add it to the maximum number of lines that can be drawn ignoring the last number from both the arrays. We perform dp[j] = 1 + dpPrev[j - 1].
        Otherwise, if the last numbers do not match, we look for the maximum number of lines that can be drawn ignoring the last number from both the arrays. We pick the maximum of these two. We perform dp[j] = max(dp[j - 1], dpPrev[j]).
        After the completion of inner loop, we copy dp to dpPrev.
    Return dp[n2] (or dpPrev[n2] as both are similar).

Implementation
Complexity Analysis

Here, n1n1n1 is the length of nums1 and n2n2n2 is the length of nums2.

    Time complexity: O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2)
        Initializing the dp and dpPrev arrays take O(n2)O(n2)O(n2) time.
        To get the answer, we use two loops that take O(n1⋅n2)O(n1 \cdot n2)O(n1⋅n2) time.

    Space complexity: O(n2)O(n2)O(n2)
        The dp and dpPrev arrays take O(n2)O(n2)O(n2) space each.

 */
class Solution {
    private int solve(int i, int j, int[] nums1, int[] nums2, int[][] memo) {
        if (i <= 0 || j <= 0) {
            return 0;
        }

        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        if (nums1[i - 1] == nums2[j - 1]) {
            memo[i][j] = 1 + solve(i - 1, j - 1, nums1, nums2, memo);
        } else {
            memo[i][j] =
                Math.max(solve(i, j - 1, nums1, nums2, memo), solve(i - 1, j, nums1, nums2, memo));
        }
        return memo[i][j];
    }

    public int maxUncrossedLines(int[] nums1, int[] nums2) {
        int n1 = nums1.length;
        int n2 = nums2.length;

        int[][] memo = new int[n1 + 1][n2 + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return solve(n1, n2, nums1, nums2, memo);
    }
}
