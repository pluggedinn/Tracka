package com.example.rsons.tracka;

import android.content.Context;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by rsons on 4/8/2017.
 */

public class FactsRetriever {

    int factId;
    // 0 language
    // 1 walk calories
    // 2 run calories
    // 3 read books
    // 4 earned minimum wage
    // 5 email respond
    // 6 week worth of meals
    // 7 cook omelettes
    // 8 saied I love you
    // 9 climbed mountain
    // 10 cooked pizzas

    String factString;
    Context context;

    // TODO: download all the drawables and order them according to the list above
    public FactsRetriever(Context c) {
        this.context = c;
    }

    public void generateNewFact(long time){
        Random r = new Random();
        factId = r.nextInt(10);

        // Language fact
        if (factId == 0) {
            int percentageProficiency = (int) (time * 100 / 1100);
            String[] languages = context.getResources().getStringArray(R.array.languages);
            String randomLanguage = languages[r.nextInt(languages.length - 1)];
            factString = "become " + percentageProficiency + "% proficient in " + randomLanguage;
        }
        // Walk calories fact
        else if (factId == 1) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
            float metersWalked = seconds * 1.4f;
            // On the assumption of this chart
            // https://www.quora.com/How-many-calories-do-we-burn-walking-1-km
            int calories = Math.round(60 * metersWalked / 1000);
            factString = "walked and burned " + calories + " calories";
        }
        // Run calories fact
        else if (factId == 2) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
            float metersWalked = seconds * 1.4f;
            // On the assumption of this chart
            // https://www.quora.com/How-many-calories-do-we-burn-walking-1-km
            int calories = Math.round(75 * metersWalked / 1000);
            factString = "run and burned " + calories + " calories";
        }
        // Books read
        else if (factId == 3) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
            // Average word per minute is 200
            long wordsRead = minutes * 200;
            String[] books = context.getResources().getStringArray(R.array.books);
            String randomBook = books[r.nextInt(books.length - 1)];
            String bookName = randomBook.split("|")[0];
            double wordsInBook = Double.parseDouble(randomBook.split("|")[1]);
            double timesBookRead = wordsRead / wordsInBook;
            factString = "read '" + bookName + "' " + timesBookRead + " times";
        }
        // Money earned minimum wage
        else if (factId == 4) {
            long hours = TimeUnit.MILLISECONDS.toHours(time);
            factString = "earned $" + hours*10.25 + " in California or $" + hours*7.25 + " Utah minimum wage";
        }
        // Email responded
        else if (factId == 5) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
            factString = "responded " + minutes/5 + " emails";
        }
        // Week worth of meal
        else if (factId == 6) {
            long hours = TimeUnit.MILLISECONDS.toHours(time);
            // let's say it takes 3 hours to cook 21 meals
            double mealsPrepared = hours * 21 / 3;
            factString = "prepared " + mealsPrepared + " full meals";
        }
        // Omeletes cooked
        else if (factId == 7) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
            // it takes around 3 minutes to cook an omelette
            factString = "cooked " + minutes / 3 + " nice omelets";
        }
        // Said I love you
        else if (factId == 8) {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(time);
            // it takes 5 seconds to type 'I love you' plus 15 seconds to find the person on the phone = 20 seconds
            factString = "text 'I LOVE YOU' to " + seconds / 20 + " friends";
        }
        // Climb mountains
        else if (factId == 9) {
            long days = TimeUnit.MILLISECONDS.toDays(time);
            String[] mountains = context.getResources().getStringArray(R.array.books);
            String randomMountains = mountains[r.nextInt(mountains.length - 1)];
            String mountainName = randomMountains.split("|")[0];
            double daysToClimb = Double.parseDouble(randomMountains.split("|")[1]);
            double timesToClimb = days / daysToClimb;
            factString = "climbed " + mountainName + " " + timesToClimb + " times";
        }
        // Pizza cooked
        else if (factId == 10) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
            factString = "baked " + minutes / 12 + " tasty pizzas";
        }
    }

    public String getFactDrawableString() {
        String[] factsImageDrawables = context.getResources().getStringArray(R.array.languages);
        return factsImageDrawables[factId];
    }

    public String getFactString() {
        return factString;
    }


}


