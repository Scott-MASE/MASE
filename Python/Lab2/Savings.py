from Accounts import Account

class SavingsAccount(Account):
    def __init__(self, name, balance, intrest_rate):
        super().__init__(name,balance)
        self.__intrest_rate = intrest_rate

    def calculate_intrest(self):
        interest = super().get_account_balance() * (self.__intrest_rate / 100)
        print(f"Intrest earned: {interest}")
        super().deposit(interest)