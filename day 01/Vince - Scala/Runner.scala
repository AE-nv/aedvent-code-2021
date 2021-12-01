package org.vro.y2021.day01

import org.scalatest.Assertions
import org.scalatest.matchers.should.Matchers
import resource.managed

import scala.io.Source

object Runner extends Assertions with Matchers {

  def part_1(input: List[Int]): Int = {
    input.zip(input.tail).count(x => x._1 < x._2)
  }

  def part_2(input: List[Int]): Int = {
    part_1(input.sliding(3).map(l => l.sum).toList)
  }

  def getInput(fileName: String): List[Int] = {
    managed(Source.fromFile(fileName)).map(r => r.getLines().toList.map(x => x.toInt)).opt.getOrElse(List())
  }

  def main(args: Array[String]) {
    test()
    println(part_1(getInput("input.txt")))
    println(part_2(getInput("input.txt")))
  }

  def test(): Unit = {
    part_1(getInput("example01.txt")) shouldBe 7
    part_2(getInput("example01.txt")) shouldBe 5
  }
}