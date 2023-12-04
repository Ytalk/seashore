import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Rentals implements Serializable, Rentables{
    private ArrayList<Rental> rentals;

    public Rentals(){
        this.rentals = new ArrayList<Rental>();
    }

    //@Override
    public boolean isEmpty(){
        return rentals.isEmpty();
    }

    public String newRental(int id, int time, boolean hasLesson){

        if(hasLesson == true){
            EquipmentWithLesson equipment_with_lesson = new EquipmentWithLesson(id);
            Lesson less = new Lesson(equipment_with_lesson);
            Rental rental = new Rental(time, less);
            rentals.add(rental);
            return rental.toString();
        }
        else{
            EquipmentWithoutLesson equipment_without_lesson = new EquipmentWithoutLesson(id);
            Rental rental = new Rental(time, equipment_without_lesson);
            rentals.add(rental);
            return rental.toString();
        }
    }

    //invalidEquipmentException-----

    /*public void saveToFile(String name){

    }*/

    public String listAll(){
        StringBuilder rentals_list = new StringBuilder();
        for(Rental rental : rentals){
            rentals_list.append(rental.toString()).append("\n\n");
        }

        return rentals_list.toString();
    }

    public List<String> listAll2() {
        List<String> list = new ArrayList<>();
    
        for (int i = 0; i < rentals.size(); i++) {
            list.add(rentals.get(i).toString());
    
            // Adicione uma linha vazia entre os aluguéis, exceto para o último aluguel
            if (i < rentals.size() - 1) {
                list.add("\n=================================================");
            }
        }
    
        return list;
    }

    /*public int size(){

    }*/
}