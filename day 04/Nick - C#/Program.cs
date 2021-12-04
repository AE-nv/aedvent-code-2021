using System;
using System.Linq;
using System.Collections.Generic;

namespace Day4
{
    class Program
    {
        static void Main(string[] args)
        {
            //READ INPUT
            var lines = System.IO.File.ReadAllLines(@"input.txt").ToList();
            var inputNumbers = lines[0].Split(",").Select(x => int.Parse(x)).ToList();
            lines.RemoveAt(0);
            var boards = new List<Board>();
            while (lines.Any()) {
                var boardLines = new List<List<int>>();
                lines.RemoveAt(0);
                var currentLine = lines.First();
                while (!string.IsNullOrWhiteSpace(currentLine)) 
                {
                    boardLines.Add(lines.First().Split(" ").Where(x => !string.IsNullOrEmpty(x)).Select(x => int.Parse(x)).ToList());
                    lines.RemoveAt(0);
                    if (!lines.Any()) break;
                    currentLine = lines.First();
                }
                boards.Add(new Board(boardLines));
            }
            //PLAY BINGO
            //Console.WriteLine(PlayBingoPart1(inputNumbers, boards));
            Console.WriteLine(PlayBingoPart2(inputNumbers, boards));
        }
        private static int PlayBingoPart1(IList<int> input, IList<Board> boards)
        {
            foreach (var number in input)
            {
                foreach (var board in boards)
                {
                    board.Mark(number);
                    if (board.IsBingo()) return board.UnMarkedSum * number;
                }
            }
            return 0;

        }

        private static int PlayBingoPart2(IList<int> input, IList<Board> boards)
        {
            foreach (var number in input)
            {
                foreach (var board in boards)
                {
                    board.Mark(number); 
                    if(boards.Count() == 1 && board.IsBingo()) return board.UnMarkedSum * number;
                }
                boards = boards.Where(x => !x.IsBingo()).ToList();
            }
            return 0;

        }
    }

    class Board {
        public Dictionary<int, Tuple<int, int>> Values;
        public int[] NumberOfItemsMarkedInRows;
        public int[] NumberOfItemsMarkedInColumns;
        public int UnMarkedSum;
        public Board(List<List<int>> lines)
        {
            NumberOfItemsMarkedInRows = new int[lines.Count];
            NumberOfItemsMarkedInColumns = new int[lines[0].Count];
            Values = new Dictionary<int, Tuple<int, int>>();
            UnMarkedSum = 0;
            for (int i = 0; i < lines.Count; i++) 
            {
                for (int j = 0; j < lines[0].Count; j++)
                {
                    Values.Add(lines[i][j], new Tuple<int, int>(i, j));
                    UnMarkedSum += lines[i][j];
                }
            }
        }

        public void Mark(int n) 
        {
            if(!Values.TryGetValue(n, out var position)) return;
            NumberOfItemsMarkedInRows[position.Item1]++;
            NumberOfItemsMarkedInColumns[position.Item2]++;
            UnMarkedSum -= n;
        }

        public bool IsBingo() {
            return NumberOfItemsMarkedInRows.Any(x => x == NumberOfItemsMarkedInColumns.Count()) || NumberOfItemsMarkedInColumns.Any(x => x == NumberOfItemsMarkedInRows.Count());
        }

    }
}
