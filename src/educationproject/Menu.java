package educationproject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Menu implements MenuObject{

    private String title;
    List<MenuObject> options;
    
    public static Scanner sc;
    
    
    public Menu(String title){
        this.title = title;
        options = new ArrayList();
        addOption("Exit", s -> {});
         
    }
    
    @Override
    public void execute() {
        int choice;
        do{
        System.out.println("------------------------------------------------------");
        displayMenu();
        System.out.print("Make choice: ");
        choice = readNumber();
        options.get(choice).execute();
        } while (choice != 0);
    }

    public void displayMenu() {
        System.out.println(title);
        System.out.println("");
        for (int i = 0; i < options.size(); i++) {
            System.out.println(String.format("%d:  %s", i, options.get(i).getString()));
        }
    }
    
    
    public void addOption(MenuObject obj){
        options.add(obj);
    }
    
    public void addOption(String title, Consumer func){
        options.add(new MenuOption(title, func));
    }
    
    @Override
    public String getString() {
        return title;
    }
    
    public static String readString(){
        if (sc == null){
            sc = new Scanner(System.in);
        }
        return sc.nextLine();
    }
    
    public static int readNumber(){
        String str = readString();
        int num;
        try{
            num = Integer.parseInt(str);
        }
        catch (NumberFormatException e){
            num = -1;
        }
        return num;
    }

    private class MenuOption implements MenuObject{

        String title;
        Consumer func;
        
        public MenuOption(String title, Consumer func){
            this.title = title;
            this.func = func;
        }
        
        @Override
        public void execute() {
            func.accept(null);
        }

        @Override
        public String getString() {
            return title;
        }
    
    }
    
}
