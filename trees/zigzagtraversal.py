"""
Binary Tree Zigzag Level Order Traversal
Difficulty: Medium

Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).

For example:
Given binary tree [3,9,20,null,null,15,7],
    3
   / \
  9  20
    /  \
   15   7
return its zigzag level order traversal as:
[
  [3],
  [20,9],
  [15,7]
]

"""

# Definition for a binary tree node.
# class TreeNode(object):
#     def __init__(self, x):
#         self.val = x
#         self.left = None
#         self.right = None

class Solution(object):
    def zigzagLevelOrder(self, root):
        """
        :type root: TreeNode
        :rtype: List[List[int]]
        """
        if root:
            layers = [[root]]
            ret = []
            # do BFS
            while len(layers)>0:
                layer = [nod.val for nod in layers[0] if nod]
                ret.append(layer)
                next_layer = []
                for node in layers[0]:
                    if node:
                        if node.left:
                            next_layer.append(node.left)
                        if node.right:
                            next_layer.append(node.right)
                if len(next_layer)>0:
                    layers = [next_layer]
                else:
                    layers = []

            # add zigzag
            for i in range(len(ret)):
                if i % 2 == 1:
                    ret[i].reverse()
            return ret
        else:
            return []            
