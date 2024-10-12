import json
import tabulate
import collections


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
    header = data[0].keys()
    rows = [data[1].values()]
    # print(tabulate.tabulate(rows, header))

    genders = [entry['Gender'] for entry in data]
    gender_counts = collections.Counter(genders)

    # Print the result
    for gender, count in gender_counts.items():
        print(f"There are {count} {gender.lower()}s.")





def main():
    jsn = "heroes.json"
    readFromJSON(jsn)


main()
