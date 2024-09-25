class Saving:
    def __init__(self, name, balance):
        self.name = name
        self.balance = balance

        print(f"Name: {self.name}     Balance: {self.balance}")

    # def get_acc_name(self):
    #     return self.name
    #
    # def get_account_balance(self):
    #     return self.balance

    def deposit(self, amt):
        self.balance += amt
        print(f"You have successfully deposited £{amt}")
        print(f"Your new balance is {self.balance}")

    def withdraw(self, amt):
        if self.balance <= 0 or amt >= self.balance:
            print(f"Sorry, not enough funds. Balance = {self.balance}")
        else:
            self.balance -= amt
            print(f"£{amt} - withdrawn, your new balance is £{self.balance}")
