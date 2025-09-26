public class Bird extends Animal {
    // Private Field
    private boolean canFly;

    // Constructor
    public Bird(String name, int age, boolean canFly) {
        super(name, age); // Memanggil constructor parent class (Animal)
        this.canFly = canFly;
    }

    // Getters dan Setters
    public boolean isCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    // Method Overriding
    @Override
    public String makeSound() {
        return "The bird chirps.";
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Can Fly: " + ( canFly ? "Yes" : "No" );
    }
}
