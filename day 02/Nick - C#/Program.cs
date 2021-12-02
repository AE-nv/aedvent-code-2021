using System;
using System.Linq;
using System.Collections.Generic;

namespace Day2
{
    class Program
    {
        static void Main(string[] args)
        {
            var input = System.IO.File.ReadAllLines(@"input.txt").Select(x => x.Split(' '));
            var horizontalPosition = 0;
            var verticalPosition = 0;
            var aim = 0;
            foreach (var i in input)
            {
                switch (i[0]) 
                {
                    case "forward":
                        horizontalPosition += int.Parse(i[1]);
                        verticalPosition += aim * int.Parse(i[1]);
                        break;
                    case "down":
                        aim += int.Parse(i[1]);
                        break;
                    case "up":
                        aim -= int.Parse(i[1]);
                        break;
                   
                }

            }
            Console.WriteLine(horizontalPosition*verticalPosition);
        }
    }
}
