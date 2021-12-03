using System;
using System.Linq;
using System.Collections.Generic;

namespace Day3
{
    class Program
    {
        static void Main(string[] args)
        {
            string[] lines = System.IO.File.ReadAllLines(@"input.txt");
            var charArrays = lines.Select(x => x.ToCharArray().ToList()).ToList();
            var intList = charArrays.Select(x => x.Select(c => int.Parse(c.ToString())).ToList()).ToList();
            var transposed = GetTransposed(intList);

            var gamma = Convert.ToInt32(new string(transposed.Select(x => x.Sum() > (x.Count / 2) ? '1' : '0').ToArray()), 2);
            var epsilon = Convert.ToInt32(new string(transposed.Select(x => x.Sum() > (x.Count / 2) ? '0' : '1').ToArray()), 2);

            //PART 1
            Console.WriteLine(gamma * epsilon);

            //PART 2
            Console.WriteLine(GetValue(intList, 0, 0, 1) * GetValue(intList, 0, 1, 0));
            int GetValue(List<List<int>> input, int i, int a, int b)
            {
                if (input.Count == 1) return Convert.ToInt32(new string(input.First().Select(x => x.ToString().ToCharArray()[0]).ToArray()), 2);
                var transposed = Enumerable.Range(0, input[0].Count).Select(i => input.Select(lst => lst[i]).ToList()).ToList();
                var gamma = transposed.Select(x => x.Sum() >= (x.Count / 2.0) ? a : b).ToList();
                return GetValue(input.Where(x => x[i] == gamma[i]).ToList(), i + 1, a, b);
            }

            List<List<int>> GetTransposed(List<List<int>> input) => Enumerable.Range(0, input[0].Count).Select(i => input.Select(lst => int.Parse(lst[i].ToString())).ToList()).ToList();
            
        }
    }

}
