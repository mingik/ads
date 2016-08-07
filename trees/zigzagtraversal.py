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
        if not root:
            return []
        
        ret = []
        layers = [[root]]
        zig = True
        while len(layers) > 0:
            zig = not zig
            prev_layer = layers[0]
            ret.append([nod.val for nod in prev_layer if nod])
            next_layer = []
            for node in prev_layer:
                if node:
                    if zig:
                        if node.left:
                        next_layer.append(node.left)
                        if node.right:
                            next_layer.append(node.right)
                        else:
                            if node.right:
                                next_layer.append(node.right)
                            if node.left:
                                next_layer.append(node.left)
                
            if len(next_layer)>0:
                layers = [next_layer]
            else:
                layers = []
        return ret
