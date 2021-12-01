using System;
using System.Linq;

namespace Day1
{
    class Program
    {
        static void Main(string[] args)
        {
            int[] lines = System.IO.File.ReadAllLines(@"input.txt").Select(x => int.Parse(x)).ToArray();
            var numberOfTimesIncreased = 0;
            for (int i = 0; i < lines.Length - 1; i++) 
            {
                if (lines[i + 1] - lines[i] > 0) numberOfTimesIncreased++;
            }
            Console.WriteLine(numberOfTimesIncreased);
            numberOfTimesIncreased = 0;

            for (int i = 0; i < lines.Length - 3; i++)
            {
                if (lines[i + 3] - lines[i] > 0) numberOfTimesIncreased++;
            }
            Console.WriteLine(numberOfTimesIncreased);
        }
    }
}
