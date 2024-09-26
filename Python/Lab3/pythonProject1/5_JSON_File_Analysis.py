import json
import tabulate


def readFromJSON(nme):
    with open(nme, 'r') as jsonFile:
        grades = json.load(jsonFile)

    print("\n Working with a list of dictionaries loaded from JSON file")
    printListOfDict(grades)

    # sum_ages = sum([float(row["Age"]) for row in grades])
    # sum_grades = sum([float(row["Grade"]) for row in grades])
    # count_students = len(grades)
    # average_grade = sum_grades / count_students
    # average_age = sum_ages / count_students
    #
    # print("\n Quick analysis")
    # print(f"Total num of students: {count_students}")
    # print(f"Average grade: {average_grade}")
    # print(f"Average age: {average_age}")




def printListOfDict(data):
    # header = data[0].keys()
    # rows = [data[1].values()]


    daSet = set(data[0].keys())
    arr = []
    for n in daSet:
        arr.append(n)
    print(arr)



    arr2 = []
    n = 1
    while n < len(data):
        for i in set(data[n].values()):
            arr2.append(i)

        n += 1

    num = 0
    daSet2 = set(arr2)

    arr3 = []
    for k in daSet2:
        for j in arr2:
            if k == j:
                num+=1
        if num > 1:
            arr3.append(j)
        num = 0
    print(arr3)




# print(tabulate.tabulate(rows, header))

    # arr2 = []
    # for row in rows:
    #     rowSet = set(row)
    #     num = 0
    #     arr = []
    #     for n in rowSet:
    #         for k in row:
    #             if k == n:
    #                 num += 1
    #         arr.append(num)
    #         num = 0
    #     arr2.append(arr)
    # rowSet = set()
    # for row in rows:
    #
    #
    # print(tabulate.tabulate(rowSet, header))





def main():
    jsn = "heroes.json"
    readFromJSON(jsn)


main()
