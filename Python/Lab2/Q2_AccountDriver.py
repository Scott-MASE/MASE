from Accounts import Account
from Savings import SavingsAccount
from Checking_Obj import CheckingAccount

def main():
    ac1 = Account("Harry Potter", 1000)
    ac1.deposit(500)
    ac1.withdraw(200)
    print("Account check__")
    ac1.accountInfo()

    print("\n")
    ac2 = SavingsAccount("Hermione Granger", 500, 2.5)
    ac2.deposit(200)
    ac2.calculate_intrest()
    ac2.accountInfo()

    print("\n")
    ac3 = CheckingAccount("Ron Weasley", 100, 50)
    ac3.deposit(300)
    ac3.withdraw(425)
    ac3.withdraw(70)
    ac3.accountInfo()

main()
