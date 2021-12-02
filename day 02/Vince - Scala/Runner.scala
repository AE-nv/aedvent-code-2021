package org.vro.y2021.day02

import org.scalatest.Assertions
import org.scalatest.matchers.should.Matchers
import resource.managed

import scala.io.Source

object Runner extends Assertions with Matchers {

  case class Move(direction: String, delta: Int)

  def part_1(moves: List[Move], x: Int = 0, depth: Int = 0): Int = {
    moves match {
      case m :: ms =>
        m.direction match {
          case "up" => part_1(ms, x, depth - m.delta)
          case "down" => part_1(ms, x, depth + m.delta)
          case "forward" => part_1(ms, x + m.delta, depth)
        }
      case _ => x * depth
    }
  }

  def part_2(moves: List[Move], x: Int = 0, depth: Int = 0, aim: Int = 0): Int = {
    moves match {
      case m :: ms =>
        m.direction match {
          case "up" => part_2(ms, x, depth, aim - m.delta)
          case "down" => part_2(ms, x, depth, aim + m.delta)
          case "forward" => part_2(ms, x + m.delta, depth + (aim * m.delta), aim)
        }
      case _ => x * depth
    }
  }

  def getInput(fileName: String): List[Move] = {
    managed(Source.fromFile(fileName))
      .map(r => r.getLines().toList)
      .opt.getOrElse(List())
      .map(x => x.split(" "))
      .map(s => Move(s.head, s(1).toInt))
  }

  def main(args: Array[String]) {
    test()
    println(part_1(getInput("input.txt")))
    println(part_2(getInput("input.txt")))
  }

  def test(): Unit = {
    part_1(getInput("example01.txt")) shouldBe 150
    part_2(getInput("example01.txt")) shouldBe 900
  }
}