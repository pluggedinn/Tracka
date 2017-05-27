package com.example.rsons.tracka;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;


/**
 * Usage:
 *  FactsRetriever rt = new FactsRetriever(context);
 *  rt.generateNewFact(32215); // where 32215 is the period of time
 *  rt.getFactDrawable();
 *  rt.getFactString();
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
    ArrayList<Integer> factNumbers;
    Random r;

    public FactsRetriever(Context c) {
        this.context = c;
        this.r = new Random();

        // Generating a list of numbers from 0 to 10 (that is the total number of facts)
        factNumbers = new ArrayList<Integer>();
        for(int i = 0; i < 11; i++) {
            factNumbers.add(i);
        }

        // Shuffling order of numbers
        Collections.shuffle(factNumbers);
    }

    public void generateNewFact(long time){
        // Checking if all the facts have been used
        if (factNumbers.size() == 0) {
            factId = 0;
        }

        // Getting first random fact
        factId = factNumbers.get(0);
        factNumbers.remove(0);

        // Language fact
        if (factId == 0) {
            long hours = TimeUnit.MILLISECONDS.toHours(time);
            int percentageProficiency = (int) ((hours * 100) / 1100);
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
            String bookName = randomBook.split("\\|")[0];
            double wordsInBook = Double.parseDouble(randomBook.split("\\|")[1]);
            double timesBookRead = wordsRead / wordsInBook;
            factString = "read '" + bookName + "' " + (int)timesBookRead + " times";
        }
        // Money earned minimum wage
        else if (factId == 4) {
            long hours = TimeUnit.MILLISECONDS.toHours(time);
            factString = "earned $" + hours*10.25 + " in California minimum wage";
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
            factString = "prepared " + (int)mealsPrepared + " full meals";
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
            String[] mountains = context.getResources().getStringArray(R.array.mountains);
            String randomMountains = mountains[r.nextInt(mountains.length - 1)];
            String mountainName = randomMountains.split("\\|")[0];
            double daysToClimb = Double.parseDouble(randomMountains.split("\\|")[1]);
            double timesToClimb = days / daysToClimb;
            factString = "climbed " + mountainName + " " + timesToClimb + " times";
        }
        // Pizza cooked
        else if (factId == 10) {
            long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
            factString = "baked " + minutes / 12 + " tasty pizzas";
        }
    }

    public int getFactDrawable() {
        TypedArray factsImageDrawables = context.getResources().obtainTypedArray(R.array.factsImagePath);
        return factsImageDrawables.getResourceId(factId, -1);
    }

    public String getFactString() {
        return factString;
    }


}


