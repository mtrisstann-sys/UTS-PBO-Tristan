public class Mammal extends Animal {
    // Private Field
    private String furColor;

    // Constructor
    public Mammal(String name, int age, String furColor) {
        super(name, age); // Memanggil constructor parent class (Animal)
        this.furColor = furColor;
    }

    // Getters dan Setters
    public String getFurColor() {
        return furColor;
    }

    public void setFurColor(String furColor) {
        this.furColor = furColor;
    }

    // Method Overriding
    @Override
    public String makeSound() {
        return "The mammal makes a sound.";
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", Fur Color: " + furColor;
    }
}
