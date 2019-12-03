import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class FileUtils {
    private FileUtils(){}

    public static boolean safeThisStep(Field field, int currentStep){
        boolean wasWritten = true;
        List<Being> beings = field.getBeings();
        List<Food> food = field.getFood();
        File file = new File("src/main/resources/" + currentStep + ".txt");
        try(FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(Being.PARAMETERS);
            fileWriter.append("\n");
            for (Being b: beings) {
                fileWriter.write(b.toString());
                fileWriter.append("\n");
            }

            fileWriter.write("\n");
            fileWriter.write(Food.PARAMETERS);
            fileWriter.append("\n");
            for (Food f: food){
                fileWriter.write(f.toString());
                fileWriter.append("\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
            wasWritten = false;
            e.printStackTrace();
        }
        return wasWritten;
    }
}
