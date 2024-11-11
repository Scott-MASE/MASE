from tabulate import tabulate


class h:
    def __init__(self):
        return

    def tab(self, str):
        print(tabulate(str, tablefmt='pretty', headers='keys'))

    def get_attribute_name(self, attribute):
        for name, value in self.__dict__.items():
            if value is attribute:
                return name
        return None