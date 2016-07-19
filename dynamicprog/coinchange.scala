
/**
  * Counts number of ways you can break provided amount of money using provided number of coins.

  Usage: 
  
  $ scala coinchange.scala 3 1 2 3 // number of ways 3 can be broken using coins 1,2,3 is 3: 3 = 1+1+1
                                                                                             3 = 1+2
                                                                                             3 = 3
  */
object CoinChangeCalculator extends App {
  def moneyToCoins(money: Int, coins: List[Int]): Int = {
    def helper(money: Int, coins: List[Int]): Int = money match {
      case amount if (amount < 0) => 0
      case 0 if (coins.isEmpty) => 1
      case amount => if (coins.isEmpty) 0 else helper(amount - coins.head, coins) + helper(amount, coins.tail)
    }

    if (money == 0) 0 else helper(money, coins)
  }

  val money = args(0).toInt
  val coins = args.tail.toList.map(_.toInt)
  println(s"Number of different ways to break $money using coins of $coins is ${moneyToCoins(money, coins)}")
}

CoinChangeCalculator.main(args)
