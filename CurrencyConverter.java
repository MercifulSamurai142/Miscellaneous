//requires gson-2.3.1.jar added externally to classpath to work

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Map;
import java.util.Scanner;
public class CurrencyConverter {
    public static void main(String[] args) throws IOException {
        // Setting URL
        String url_str = "https://v6.exchangerate-api.com/v6/159e2f308afd4b7b7aaa479d/latest/USD";

        // Making Request
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        String jsonData = new Gson().toJson(jsonobj);

        // Accessing object
        String req_result = jsonobj.get("result").getAsString();

        // Parse JSON using Gson
        Gson gson = new Gson();
        ExchangeRateData exchangeRateData = gson.fromJson(jsonData, ExchangeRateData.class);


        // Access conversion rates
        Map<String, Double> conversion_rates = exchangeRateData.getConversionRates();

        if (conversion_rates == null){
            System.out.println("!!API NOT WORKING!! DATA NOT FETCHED !! RESTART PROGRAM !!");
            System.exit(1);
        }

        Scanner sc = new Scanner(System.in);
        boolean flag = true;
        do{
            int ch;
            System.out.println("Enter 1 to convert\nEnter 2 to view available country codes");
            ch=sc.nextInt();
            if(ch==2){
                int counter=0;
                for (Map.Entry<String, Double> entry : conversion_rates.entrySet()) {
                    if(counter%5==0)System.out.println();
                    System.out.print(entry.getKey() + "  ");
                    counter++;
                }
                counter=1;
                System.out.println();
                continue;
            }
            String ch1,ch2;
            System.out.print("Enter currency code to convert from : ");
            ch1=sc.next();
            System.out.print("Enter currency code to convert to   : ");
            ch2=sc.next();
            if(!(conversion_rates.containsKey(ch1)&&conversion_rates.containsKey(ch2))){
                System.out.println("\n!!!WRONG CODES!!!\n");
                continue;
            }
            double value1 = conversion_rates.get(ch1);
            double value2 = conversion_rates.get(ch2);
            System.out.print("Enter value to convert : ");
            double convert = sc.nextDouble();
            double result=(convert*value2)/value1;
            System.out.println("Converted result : "+convert+" "+ch1+" = "+result+" "+ch2);
            System.out.println("Do you wish to continue?(0 for no/anything else for yes)");
            ch=sc.nextInt();
            if (ch==0)flag=false;

        }while(flag);




    }
}
class ExchangeRateData {
    private String result;
    private String documentation;
    private String terms_of_use;
    private long time_last_update_unix;
    private String time_last_update_utc;
    private long time_next_update_unix;
    private String time_next_update_utc;
    private String base_code;
    private Map<String, Double> conversion_rates;

    // Getters and setters for all fields

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getTermsOfUse() {
        return terms_of_use;
    }

    public void setTermsOfUse(String terms_of_use) {
        this.terms_of_use = terms_of_use;
    }

    public long gettime_last_update_unix() {
        return time_last_update_unix;
    }

    public void settime_last_update_unix(long time_last_update_unix) {
        this.time_last_update_unix = time_last_update_unix;
    }

    public String gettime_last_update_utc() {
        return time_last_update_utc;
    }

    public void settime_last_update_utc(String time_last_update_utc) {
        this.time_last_update_utc = time_last_update_utc;
    }

    public long gettime_next_update_unix() {
        return time_next_update_unix;
    }

    public void settime_next_update_unix(long time_next_update_unix) {
        this.time_next_update_unix = time_next_update_unix;
    }

    public String gettime_next_update_utc() {
        return time_next_update_utc;
    }

    public void settime_next_update_utc(String time_next_update_utc) {
        this.time_next_update_utc = time_next_update_utc;
    }

    public String getbase_code() {
        return base_code;
    }

    public void setbase_code(String base_code) {
        this.base_code = base_code;
    }

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

    public void setConversionRates(Map<String, Double> conversion_rates) {
        this.conversion_rates = conversion_rates;
    }
}
