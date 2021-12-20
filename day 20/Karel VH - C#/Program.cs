var input = File.ReadLines("input.txt");
List<int> algorithm = input.First().Select(x => x.Equals('#') ? 1 : 0).ToList();
List<List<int>> image = input.Skip(2).Select(x => x.Select(x => x.Equals('#') ? 1 : 0).ToList()).ToList();

List<List<int>> resultImage = new();
int def = 0;

//Run twice, change amount of steps
for (int steps = 0; steps < 50; steps++)
{
    resultImage = new();
    for (int i = -1; i < image.Count + 1; i++)
    {
        resultImage.Add(new());
        for (int j = -1; j < image[0].Count + 1; j++)
        {
            int res = algorithm[Convert.ToInt32(string.Join("", GetGrid(image, i, j, def)), 2)];

            resultImage[1 + i].Add(res);
        }
        resultImage.Count();
    }
    resultImage.ForEach(x => Console.WriteLine(string.Join("", x).Replace('1', '#').Replace('0', '.')));
    def = Math.Abs(def - 1);
    image = resultImage;
}

Console.WriteLine(image.SelectMany(r => r).Where(y => y.Equals(1)).Count());

List<int> GetGrid(List<List<int>> image, int X, int Y, int def)
    => new List<(int X, int Y)> { (-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 0), (0, 1), (1, -1), (1, 0), (1, 1) }
        .Select(n =>
        {
            if (n.X + X > -1 && n.Y + Y > -1 && n.X + X < image.Count && n.Y + Y < image[0].Count)
            {
                return image[X + n.X][Y + n.Y];
            }
            return def;
        }).ToList();