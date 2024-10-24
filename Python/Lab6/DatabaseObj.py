import pandas as pd
import matplotlib.pyplot as plt
from sqlalchemy import create_engine, inspect
from tabulate import tabulate

class DBConnection:
    def __init__(self, connectInfo):
        self.host, self.user, self.password, self.port, self.database = connectInfo
        self.table = ""
        self.mydb = None
        self.db_config = {
            'host' : self.host,
            'user': self.user,
            'password': self.password,
            'port': self.port,
            'database': self.database
        }
        self.ConnectNow(self)

    def ConnectNow(self):

    def preformEDA(self):

    def visualiseClient(self):

    def selectTable(self, tablenme):
        print("Establishing a connect to table; {0}".format(tablenme))
        self.table = tablenme
        self.preformEDA()

    def printDF(self, dataF):
        print(tabulate(dataF.head(),headers = 'keys', tablefmt='pretty', showindex=True))
        print(tabulate(dataF.tail(),headers='keys',tablefmt='pretty',showindex=True))
        print('\n')

    def disposeConnection(self):
        self.mydb.dispose()

    def ConnectNow(self):
        # Print the connection data to the user se they can see what was entered
        print("New Connection Using:"
              "\nHost: {0}"
              "\nUser: {1}"
              "\nPassword: {2]"
              "\nPort: {3}"
              "\nDatabase: (4}\n\n".format(self, host, self.user, self.password, self.port, self.database))
        # Establish a connection
        try:
        # Create a SQLAlchemy engine to connect to the database
            self.mydb = create_engine(
                f"mysql+mysqlconnector://{self.db_config['user']}:{self.db_configl['password']
                {self.d_config['host']):(self.db_config['port']}/{self.db_config['database']
        # Create an Inspector object to inspect the database
        inspector =
        inspect(self.mydb)
        # Get the list of table names in the database
        table_names = inspector.get_table_names)
        # Print the list of table names
        print("Tables in the database:")
        for table_name in table_names:
            print(table_name)
        except Exception as error:
        # Handle the exception if the connection fails or any other error occurs
        print("Error: Unable to connect to the MySQL database")
        print(f"Error Details: (error)")

