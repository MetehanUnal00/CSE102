import java.util.*;

public class Ex10_20190808022 {
    public static void main(String[] args) {
    }
}

class User {
    private int id;
    private String username;
    private String email;
    private Set<User> followers;
    private Set<User> following;
    private Set<Post> likedPosts;
    private Map<User, Queue<Message>> messages;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.id = this.hashCode();
        followers = new HashSet<>();
        following = new HashSet<>();
        likedPosts = new HashSet<>();
        messages = new HashMap<>();
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public Set<User> getFollowers() {
        return followers;
    }
    public Set<User> getFollowing() {
        return following;
    }
    public Set<Post> getLikedPosts() {
        return likedPosts;
    }
    public int getId(){
        return this.id;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void message(User recipient, String content) {
        if (!messages.containsKey(recipient)) {
            messages.put(recipient, new LinkedList<>());
        }
        if (!recipient.messages.containsKey(this)) {
            recipient.messages.put(this, new LinkedList<>());
        }
        Message newMessage = new Message(this, content);
        messages.get(recipient).add(newMessage);
        recipient.messages.get(this).add(newMessage);
        read(recipient);
    }
    public void read(User user) {
        if (messages.containsKey(user)) {
            for (Message message : messages.get(user)) {
                System.out.println(message);
            }
        }
    }
    public void follow (User user) {
        if (user != this) {
            if (following.contains(user)) {
                following.remove(user);
                user.followers.remove(this);
            }
            else {
                following.add(user);
                user.followers.add(this);
            }
        }
        else {
            System.out.println("You cannot follow yourself.");
        }
    }
    public void like (Post post) {
        if (likedPosts.contains(post)) {
            likedPosts.remove(post);
            post.likedBy(this);
        }
        else {
            likedPosts.add(post);
            post.likedBy(this);
        }
    }
    public Post post (String content) {
        Post newPost = new Post(content);
        return newPost;
    }
    public Comment comment (Post post, String content) {
        Comment newComment = new Comment(content);
        post.commentBy(this, newComment);
        return newComment;
    }
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof User) {
            User user = (User) obj;
            return this.id == user.id;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.email);
    }
}

class Message {
    private boolean seen;
    private Date dateSent;
    private String content;
    private User sender;

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
        this.dateSent = new Date();
        this.seen = false;
    }
    public String read (User reader){
        if (this.sender != reader) {
            seen = true;
        }
        System.out.println("Sent at: " + dateSent + "\n" + "From: " + sender.getUsername());
        return this.content;
    }
    public boolean hasRead() {
        return seen;
    }
}

class Post {
    private Date datePosted;
    private String content;
    private Set<User> likes;
    private Map<User, List<Comment>> comments;

    public Post(String content) {
        this.content = content;
        this.datePosted = new Date();
        this.likes = new HashSet<>();
        this.comments = new HashMap<>();
    }
    public boolean likedBy(User user) {
        if (likes.contains(user)) {
            likes.remove(user);
            return false;
        }
        else {
            likes.add(user);
            return true;
        }
    }
    public void commentBy(User user, Comment comment){
        if (!comments.containsKey(user)) {
            comments.put(user, new LinkedList<>());
        }
        comments.get(user).add(comment);
    }
    public String getContent() {
        System.out.println("Posted at: " + datePosted);
        return content;
    }
    public Comment getComment(User user, int index) {
        if (comments.containsKey(user) && index < comments.get(user).size()) {
            return comments.get(user).get(index);
        }
        return null;
    }
    public int getCommentCount(){
        int count=0;
        if (comments.isEmpty()==true){
            return count;
        }
        else {
            for (List <Comment> commentList:comments.values()){
                for (Comment comment : commentList){
                    count++;
                }
            }
            return count;
        }
    }
    public int getCommentCountByUser(User user){
        int count = 0;
        if (!comments.containsKey(user)){
            return count;
        }
        else {
            return comments.get(user).size();
        }
    }
}

class Comment extends Post {

    public Comment(String content) {
        super(content);
    }
}

class SocialNetwork {
    private static Map<User, List<Post>> postsByUsers = new HashMap<>();

    public static User register(String username, String email){
        User user = new User(username, email);
        if (!postsByUsers.containsKey(user)){
            postsByUsers.put(user, new ArrayList<>());
            return user;
        }
        else {
            return null;
        }
    }
    
    public static Post post(User user, String content){
        if (postsByUsers.containsKey(user)){
            Post post = user.post(content);
            postsByUsers.get(user).add(post);
            return post;
        }
        else {
            return null;
        }
    }
    
    public static User getUser(String email){
        int hashedEmail = Objects.hash(email);
        for (User user : postsByUsers.keySet()) {
            if (user.getId() == hashedEmail) {
                return user;
            }
        }
        return null;
    }

    public static Set<Post> getFeed(User user){
        Set<Post> feed = new HashSet<>();
        for (User following : user.getFollowing()) {
            if (postsByUsers.containsKey(following)) {
                feed.addAll(postsByUsers.get(following));
            }
        }
        return feed;
    }

    public static Map<User, String> search(String keyword){
        Map<User, String> result = new HashMap<>();
        for (User user : postsByUsers.keySet()) {
            if (user.getUsername().contains(keyword)) {
                result.put(user, user.getUsername());
            }
        }
        return result;
    }
    
    public static <K, V> Map<V, Set<K>> reverseMap(Map<K, V> map) {
        Map<V, Set<K>> reversedMap = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (!reversedMap.containsKey(entry.getValue())) {
                reversedMap.put(entry.getValue(), new HashSet<>());
            }
            reversedMap.get(entry.getValue()).add(entry.getKey());
        }
        return reversedMap;
    }
}
