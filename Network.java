/** Represents a social network. The network has users, who follow other uesrs.
 *  Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network  with some users. The only purpose of this constructor is 
     *  to allow testing the toString and getUser methods, before implementing other methods. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    /** Finds in this network, and returns, the user that has the given name.
     *  If there is no such user, returns null.
     *  Notice that the method receives a String, and returns a User object. */
    public User getUser(String name) {
       for ( int i = 0; i < userCount; i++){
        if (users[i] != null && users[i].getName().equals(name)) {
            return users[i];
       }
        
    }
     return null;
    }
    /** Adds a new user with the given name to this network.
    *  If ths network is full, does nothing and returns false;
    *  If the given name is already a user in this network, does nothing and returns false;
    *  Otherwise, creates a new user with the given name, adds the user to this network, and returns true. */
    public boolean addUser(String name) {
       if (userCount == 1000){
        return false;
       }
       for (int i = 0; i < userCount; i++){
        if (users[i] != null && users[i].getName().equals(name)) {
            return false;
        }
        }
       users[userCount] = new User(name); 
        userCount++; 
        return true;
       }
       
    

    /** Makes the user with name1 follow the user with name2. If successful, returns true.
     *  If any of the two names is not a user in this network,
     *  or if the "follows" addition failed for some reason, returns false. */
    public boolean addFollowee(String name1, String name2) {
        User first = getUser(name1);
        User second = getUser(name2);
        if (first == null || second == null) {
        return false;}
        
        
        
        return first.addFollowee(name2); 
    }
    
    /** For the user with the given name, recommends another user to follow. The recommended user is
     *  the user that has the maximal mutual number of followees as the user with the given name. */
    public String recommendWhoToFollow(String name) {
        User dude = getUser(name);
        int max = 0;
        String result = "";

        if (dude == null) {
        return "";
    }

       
       
        for (int i = 0; i < userCount; i++){
            User other = users[i];
            
            if (other == null || other.getName().equals(name)) {
            continue;
        }
          
        String title = other.getName();
        if (dude.follows(title)) {
            continue;
        }
          
        int mutualCount = dude.countMutual(other);
        if (mutualCount > max) {
            max = mutualCount;
            result = title;
        }
    }
        
        return result;
    }

    /** Computes and returns the name of the most popular user in this network: 
     *  The user who appears the most in the follow lists of all the users. */
    public String mostPopularUser() {
        if (userCount == 0){
            return null;
        }
        int [] tracker = new int [userCount];
        for (int i = 0; i < userCount; i++){
            for (int j = 0; j < userCount; j++){
                if (users[j] != null && users[j].follows(users[i].getName())) {
                tracker[i]++;
            }
        }    
    }
        int max = 0;
        for (int i = 1; i < userCount; i++) {
        if (tracker[i] > tracker[max]) {
            max = i;
        }
   
    }
        return users[max].getName();
        }
    
    /** Returns the number of times that the given name appears in the follows lists of all
     *  the users in this network. Note: A name can appear 0 or 1 times in each list. */
    private int followeeCount(String name) {
        int count = 0;
        for (int i = 0; i < userCount; i++) { 
        if (users[i] != null && users[i].follows(name)) { 
            count++;
    }
        }  
        return count;
    }
   public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < userCount; i++) {
        if (users[i] != null) {
            sb.append(users[i].getName()).append(" -> ");
            String[] followees = users[i].getfFollows();
            int fCount = users[i].getfCount();
            for (int j = 0; j < fCount; j++) {
                sb.append(followees[j]);
                if (j < fCount - 1) {
                    sb.append(", ");
                }
            }
            sb.append("\n");
        }
    }
    return sb.toString();
}
}