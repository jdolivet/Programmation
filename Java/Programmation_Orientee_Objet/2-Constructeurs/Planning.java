import java.util.ArrayList;

class Time
/* Représente le jour et l'heure d'un évènement.
 * Les heures sont représentées en double depuis minuit.
 * Par exemple 14:30 est représenté 14.5.
 */
{
    private final String day_;
    private final double hour_;

    // Constructeur à partir du jour et de l'heure
    public Time(String jour, double heure) {
        day_ = jour;
        hour_ = heure;
    }

    // Affichage
    public void print() {
        System.out.print(day_ + " à ");
        Time.printTime(hour_);
    }

    // Pour connaître le jour
    public String day() {
        return day_;
    }

    // Pour connaître l'heure
    public double hour()  {
        return hour_;
    }

    // Affiche un double en format heures:minutes
    public static void printTime(double hour) {
        int hh = (int)hour;
        int mm = (int)(60. * (hour - hh));
        System.out.format("%02d:%02d", hh, mm);
    }
}


/*******************************************
 * Complétez le programme à partir d'ici.
 *******************************************/
class Activity{
	private final String lieu;
	private final Time horaire;
	private final double duree;

	
	public Activity(String l, Time t, double d)
	{
		this.lieu=l;
		this.horaire=t;
		this.duree=d;
	}
	public Activity(String l, String day, double hour, double d)
	{
		this.lieu=l;
		this.horaire=new Time(day,hour);
		this.duree=d;
	}
	public String getLocation()
	{
		return this.lieu;
	}
	public Time getTime()
	{
		return this.horaire;
	}
	public double getDuration()
	{
		return this.duree;
	}
	public boolean conflicts(Activity a)
	{
		Time finCoursCurrent=new Time(this.horaire.day(), this.horaire.hour()+this.duree);
		Time finCoursa=new Time(a.horaire.day(), a.horaire.hour()+a.duree);
//		System.out.println(finCoursCurrent.hour());
//		System.out.println(a.horaire.hour());
//		System.out.println(finCoursa.hour());
//		System.out.println(this.horaire.hour());
		return (this.horaire.day()==a.horaire.day()) 
				&& 
					(((this.horaire.hour()<=a.horaire.hour()) &&(finCoursCurrent.hour()>a.horaire.hour()))
					||
					((a.horaire.hour()<=this.horaire.hour()) &&(finCoursa.hour()>this.horaire.hour())))
					;

	}
	public void print()
	{
		System.out.print("le ");
		this.horaire.print();
		System.out.print(" en "+this.lieu+", durée ");
        int hh = (int)this.duree;
        int mm = (int)(60. * (this.duree - hh));
        System.out.format("%02d:%02d", hh, mm);
	}  
}

class Course{
	private final String id;
	private final String name;
	private final Activity cathedra;
	private final Activity hebdo;
	private final int credits;
	
	public Course(String i, String n, Activity c, Activity h, int cred)
	{
		this.id=i;
		this.name=n;
		this.cathedra=c;
		this.hebdo=h;
		this.credits=cred;
		System.out.println("Nouveau cours : "+i);
	}
	public Course(Course c)
	{
		this.id=c.id;
		this.name=c.name;
		this.cathedra=c.cathedra;
		this.hebdo=c.hebdo;
		this.credits=c.credits;
		System.out.println("Nouveau cours : "+c.id);
	}
	
	public String getId()
	{
		return this.id;
	}
	public String getTitle()
	{
		return this.name;
	}
	public int getCredits()
	{
		return this.credits;
	}
	
	public double workload()
	{
		return (this.cathedra.getDuration()+this.hebdo.getDuration());
	}
	
	public boolean conflicts(Activity a)
	{
		return (this.cathedra.conflicts(a)==true || this.hebdo.conflicts(a)==true);
	}
	
	public boolean conflicts(Course c)
	{
		return (c.conflicts(this.cathedra)==true || c.conflicts(this.hebdo)==true);	
	}
	public void print()
	{
		System.out.print(this.id+": "+this.name+" - cours ");
		this.cathedra.print();
		System.out.print(", exercices ");
		this.hebdo.print();
		System.out.print(". crédits : "+this.credits);
	}
}

class StudyPlan{
	private ArrayList<Course> listCourses;
	
	public StudyPlan()
	{
		listCourses=new ArrayList<Course>();
	}
	
	
	public StudyPlan(StudyPlan s)
	{
		listCourses=new ArrayList<Course>();
		for(int i=0;i< s.size();i++)
		{
			listCourses.add(s.get(i));
		}
	}
	
	public int size()
	{
		return this.listCourses.size();
	}
	
	public Course get(int i)
	{
		return this.listCourses.get(i);
	}
	
	private Course trouve(String idCours)
	{
		for(int j=0;j<this.size();j++)
		{
			if (this.get(j).getId().equals(idCours))
			{
				return this.get(j);
			}			
		}
		return null;
	}
	
	public void addCourse(Course c)
	{
		if(trouve(c.getId())==null)
		{
			listCourses.add(c);			
		}
	}
	
	public boolean conflicts(String idCours, ArrayList<String> selCours)
	{
		if(trouve(idCours)==null)
		{
			return false;
		}
		else{
			for(int j=0;j<selCours.size();j++)
			{
				if (trouve(selCours.get(j))!=null)
				{
					if (trouve(idCours).conflicts(trouve(selCours.get(j))))
					{
						return true;
					}
				}
			}	
			return false;
		}

	}
	
	
	public void print(String idCours)
	{
		if(trouve(idCours)!=null)
		{
			trouve(idCours).print();
		}
	}	
	
