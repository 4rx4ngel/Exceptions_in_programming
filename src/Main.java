import java.io.*;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class Main {
    public static void main(String[] args) {


        try {
            makeRecord();
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            System.out.println(e.getMessage());
        }

    }

    public static void makeRecord() throws Exception{
        System.out.println("Введите фамилию, имя, отчество, дату рождения (dd.mm.yyyy), номер телефона" +
                " (число без разделителей) и пол(f или m), разделенные пробелом");

        String[] array = getStrings();

        String surname = array[0];
        String name = array[1];
        String patronymic = array[2];

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date birthdate;
        try {
            birthdate = format.parse(array[3]);
        }catch (ParseException e){
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        long phone;
        try {
            phone = Long.parseLong(array[4]);
        }catch (NumberFormatException e){
            throw new NumberFormatException("Неверный формат телефона");
        }

        String sex = array[5];
        if (!sex.equalsIgnoreCase("m") && !sex.equalsIgnoreCase("f")){
            throw new RuntimeException("Неверно введен пол");
        }

        String fileName = capitalized(surname) + ".txt";
        File file = new File(fileName);
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            if (file.length() > 0){
                fileWriter.write('\n');
            }
            fileWriter.write(capitalized(surname) + " " + capitalized(name) + " " + capitalized(patronymic) + " "
                    + format.format(birthdate)+ " " + phone + " " + sex.toLowerCase());
            fileWriter.close();
            System.out.println("Запись прошла успешно");


        }catch (IOException e){
            throw new FileSystemException("Ошибка при работе с файлом");
        }

    }

    private static String[] getStrings() throws Exception {
        String text;
        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))) {
            text = bf.readLine();
        }catch (IOException e){
            throw new Exception("Произошла ошибка при работе с консолью");
        }

        String[] array = text.split(" ");
        if (array.length > 6){
            throw new Exception("Введено больше 6 параметров");
        }
        if (array.length < 6){
            throw new Exception("Введено меньше 6 параметров");
        }
        return array;
    }

    private static String capitalized(String string){
        return string.substring(0,1).toUpperCase() + string.substring(1).toLowerCase();
    }
}