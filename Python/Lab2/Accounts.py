class Account:
    def __init__(self, name, balance):
        self.__name = name
        self.__balance = balance
        print("Account Created")
        self.accountInfo()

    def accountInfo(self):
        print(f"name: {self.get_acc_name()}, balance: {self.get_account_balance()}")

    def get_acc_name(self):
        return self.__name

    def get_account_balance(self):
        return self.__balance

    def set_acc_balance(self, new_bal):
        self.__balance = new_bal

    def deposit(self, amt):
        self.__balance += amt
        print(f"You have successfully deposited £{amt}")
        print(f"Your new balance is {self.get_account_balance()}")

    def withdraw(self, amt):
        bal = self.get_account_balance()
        if bal <= 0 or amt >= bal:
            print(f"Sorry, not enough funds. Balance = {bal}")
        else:
            self.__balance -= amt
            print(f"£{amt} - withdrawn, your new balance is £{bal}")


