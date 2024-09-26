from Accounts import Account

class CheckingAccount(Account):
    def __init__(self, name, balance, overdraft_limit):
        super().__init__(name,balance)
        self.__overdraft_limit = overdraft_limit

    def withdraw(self, amt):
        if amt <= super().get_account_balance()+ self.__overdraft_limit:
            bal = super().get_account_balance() -amt
            super().set_acc_balance(bal)
        else:
            print(f"Exceded overdraft. overdraft limit is {self.__overdraft_limit}")