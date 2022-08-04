import java.io.*;
import java.util.*;
class InvalidAgeException extends Exception
{
	public String toString()
	{
		return("you are minor now you can't vote\n");

	}
}
class Check 
{
	int check_id(String file_name,int id)
	{
		try
		{
		 File f1=new File(file_name);
		 Scanner s1=new Scanner(f1);
		 while(s1.hasNextLine())
		 {
			 String vi=s1.nextLine();
			 if(vi.equals(Integer.toString(id)))
			 {
				 return 1;
			 }
		 }
		}
		catch(Exception e)
		{
	         System.out.println(e);
		}
		return 0;
	}
}
class VoterDetails implements Serializable
{
	String name;
	int age;
	int voterid;
	String adharno;
	public VoterDetails(String n,int a,int vi,String an)
	{
		this.name=n;
		this.age=a;
		this.voterid=vi;
		this.adharno=an;
	}
	public VoterDetails() {
		// TODO Auto-generated constructor stub
	}
	int vi;
	static int b1=0,b2=0,b3=0;
    static	ArrayList<VoterDetails> l=new ArrayList<VoterDetails>();
	void register()throws Exception
	{
		Check ch=new Check();
	    FileOutputStream f1=new FileOutputStream("registerids.txt");
	    ObjectOutputStream d=new ObjectOutputStream(f1);
		Scanner s=new Scanner(System.in);
		System.out.println(" Enter your full name:");
		String n=s.nextLine();
		try
		{
		  System.out.println("Enter your age");
		  int a=s.nextInt();
		  if(a<18)
		  {
		   throw new InvalidAgeException();
		  }
		  else{
		      System.out.println("enter your adhar number");
			  Scanner z=new Scanner(System.in);
			  String an=z.nextLine();
			  int choice=ch.check_id("adharids.txt",Integer.parseInt(an));
			  if(choice == 0)
			  {
			    System.out.println("enter the voter id to be created(4 digits)");
		        vi=s.nextInt();
		        while(true)
		        {
		          int r=ch.check_id("registered_voterids.txt", vi);
		          if(r==1)
		          {
		        	  System.out.println("voter id already exits try again..");
		        	  vi=s.nextInt();
		          }
		          if(r==0)
		          {
		        	  File fe=new File("registered_voterids.txt");
		        	  FileWriter fw=new FileWriter(fe,true);
		        	  fw.write(Integer.toString(vi)+"\n");
		        	  fw.flush();
		        	  fw.close();
		        	  break;
		          }
		        }
		        l.add(new VoterDetails(n,a,vi,an));
		        d.writeObject(l);
		        System.out.println("\n\nYou have successfully registered,you can vote now.\n");
		        d.flush();
		        d.close();
		        File adi = new File("adharids.txt");
		        FileWriter fos1=new FileWriter(adi,true);
		        fos1.write(an+"\n");
		        fos1.flush();
		        fos1.close();
			 }
			 else
				 System.out.println("You have already registered");
		  }
		 }
		 catch(InvalidAgeException e)
		{
				System.out.println(e);
		}
	}
		@SuppressWarnings("unchecked")
	void vote()throws Exception
	{
		int f=0;
		Scanner sc=new Scanner(System.in);
		System.out.println("enter the voter id");
		FileInputStream f1=new FileInputStream("registerids.txt");
		ObjectInputStream d1=new ObjectInputStream(f1);
		int v=sc.nextInt();
		while(d1.available()>0)
		{
			l=(ArrayList<VoterDetails>)d1.readObject();
		}
		Iterator<VoterDetails> it=l.iterator();
		while(it.hasNext())
		{
			VoterDetails vo=it.next();
			if(vo.voterid==v)
			{
				System.out.println("Name :"+vo.name);
				f=1;
				break;
			}
		}
		if(f==1)
		{
			Check ch=new Check();
			int cho=ch.check_id("voters.txt",v);
				
			if(cho==1)
			{
				System.out.println("Your have already voted. You can't vote now\n");
			}

			else
			{
				 System.out.println("enter 1 to vote AIMIM:Asad");
				 System.out.println("enter 2 to vote TRS:john");
				 System.out.println("enter 3 to vote BJP:Alex");
				 int c=sc.nextInt();
				 if(c==1)
					b1++;
				 else if(c==2)
					b2++;
				 else if(c==3)
					b3++;
				 if(c>=1 && c<=3)
					System.out.println("Your vote has been submitted successfully\n");
				 else
					System.out.println("enter correct number\n");
				 File file = new File("voters.txt");
                 FileWriter fos=new FileWriter(file,true);
				 fos.write(Integer.toString(v)+"\n");
			     fos.flush();
				 fos.close();
			}
		}
		else
			System.out.println("you have not registered yet. You can't vote without registering.\n");
	}

}

class Party
{
	String canditate;
	String partyname;
	Party(String n,String pn)
	{
		this.canditate=n;
		this.partyname=pn;
	}
	public String toString()
	{
		return("canditate:"+canditate+" "+"partyname:"+partyname);
	}
}
class Admin
{
 void manageParty()
 {
	ArrayList<Party> at=new ArrayList<Party>();
	at.add(new Party("Asad","AIMIM"));
	at.add(new Party("john","TRS"));
	at.add(new Party("Alex","BJP"));
	System.out.println("\nthe canditates and their parties are\n");
	for(Party p:at)
	{
		System.out.println("canditate:"+p.canditate+", "+"partyname:"+p.partyname);
		 System.out.println();
	}
 }
 void pollResult()
 {
	 //VoterDetails sd=new VoterDetails();
	 System.out.println("party:AIMIM, Canditate:Asad,  no of votes:"+VoterDetails.b1);
	 System.out.println("party:TRS, Canditate:john,  no of votes:"+VoterDetails.b2);
	 System.out.println("party:BJP, Canditate:Alex,  no of votes:"+VoterDetails.b3);
	 System.out.println("");
 }
}

class Voting
{
	public static void main(String args[]) throws Exception
	{
		System.out.println("     <----ONLINE VOTING APP---->    \n");
	 while(true)
	 {
		 Scanner sc=new Scanner(System.in);
		 System.out.println("Do you want to-");
		 System.out.println("1.login to admin");
		 System.out.println("2.register and vote");
		 //note that without registering you can't vote
		 System.out.println("3. exit\n");
		 int ch=sc.nextInt();
		 if(ch==1)
		 {
			 Scanner s=new Scanner(System.in);
			final String password="admin@145";
			System.out.println("enter password");
			String p=s.nextLine();
			if(p.equals(password))
			{
				while(true){
				Scanner sa=new Scanner(System.in);
				Admin ad=new Admin();
				System.out.println("press");
				System.out.println("1.check canditates who are campaigning");
				System.out.println("2.check poll results");
				System.out.println("3.log out\n");
				int c=sa.nextInt();
				if(c==1)
				{
					ad.manageParty();
				}
				if(c==2)
				{
					ad.pollResult();
				}
				if(c==3)
					break;
			}}
			else
				System.out.println("wrong password try again\n");
		 }
		 if(ch==2)
		 {
			Scanner sv=new Scanner(System.in);
			VoterDetails vd=new VoterDetails();
			while(true){
				System.out.println("press");
			System.out.println("1.Register");
			System.out.println("2.Give vote");
			System.out.println("3.Go back");
			int c=sv.nextInt();
			if(c==1)
			{
				vd.register();
			}
			if(c==2)
			{
				vd.vote();
			}
			if(c==3)
				break;}
		 }
		 if(ch==3)
		 {
			 System.out.println("Thank you for participating.");
			 break;
		 }
	 }
	}
}
