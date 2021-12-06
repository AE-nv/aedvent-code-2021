using System;
using System.Linq;
using System.Collections.Generic;

namespace Day6
{
    class Program
    {
        static void Main(string[] args)
        {
            var input = System.IO.File.ReadAllLines(@"input.txt").First().Split(",").Select(x => int.Parse(x)).ToList();
            var day = 0;
            var dictInput = input.GroupBy(l => l).ToDictionary(grp => grp.Key, grp => (decimal)grp.Count());
            while (day < 256) {
                dictInput = dictInput.ToDictionary(grp => grp.Key - 1, grp => grp.Value);
                if (dictInput.ContainsKey(-1)) 
                {
                    if (dictInput.ContainsKey(6)) dictInput[6] += dictInput[-1];
                    else dictInput.Add(6, dictInput[-1]);
                    dictInput.Add(8, dictInput[-1]);
                    dictInput.Remove(-1);
                }               
                day++;
            }
            Console.WriteLine(dictInput.Sum(x => x.Value));
        }
    }
}
