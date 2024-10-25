
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class Test {

	public static void main(String[] args) {
		EmployeeFactory aEmployeefactory =new EmployeeFactory();

		//Create and persist an Employee
		int aId = aEmployeefactory.createEmployee("Tom", "Kelly", "Sales");
		System.out.println("---Employee Tom created with ID:"+aId+"----");
		int aId1 = aEmployeefactory.createEmployee("John", "Fox", "Support");
		System.out.println("---Employee John created with ID:"+aId1+"----");
		int aId2 = aEmployeefactory.createEmployee("Mary", "Murphy", "Sales");
		System.out.println("---Employee Mary created with ID:"+aId2+"----");


		//List the details of all stored Employees
		System.out.println("\n----All Employee Details (Read From Objects)----");
		Collection<Employee> allEmployeesColl = aEmployeefactory.getAllEmployeeDetails(); 
		ArrayList<Employee> allEmployees = new ArrayList<Employee>(allEmployeesColl);
		
		for(int i=0;i<allEmployees.size();i++){
			Employee e = (Employee)allEmployees.get(i);
			System.out.println("Employee Id: "+e.getId()+ " Employee Name:"+e.getFirstName()+" Employee Role:"+e.getRole());
		}


		//Delete A Specific Employee
		System.out.println("\n----Delete Toms Employee Details----");
		aEmployeefactory.deleteEmployee(aId);

		//List the details of all stored Employees
		System.out.println("\n----All Employee Details (Read From Objects)----");
		allEmployees = aEmployeefactory.getAllEmployeeDetails(); 
		for(int i=0;i<allEmployees.size();i++){
			Employee e = (Employee)allEmployees.get(i);
			System.out.println("Employee Id: "+e.getId()+ " Employee Name:"+e.getFirstName()+" Employee Role:"+e.getRole());
		}

		//Update Marys Role
		System.out.println("\n----Update Marys Role----");
		aEmployeefactory.updateEmployeeRole(aId2, "Manager");

		//List the details of all stored Employees
		System.out.println("\n----All Employee Details (Read From Objects)----");
		allEmployees = aEmployeefactory.getAllEmployeeDetails(); 
		for(int i=0;i<allEmployees.size();i++){
			Employee e = (Employee)allEmployees.get(i);
			System.out.println("Employee Id: "+e.getId()+ " Employee Name:"+e.getFirstName()+" Employee Role:"+e.getRole());
		}

		//Delete All Employees
		System.out.println("\n----Delete all Employee Details----");
    	aEmployeefactory.deleteAllEmployees();

    	//List the details of all stored Employees
    	System.out.println("\n----All Employee Details (Read From Objects)----");
	    allEmployees = aEmployeefactory.getAllEmployeeDetails(); 
	    for(int i=0;i<allEmployees.size();i++){
	    	Employee e = (Employee)allEmployees.get(i);
	    	System.out.println("Employee Id: "+e.getId()+ " Employee Name:"+e.getFirstName()+" Employee Role:"+e.getRole());
	    }   
		
		int aId4 = aEmployeefactory.createEmployee("Kate", "Fallon", "Sales");
		System.out.println("---Employee Kate created with ID:"+aId4);
	
	}
}

