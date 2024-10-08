def Threemain():
    listFileName = "ListData.txt"
    myListOut = [1, 2, 3, "a", "b", "c", True, False]
    writeToTxtFile(myListOut, listFileName)

    myListIn = readFromTxtFile(listFileName)
    for item in myListIn:
        print(item)


def writeToTxtFile(data, nme):
    data = ",".join(map(str, data))
    with open(nme, 'w') as f:
        for item in data:
            f.write("{0}".format(item))


def readFromTxtFile(nme):
    # Read the data from the text file
    with open(nme, 'r') as file:
        data = file.read()
        # Split the data into individual items using commas
        my_list = data.split(',')
    # Convert each item to its appropriate data type (e.g., int, float, etc.)
    my_list = [item.strip() for item in my_list]
    return my_list

def updateDataTypes(data):
    # Convert each item to its appropriate data type (e.g., int, float, etc.)
    updated_list = []
    for item in data:
        item = item.strip() # Remove leading/trailing whitespaces
        try:
            # Try to convert to int first
            converted_item = int(item)
        except ValueError:
            try:
                # If not an int, try to convert to float
                converted_item = float(item)
            except ValueError:
                # If not a float either, keep it as a string
                converted_item = item
        updated_list.append(converted_item)
    return updated_list



