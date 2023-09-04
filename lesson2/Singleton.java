public final class Singleton {

    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstatnce(){
        return INSTANCE;
    }
    
}
