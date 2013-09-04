package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class Change extends Model {

    @Id
    public Long id;
    public Date initiated;
    public String summary;
    public String description;
	
    public boolean iaoApproved;
    public boolean testApproved;

	@ManyToOne
    public ICTSystem system;
	
    @ManyToOne
    public User initiator;

    @ManyToOne
    public User builder;

//    @OneToMany(cascade = CascadeType.PERSIST)
//    public List<Outage> outages = new ArrayList<Outage>();

	/**
    public Change(String summary, String description, User initiator, User builder, ICTSystem system) {
        this.summary = summary;
        this.description = description;
        this.initiator = initiator;
        this.builder = builder;
        this.system = system;
    }
	*/
	
	public Change(String id, String summary, String description, User initiator, String system) {
		this.id = stringLongConverter(id);
		this.summary = summary;
        this.description = description;
        this.initiator = initiator;
        this.system = ICTSystem.find.where().eq("name", system).findUnique();
		System.out.println("Using Z");
	}
	
    public static Model.Finder<Long,Change> find = new Model.Finder(Long.class, Change.class);
	

	public static Change create(String id, String summary, String description, User initiator, String system) {		
		//debug
		System.out.println("Using C");
		System.out.println(id);
		System.out.println(summary);
		System.out.println(description);
		System.out.println(initiator);
		System.out.println(system);
		
		Change change = new Change(id, summary, description, initiator, system);		
		change.save();
		return change;
	}

    public String getSummary(){
        return this.summary;
    }
	
	
	/**
	* 	Checks the user is the initiator of the change.
	*/
	public static boolean isInitiator(Long changeRef, String user) {
		return find.where()
			.eq("initiator.userid", user)
			.eq("id", changeRef)
			.findRowCount() > 0;
	}
	
	/**
	*	Changes the summary of the change to the value of argument newSummary.
	*/
	public static String changeSummary(Long changeRef, String newSummary) {
		Change change= find.ref(changeRef);
		change.summary = newSummary;
		change.update();
		return newSummary;
	}
	
	
	/**
	*	Helper method to convert String to Long. (dirty)
	*/
	private static Long stringLongConverter(String value) {
		long returnMe;
		returnMe = 0;
		try 
		{
			returnMe = Long.parseLong(value);
		} catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException: " + nfe.getMessage());
		}
		return returnMe;
	}
	
	public String toString() {
		return this.summary + " " + this.description;
	}
	
	/**Old code not needed now
		public Change(String id, String summary, String description, User initiator, ICTSystem system) {
		this.id = stringLongConverter(id);
		this.summary = summary;
        this.description = description;
        this.initiator = initiator;
        this.system = system;
		System.out.println("Using X");
	}
	//temporary overload
	public Change(Long id, String summary, String description, User initiator, ICTSystem system) {
		this.id = id;
		this.summary = summary;
        this.description = description;
        this.initiator = initiator;
        this.system = system;
		System.out.println("Using Y");
	}
	//temporary overload
		/**
	*	Create a new change request
	*
    public static Change create (String summary, String description, String initiator, String builder, Long system) {
        Change change = new Change(summary, description, User.find.ref(initiator),User.find.ref(builder),ICTSystem.find.ref(system));
        change.save();
        return change;
    }	
	public static Change create(String summary, String description, String initiator, String system) {		
		
		Change change = new Change(summary, description, User.find.ref(initiator),
				ICTSystem.find.ref(stringLongConverter(system)));
		System.out.println("Using A");
		change.save();
		return change;
	}
	//temporary overload
	public static Change create(Long id, String summary, String description, User initiator, ICTSystem system) {		
		//debug
		System.out.println("Using B");
		System.out.println(id);
		System.out.println(summary);
		System.out.println(description);
		System.out.println(initiator);
		System.out.println(system);
		
		Change change = new Change(id, summary, description, initiator, system);
		
		change.save();
		return change;
	}
	//temporary overload
	
    public static List<Project> findInvolving(String user) {
        return find.where().eq("members.email", user).findList();
    } .find.ref(stringLongConverter(system))
	
	*/
}
