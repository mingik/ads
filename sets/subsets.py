
# Given a set of distinct integers, nums, return all possible subsets.

# Note: The solution set must not contain duplicate subsets.

# For example,
# If nums = [1,2,3], a solution is:

# [
#   [3],
#   [1],
#   [2],
#   [1,2,3],
#   [1,3],
#   [2,3],
#   [1,2],
#   []
# ]

# Idea: each element either belongs to the subset or not. 
# So for current element, recurse on rest. Then output recursed result + current element added to each subset in recursed result.

class Solution(object):
    def subsets(self, nums):
        """
        :type nums: List[int]
        :rtype: List[List[int]]
        """
        if len(nums)>0:
            first = nums[0]
            rec = self.subsets(nums[1:])
            return [[first] + li for li in rec] + rec
        else:
            return [[]]
