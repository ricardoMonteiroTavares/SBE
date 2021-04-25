package Singletons;

public class ProfileSingleton 
{
	
	private static ProfileSingleton singleton = null;
	private String[] profiles;

	private ProfileSingleton() {}

	public static ProfileSingleton getSingleton() 
	{
		if (singleton == null) { singleton = new ProfileSingleton();}
		return singleton;
	}

	public String[] getProfiles() 
	{ 
		return profiles;
	}

	public void setProfiles(String[] profiles) 
	{
		this.profiles = profiles;
	}
}
