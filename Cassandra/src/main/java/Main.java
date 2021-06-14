import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.querybuilder.truncate.Truncate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Scanner scan = new Scanner(System.in);
        CqlSession session = CqlSession.builder().build();

        KeyspaceBuilder keyspaceManager = new KeyspaceBuilder(session, "CarServices");
        keyspaceManager.dropKeyspace();
        keyspaceManager.selectKeyspaces();
        keyspaceManager.createKeyspace();
        keyspaceManager.useKeyspace();


        CarServiceTableBuilder CarServiceManager = new CarServiceTableBuilder(session);
        CarServiceManager.createTable();

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


                CarServiceManager.addCarService(name, repair, price);
                System.out.print("\n");

                if (menu_interrior == 0) {
                    System.out.print("\n");
                    System.out.print("\n");
                    menu_interrior = -1;
                    continue;
                }
            }

            if (menu == 2) {

                CarServiceManager.deleteAllCarService();
                System.out.print("Usunięto wszystkie dane z bazy danych.\n");
                System.out.print("\n");
            }

            if (menu == 3) {
                System.out.print("Podaj klucz do usunięcia:\n");
                Integer key = scan.nextInt();
                CarServiceManager.deleteCarServiceById(key);
                System.out.print("\n");
            }

            if (menu == 4) {
                System.out.print("Wyświetlanie wszystkich danych:\n");
                CarServiceManager.ShowAll();

                System.out.print("\n");
                System.out.print("Podaj nazwe do usunięcia:\n");

                String nam = in.readLine();
                CarServiceManager.deleteCarServiceByName(nam);

                System.out.print("Usunięto dane:\n");
                CarServiceManager.ShowAll();
                System.out.print("\n");
            }

            if (menu == 5) {
                System.out.print("\n");
                System.out.print("Wyświetlanie wszystkich danych:\n");
                CarServiceManager.ShowAll();
                System.out.print("\n");
            }

            if (menu == 6) {
                System.out.print("\n");
                System.out.print("Podaj id:\n");
                int key = scan.nextInt();
                System.out.print("Wyświetlanie danych o podanym id:\n");
                CarServiceManager.ShowCarServiceById(key);
                System.out.print("\n");
            }

            if (menu == 7) {
                System.out.print("\n");
                System.out.print("Podaj nazwe:\n");
                String nam = in.readLine();
                System.out.print("Wyświetlanie serwisu o podanej nazwie:\n");
                CarServiceManager.ShowCarServiceByName(nam);
                System.out.print("\n");
            }

            if (menu == 8) {
              CarServiceManager.ShowAll();
                System.out.print("\n");

                System.out.print("Podaj klucz do modyfikacji:\n");
                Integer key = scan.nextInt();

                System.out.print("\n");
                System.out.print("Podaj nową nazwe: \n");
                String nam = in.readLine();

                System.out.print("Podaj nowy rodzja naprawy: \n");
                repair = in.readLine();

                System.out.print("Podaj nową cene (zł): \n");
                price = scan.nextInt();

                CarServiceManager.updateCarService(key, nam, repair, price);
                System.out.print("\n");
                System.out.print("Zmieniony rekord:\n");
                CarServiceManager.ShowCarServiceById(key);
                System.out.print("\n");

            }

            if (menu == 0) {
                System.out.print("\n");
                System.out.print("Wyłączanie programu...\n");
                scan.close();
                session.close();
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
}
