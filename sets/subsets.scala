/**

Given a set of distinct integers, nums, return all possible subsets.

Note: The solution set must not contain duplicate subsets.

For example,
If nums = [1,2,3], a solution is:

[
  [3],
  [1],
  [2],
  [1,2,3],
  [1,3],
  [2,3],
  [1,2],
  []
]

Idea: each element either belongs to the subset or not. 
So for current element, recurse on rest. Then output recursed result + current element added to each subset in recursed result.

  */

def subsets(li: List[Int]): List[List[Int]] = {
  li match {
    case Nil => List(List())
    case a::rest => {
      val recurse = subsets(rest)
      recurse.map(li => a :: li) ++ recurse
    }
  }
}

//scala> subsets( List(1,2,3) )
//res8: List[List[Int]] = List(List(1, 2, 3), List(1, 2), List(1, 3), List(1), List(2, 3), List(2), List(3), List())
