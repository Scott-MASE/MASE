import javax.persistence.*;

@Entity
public class Employee {
	@Id // For JPA
	@GeneratedValue
	private int id;

	private String firstName;
	private String lastName;
	private String jobRole;

	public Employee(){
		// required by JPA but not used by us
	}

	public Employee(String fn, String ln, String jr){
		this.firstName = fn;
		this.lastName = ln;
		this.jobRole = jr;
	}

	public String getFirstName(){
		return this.firstName;
	}
	public String getLastName(){
		return this.lastName;
	}
	public int getId(){
		return this.id;
	}

	public String getRole(){
		return this.jobRole;
	}
	public void setFirstName(String aName){
		this.firstName=aName;
	}
	public void setLastName(String aName){
		this.lastName=aName;
	}
	public void setId(int aId){
		this.id=aId;
	}
	public void setRole(String aRole){
		this.jobRole= aRole;
	}

}
