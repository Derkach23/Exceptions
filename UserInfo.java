import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;

public class UserInfo {

    public static void main(String[] args) {
        try {
            String[] userInfo = getUserInfo();

            if (userInfo.length != 6) {
                if (userInfo.length < 6) {
                    System.out.println("Введено недостаточно данных.");
                } else {
                    System.out.println("Введено больше данных, чем требуется.");
                }
                return;
            }

            String фамилия = userInfo[0];
            String имя = userInfo[1];
            String отчество = userInfo[2];
            String датаРождения = userInfo[3];
            String номерТелефона = userInfo[4];
            char пол = userInfo[5].charAt(0);

            checkDateOfBirth(датаРождения);
            checkPhoneNumber(номерТелефона);
            checkGender(пол);

            try (FileWriter writer = new FileWriter(фамилия + ".txt", true)) {
                writer.write(фамилия + " " + имя + " " + отчество + " " + датаРождения + " " + номерТелефона + " " + пол + "\n");
                writer.flush();
            } catch (IOException e) {
                System.out.println("Ошибка при записи в файл.");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении данных от пользователя.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный формат введенных данных.");
            System.out.println(e.getMessage());
        }
    }

    public static String[] getUserInfo() throws IOException {
        System.out.println("Введите следующие данные, разделенные пробелом:");
        System.out.println("Фамилия Имя Отчество Дата рождения Номер телефона Пол (f или m)");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine().split(" ");
    }

    public static void checkDateOfBirth(String date) {
        if (!Pattern.matches("^(\\d{2}\\.\\d{2}\\.\\d{4})|(\\d{2}\\.\\d{2}\\.\\d{2})$", date)) {
            throw new IllegalArgumentException("Неверный формат даты рождения. Ожидается формат дд.мм.гггг или дд.мм.гг.");
        }
    }

    public static void checkPhoneNumber(String phoneNumber) {
        if (!Pattern.matches("^\\d+$", phoneNumber)) {
            throw new IllegalArgumentException("Неверный номер телефона. Ожидается целое число без форматирования.");
        }
    }

    public static void checkGender(char gender) {
        if (gender != 'f' && gender != 'm') {
            throw new IllegalArgumentException("Неверный пол. Ожидается символ латиницей f (женский) или m (мужской).");
        }
    }
}