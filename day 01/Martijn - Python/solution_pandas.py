import pandas as pd

data = pd.read_csv(r'./input.txt', sep=',', header=None)
data.columns = ['depth']

diff_single = data.diff()

count = diff_single.loc[diff_single['depth'] > 0]
nb_increased = count.shape[0]
print(nb_increased)

# Calculate sum of window quick and dirty
data['depth_1'] = data['depth'].shift(-1)
data['depth_2'] = data['depth'].shift(-2)
data['depth_sum'] = data.sum(axis=1)

# Calculate sum of window shorter and more elegant
data['depth_sum_2'] = data['depth'].rolling(3).sum().shift(-2)

diff_sum = data.diff()
count_sum = diff_sum.loc[diff_sum['depth_sum_2'] > 0]
nb_increased_sum = count_sum.shape[0]
print(nb_increased_sum)

