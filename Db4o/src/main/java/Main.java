import com.db4o.Db4o;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Scanner scan = new Scanner(System.in);
        ObjectContainer db = Db4o.openFile("CarSerivce");

        int menu;
        int menu_interrior = -1;
        String name;
        String repair;
        int price;

        while(true) {
            System.out.print("Główne Menu:\n");
            System.out.print("\n");
            System.out.print("1.Dodawanie do bazy danych.\n");
            System.out.print("2.Usuwanie całej bazy danych.\n");
            System.out.print("3.Usuwanie rekordu z bazy danych o podanym id.\n");
            System.out.print("4.Usuwanie rekordu z bazy danych o podanej nazwie.\n");
            System.out.print("5.Wyświetlenie całej bazy danych.\n");
            System.out.print("6.Wyświetlenie rekordu z bazy danych o podanym id.\n");
            System.out.print("7.Wyświetlenie rekordu z bazy danych o podanej nazwie.\n");
            System.out.print("8.Modyfikacja rekordu o podanym id.\n");
            System.out.print("0.Wyjście z programu.\n");

            System.out.print("Wybierz funkcje. I kliknij klawisz ENTER: ");
            menu = scan.nextInt();

            if (menu == 1) {
                System.out.print("Menu dodawania do bazy danych:\n");

                System.out.print("Dodaj nazwe: \n");
                name = in.readLine();

                System.out.print("Dodaj rodzaj naprawy: \n");
                repair = in.readLine();

                System.out.print("Dodaj koszt usługi (zł): \n");
                price = scan.nextInt();


                AddCarService(db, name, repair, price);
                System.out.print("\n");

                if (menu_interrior == 0) {
                    System.out.print("\n");
                    System.out.print("\n");
                    menu_interrior = -1;
                    continue;
                }
            }

            if (menu == 2) {
                deleteAllCarService(db);
                System.out.print("Usunięto wszystkie dane z bazy danych.\n");
                System.out.print("\n");
            }

            if (menu == 3) {
                System.out.print("Wyświetlanie wszystkich danych:\n");
                ShowAll(db);
                System.out.print("\n");

                System.out.print("Podaj klucz do usunięcia:\n");
                Integer key = scan.nextInt();
                deleteCarServiceByID(db, key);
                System.out.print("\n");
            }

            if (menu == 4) {
                System.out.print("Wyświetlanie wszystkich serwisów samochodowych:\n");
                ShowAll(db);

                System.out.print("\n");
                System.out.print("Podaj nazwe do usunięcia:\n");

                String nam = in.readLine();
                deleteCarServiceByName(db, nam);

                System.out.print("Usunięto dane:\n");
                ShowAll(db);
                System.out.print("\n");
            }

            if (menu == 5) {
                System.out.print("\n");
                System.out.print("Wyświetlanie wszystkich serwisów samochodowych:\n");
                ShowAll(db);
                System.out.print("\n");
            }

            if (menu == 6) {
                System.out.print("\n");
                System.out.print("Podaj id:\n");
                long key = scan.nextLong();
                System.out.print("Wyświetlanie serwisu samochodowego o podanym id:\n");
                ShowCarServiceById(db, key);
                System.out.print("\n");
            }

            if (menu == 7) {
                System.out.print("\n");
                System.out.print("Podaj nazwe:\n");
                String nam = in.readLine();
                System.out.print("Wyświetlanie serwisu samochodowego o podanej nazwie:\n");
                ShowCarServiceByName(db, nam);
                System.out.print("\n");
            }

            if (menu == 8) {
                ShowAll(db);
                System.out.print("\n");

                System.out.print("Podaj klucz do modyfikacji:\n");
                long key = scan.nextLong();

                System.out.print("\n");
                System.out.print("Podaj nową nazwe: \n");
                String nam = in.readLine();

                System.out.print("Podaj nowy rodzja naprawy: \n");
                repair = in.readLine();

                System.out.print("Podaj nową cene (zł): \n");
                price = scan.nextInt();

                UpdateCarService(db, key, nam, repair, price);
                System.out.print("\n");
                System.out.print("Zmieniony rekord:\n");
                ShowCarServiceById(db, key);
                System.out.print("\n");

            }

            if (menu == 0) {
                System.out.print("\n");
                System.out.print("Wyłączanie programu...\n");
                scan.close();
                db.close();
                System.exit(0);
            }


            if (menu < 0 || menu > 8) {
                System.out.print("\n");
                System.out.print("Nie ma takiej opcji!\n");
                System.out.print("\n");
                continue;
            }
        }
    }

    private static void AddCarService(ObjectContainer db, String name, String repair, int price){
        Random r = new Random(System.currentTimeMillis());
        Long id = (long) Math.abs(r.nextInt());

        CarService carService = new CarService(id, name, repair, price);
        db.store(carService);
        System.out.println("Stored "+ carService);
    }

    public static void ShowAll(ObjectContainer db) {
        ObjectSet result = db.queryByExample(CarService.class);

        System.out.println("Ilość rekordów w tabeli: " + result.size());
        while(result.hasNext()) {
            System.out.println(result.next());
        }
    }

    public static void ShowCarServiceById(ObjectContainer db, long id) {
        CarService carService = new CarService(id, null, null, 0);
        ObjectSet result = db.queryByExample(carService);

        System.out.println("Ilość rekordów w tabeli: " + result.size());
        while(result.hasNext()) {
            System.out.println(result.next());
        }
    }

    public static void ShowCarServiceByName(ObjectContainer db, String name) {
        CarService carService = new CarService(0, name, null, 0);

        ObjectSet result = db.queryByExample(carService);

        System.out.println("Ilość rekordów w tabeli: " + result.size());
        while(result.hasNext()) {
            System.out.println(result.next());
        }
    }

    public static void UpdateCarService(ObjectContainer db, long id, String name, String repair, int price){
        ObjectSet result = db.queryByExample(new CarService(id, null, null, 0));

        CarService carServiceChange = (CarService) result.next();

        carServiceChange.setName(name);
        carServiceChange.setRepair(repair);
        carServiceChange.setPrice(price);

        db.store(carServiceChange);
    }

    public static void deleteAllCarService(ObjectContainer db){
        CarService carService = new CarService(0, null, null, 0);

        ObjectSet result = db.queryByExample(carService);


        while(result.hasNext()) {
            db.delete(result.next());
        }
    }

    public static void deleteCarServiceByID(ObjectContainer db, long id){
        ObjectSet result = db.queryByExample(new CarService(id, null, null, 0));

        CarService carServiceFound = (CarService)result.next();

        db.delete(carServiceFound);
    }

    public static void deleteCarServiceByName(ObjectContainer db, String name){
        ObjectSet result = db.queryByExample(new CarService(0, name, null, 0));

        CarService carServiceFound = (CarService)result.next();

        db.delete(carServiceFound);
    }
}
