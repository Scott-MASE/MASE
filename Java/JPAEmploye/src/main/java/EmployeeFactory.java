import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class EmployeeFactory {
	/**
	 * @param args
	 */

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpatest2");
	EntityManager em = emf.createEntityManager();

	public int createEmployee(String aFirstName, String aLastName, String aJobDescription){
		Employee E = new Employee(aFirstName, aLastName, aJobDescription);
		EntityTransaction aTransaction = em.getTransaction();
		aTransaction.begin();
		em.persist(E); 
		em.getTransaction().commit();
		return E.getId();
	}

	public void deleteAllEmployees(){
		EntityTransaction aTransaction = em.getTransaction();
		aTransaction.begin();
		em.createQuery("DELETE FROM Employee").executeUpdate();
		em.getTransaction().commit();
	}


	public void deleteEmployee(int aID){
		Employee employee = em.find(Employee.class, aID);	
		em.getTransaction().begin();
		em.remove(employee);
		em.getTransaction().commit();
	}

	public ArrayList<Employee> getAllEmployeeDetails(){
		Query query = em.createQuery("SELECT e FROM Employee e");
		Collection<Employee> e =query.getResultList();
		return new ArrayList<Employee>(e);	
	}


	public void updateEmployeeRole(int aID,  String aRole){
		Employee employee = em.find(Employee.class, aID);
		em.getTransaction().begin();
		employee.setRole(aRole);
		em.getTransaction().commit();
	}

}

