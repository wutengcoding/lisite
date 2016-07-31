package com.bruce.leetcode;

import java.util.Arrays;

/**
 * Created by wuteng1 on 2016/7/7.
 */
public class CoinChange322 {
    public static void main(String[] args) {

    }
    static class Solution{
        public int coinChange(int[] coins, int amount){
            int[] dp = new int[amount+1];
            Arrays.fill(dp, amount+1);
            dp[0] = 0;
            //dp[i] represents the minimal count of coins to make up i
            for(int i = 1; i <= amount; i++){
                for(int j = 0; j < coins.length; j++){
                    if(i >= coins[j]){
                        dp[i] = Math.min(dp[i], dp[i-coins[j]] + 1);
                    }
                }
            }
            return dp[amount] == amount+1 ? -1: dp[amount];
        }
    }
}
