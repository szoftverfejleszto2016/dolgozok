package dolgozok;

import java.util.Scanner;

public class Dolgozok {

    public static void main(String[] args) {
        DB ab = new DB();
        Scanner bill = new Scanner(System.in, "cp1250");
        boolean tovabb = true;
        String nev, szulido, fnev;
        int fizetes;

        while (tovabb) {
            System.out.println("1-listázás 2-új 3-importálás 4-exportálás "
                             + "5-emelés 6-törlés 0-kilépés");
            System.out.print("Válasz: ");
            String v = bill.nextLine();
            switch (v.charAt(0)) {
                case '1':
                    ab.lista();
                    break;
                default:
                    tovabb = false;
                    break;
            }
        }
    }

}