	public int credits(String idCours)
	{
		if(trouve(idCours)!=null)
		{
			return trouve(idCours).getCredits();
		}
		else{return 0;}
	}
	
	public double workload(String id)
	{
		if(trouve(id)!=null)
		{
			return trouve(id).workload();
		}
		else{return 0;}		
	}
	
	public void printCourseSuggestions(ArrayList<String> selCours)
	{
		boolean pasPossible=true;
		for(int j=0;j<this.listCourses.size();j++)
		{
				if (!(conflicts(this.listCourses.get(j).getId(),selCours)))
				{
					pasPossible=false;
					this.listCourses.get(j).print();
					System.out.println();					
				}			
		}
		if(pasPossible==true)
		{
			System.out.println("Aucun cours n'est compatible avec la sélection de cours.");
		}
	}
}

class Schedule{
	private ArrayList<String> listId;
	private StudyPlan plan;
	
	public Schedule(StudyPlan s)
	{
		listId=new ArrayList<String>();
		this.plan=new StudyPlan();
		for(int i=0;i< s.size();i++)
		{
			this.plan.addCourse(s.get(i));
		}
	}
	

	
	public Schedule(Schedule s)
	{
		this.listId=new ArrayList<String>();
		for(int i=0;i< s.listId.size();i++)
		{
			this.listId.add(s.listId.get(i));
		}
		this.plan=new StudyPlan();
		for(int i=0;i< s.plan.size();i++)
		{
			this.plan.addCourse(s.plan.get(i));
		}
	}
	
	private Course trouve(String idCours)
	{
		for(int j=0;j<this.plan.size();j++)
		{
			if (this.plan.get(j).getId()==idCours)
			{
				return this.plan.get(j);
			}			
		}
		return null;
	}
	
	public boolean addCourse(String id)
	{
		if (trouve(id)!=null)
		{
			if(this.plan.conflicts(id,this.listId)==true)
			{
				return false;
			}
			else
			{
				this.listId.add(id);
				return true;
			}			
		}
		return false;
	}
	
	public double computeDailyWorkload()
	{
		double temps=0;
		for(int i=0;i<this.listId.size();i++)
		{
			temps=temps+this.plan.workload(listId.get(i));
		}
		return temps/5.0;
	}
	
	public int computeTotalCredits()
	{
		int credits=0;
		for(int i=0;i<this.listId.size();i++)
		{
			credits=credits+this.plan.credits(listId.get(i));
		}
		return credits;
	}
	
	public void print()
	{
		for(int i=0;i<this.listId.size();i++)
		{
			this.plan.print(listId.get(i));
			System.out.println();
		}
		System.out.println("Total de crédits   : "+this.computeTotalCredits());
		System.out.print("Charge journalière : ");
		int hh = (int)this.computeDailyWorkload();
        int mm = (int)(60. * (this.computeDailyWorkload() - hh));
        System.out.format("%02d:%02d", hh, mm);
        System.out.println();
		System.out.println("Suggestions :");
		this.plan.printCourseSuggestions(listId);
		System.out.println();
	}
	
}
/*******************************************
 * Ne rien modifier après cette ligne.
 *******************************************/
class Planning {
    public static void main(String[] args) {
        // Quelques activités
        Activity physicsLecture  = new Activity("Central Hall", "lundi",  9.25, 1.75);
        Activity physicsExercises = new Activity("Central 101" , "lundi", 14.00, 2.00);

        Activity historyLecture  = new Activity("North Hall", "lundi", 10.25, 1.75);
        Activity historyExercises = new Activity("East 201"  , "mardi",  9.00, 2.00);

        Activity financeLecture  = new Activity("South Hall",  "vendredi", 14.00, 2.00);
        Activity financeExercises = new Activity("Central 105", "vendredi", 16.00, 1.00);

        // On affiche quelques informations sur ces activités
        System.out.print("L'activité physicsLecture a lieu ");
        physicsLecture.print();
        System.out.println(".");

        System.out.print("L'activité historyLecture a lieu ");
        historyLecture.print();
        System.out.println(".");

        if (physicsLecture.conflicts(historyLecture)) {
            System.out.println("physicsLecture est en conflit avec historyLecture.");
        } else {
            System.out.println("physicsLecture n'est pas en conflit avec historyLecture.");
        }
        System.out.println();

        // Création d'un plan d'étude
        StudyPlan studyPlan = new StudyPlan();
        Course physics = new Course("PHY-101", "Physique", physicsLecture, physicsExercises, 4);
        studyPlan.addCourse(physics);
        Course history = new Course("HIS-101", "Histoire", historyLecture, historyExercises, 4);
        studyPlan.addCourse(history);
        Course finance = new Course("ECN-214", "Finance" , financeLecture, financeExercises, 3);
        studyPlan.addCourse(finance);

        // Première tentative d'emploi du temps
        Schedule schedule1 = new Schedule(studyPlan);
        schedule1.addCourse(finance.getId());
        System.out.println("Emploi du temps 1 :");
        schedule1.print();
        /* On ne sait pas encore très bien quoi faire : on essaye donc
         * sur une copie de l'emploi du temps précédent.
         */
        Schedule schedule2 = new Schedule(schedule1);
        schedule2.addCourse(history.getId());
        System.out.println("Emploi du temps 2 :");
        schedule2.print();

        // Un troisième essai
        Schedule schedule3 = new Schedule(studyPlan);
        schedule3.addCourse(physics.getId());
        System.out.println("Emploi du temps 3 :");
        schedule3.print();
    }
}

