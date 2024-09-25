import random as rand
import matplotlib.pyplot as plt
import numpy as np

list_size = 30

def generate_random_list(list_size):
    arr = []
    n = 0
    while n < list_size:
        arr.append(rand.randint(1,10))
        n += 1
    return arr

def count_distinct(list,set):
    print(f"{len(set)} distinct numbers out of {len(list)} numbers in list")


rand_list = generate_random_list(20)

print(rand_list)

list_set = set(generate_random_list(20))

print(list_set)

count_distinct(rand_list,list_set)

def barc():
    arr = []
    num = 0
    for n in list_set:
        for k in rand_list:
            if k == n:
                num += 1
        arr.append(num)
        num = 0
    return arr


x = list(map(lambda x: x, list_set))
y = barc()



plt.bar(x,y)
plt.xticks(np.arange(0,max(x)+1,step=1))
plt.yticks(np.arange(0,max(y)+1,step=1))
plt.xlabel("Number")
plt.ylabel("Counts")
plt.show()



