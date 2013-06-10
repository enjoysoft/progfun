package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) {
        case (block, move) => move match {
          case Left => block.left
          case Right => block.right
          case Up => block.up
          case Down => block.down
        }
      }
  }

  trait Level1 extends SolutionChecker {
    /* terrain for level 1*/

    val level =
      """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }

  test("simple moves") {
    new Level1 {
      assert(startBlock.right.b1 == Pos(1, 2))
      assert(startBlock.right.b2 == Pos(1, 3))
    }
  }

  test("Neighbors With History") {
    new Level1 {
      val moves = neighborsWithHistory(startBlock, List(Up)).toList

      assert(moves.contains((Block(Pos(2, 1), Pos(3, 1)), List(Down, Up))))
      assert(moves.contains((Block(Pos(1, 2), Pos(1, 3)), List(Right, Up))))
      assert(!moves.exists(m => m._2 == List(Up, Up) || m._2 == List(Left, Up)))
    }
  }

  test("No backward movement") {
    new Level1 {
      val neighbors = neighborsWithHistory(startBlock.right, List(Right))
      assert(neighbors.exists(_._1 == startBlock))

      val moves = newNeighborsOnly(neighbors, Set(startBlock))
      assert(!moves.exists(_._1 == startBlock))
    }
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0, 0)), "0,0")
      assert(!terrain(Pos(4, 11)), "4,11")
      assert(!terrain(Pos(3, 0)), "3,0")
      assert(!terrain(Pos(0, 3)), "0,3")
      assert(terrain(Pos(3, 9)), "3,9")
      assert(!terrain(Pos(5, 9)), "5,9")
    }
  }

  test("sanity check of solve level 1") {
    new Level1 {
      val solved = solve(optsolution)
      assert(solved.b1 == solved.b2, "not same place")
      assert(solved.isStanding, "not standing")
      assert(solved.b2 == goal, "not on goal")
      assert(done(solved), "its not done")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1, 1), "start not parsed correctly")
      assert(goal == Pos(4, 7), "goal not parsed correctly")
    }
  }

  test("sanity check for level 1") {
    new Level1 {
      assert(startPos == Pos(1, 1))
      assert(!done(startBlock))
    }
  }

  test("paths from start") {
    new Level1 {
      val step0 = pathsFromStart
      val step1 = pathsFromStart.drop(1).take(2)

      assert(step1.contains((Block(Pos(2, 1), Pos(3, 1)), List(Down))))
      assert(step1.contains((Block(Pos(1, 2), Pos(1, 3)), List(Right))))

      val step2 = step0.drop(3).take(3)

      println(step2)

      assert(step2.contains((Block(Pos(2, 2), Pos(3, 2)), List(Right, Down))))
      assert(step2.contains((Block(Pos(2, 2), Pos(2, 3)), List(Down, Right))))
      assert(step2.contains((Block(Pos(1, 4), Pos(1, 4)), List(Right, Right))))
    }
  }

  test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }
}