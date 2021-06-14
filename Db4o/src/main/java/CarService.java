public class CarService {
    private long id;
    private String name;
    private String repair;
    private int price;

    public CarService(long id, String name, String repair, int price) {
        this.id = id;
        this.name = name;
        this.repair = repair;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String toString() {
        return "W serwisie samochodowym o id = "+ id + ", nazwie = "+ name+ ", dokonano napraw: "+ repair + " w cenie = "+ price + " zł.";
    }


}
