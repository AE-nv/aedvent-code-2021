using System;
using System.Linq;
using System.Collections.Generic;

namespace Day5
{
    class Program
    {
        static void Main(string[] args)
        {
            var lines = System.IO.File.ReadAllLines(@"input.txt").ToList();
            Console.WriteLine(lines.Select(x => x.Split(" -> ")).SelectMany(x =>  GetPoints(x)).GroupBy(x => x).Count(x => Enumerable.Count(x) >= 2));            

            List<Tuple<int, int>> GetPoints(string[] line)
            {
                var start = line.First().Split(",").Select(x => int.Parse(x));
                var end = line.Last().Split(",").Select(x => int.Parse(x));
                var pointList = new List<Tuple<int, int>>();              
                foreach (var i in Enumerable.Range(0, Math.Max(Math.Abs((int)(start.First() - end.First())), Math.Abs((int)(start.Last() - end.Last()))) + 1)) 
                {
                    pointList.Add(new Tuple<int, int>(
                         start.First() > end.First() ? end.First() + i : start.First() < end.First() ? end.First() - i : end.First(),
                         start.Last() > end.Last() ? end.Last() + i : start.Last() < end.Last() ? end.Last() - i : end.Last()));
                }               
                return pointList;
            }            
        }
    }
}
