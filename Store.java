import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ToyStore.Toy;

public class ToyStore {
    private List<Toy> toys = new ArrayList<>();

    public void addToy(Toy toy) {
        toys.add(toy);
    }

    public void updateWeight(int toyId, double newWeight) {
        for (Toy toy : toys) {
            if (toy.getId() == toyId) {
                toy.setWeight(newWeight);
                break;
            }
        }
    }

    public Toy choosePrizeToy() {
        double totalWeight = toys.stream().mapToDouble(Toy::getWeight).sum();
        double random = Math.random() * totalWeight;

        for (Toy toy : toys) {
            random -= toy.getWeight();
            if (random <= 0) {
                Toy prizeToy = new Toy(toy.getId(), toy.getName(), 1, toy.getWeight());
                toy.decreaseQuantity();
                toys.remove(toy);
                return prizeToy;
            }
        }
        return null;
    }

    public void writeToFile(Toy toy) {
        try (FileWriter writer = new FileWriter("prize_toys.txt", true)) {
            writer.write("ID: " + toy.getId() + ", Name: " + toy.getName() + "\n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл.");
        }
    }
}
