﻿Console.WriteLine((File.ReadAllLines("input.txt").Select(x => (x.Split(" ")[0], (x.Split(" ")[0].Equals("up") ? -Convert.ToInt32(x.Split(" ")[1]) : Convert.ToInt32(x.Split(" ")[1])))).Aggregate((F: 0, D: 0), (a, n) => n.Item1.Equals("forward") ? (a.F + n.Item2, a.D) : (a.F, a.D + n.Item2)).ToString().Trim('(', ')').Split(",").Select(x => Convert.ToInt32(x)).Aggregate(0, (t, c) => t == 0 ? c : t *= c), (File.ReadAllLines("input.txt").Select(x => (x.Split(" ")[0], (x.Split(" ")[0].Equals("up") ? -Convert.ToInt32(x.Split(" ")[1]) : Convert.ToInt32(x.Split(" ")[1])))).Aggregate((F: 0, D: 0, Aim: 0), (a, n) => n.Item1.Equals("forward") ? (a.F + n.Item2, a.D + n.Item2 * a.Aim, a.Aim) : (a.F, a.D, a.Aim + n.Item2)).ToString().Trim('(', ')').Split(",").Select(x => Convert.ToInt32(x)).Take(2).Aggregate(0, (t, c) => t == 0 ? c : t *= c))));