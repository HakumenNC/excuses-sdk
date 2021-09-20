/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.adriens.excuses.sdk;

import com.opencsv.bean.CsvToBeanBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author 3004SAL
 */
public class Excuses {

    public static final String DATA_FILE = "excuses.csv";

    public List<Excuse> getAll() throws Exception {
        List<Excuse> out;
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(DATA_FILE).getFile());

        out = new CsvToBeanBuilder(new FileReader(file))
                .withType(Excuse.class)
                .build()
                .parse();
        //out.forEach(System.out::println);
        return out;
    }

    //TODO switch to hashSet
    public List<String> getCategories() throws Exception {
        HashSet<String> out = new HashSet<String>();
        Iterator<Excuse> catsIter = getAll().iterator();
        while (catsIter.hasNext()) {
            out.add(catsIter.next().getCategory());
        }
        return new ArrayList<>(out);
    }

    //TODO  inject to HashSet then filter and return
    public List<Excuse> getByCategory(String cat) throws Exception {
        System.out.println("Get excuses for <" + cat + "> :");
        Iterator<Excuse> excIter = getAll().iterator();
        ArrayList<Excuse> out = new ArrayList<Excuse>();
        while (excIter.hasNext()) {
            Excuse ex = excIter.next();
            if (ex.getCategory().equalsIgnoreCase(cat)) {
                out.add(ex);
                System.out.println(ex);
            }
        }
        return out;
    }

    // implementer excuseNotFoundException
    // implementer categoryNotFoundException
    // get a totally random excuse (no matter the category)
    // get a random excuse from a given category
    public static void main(String[] args) {
        try {
            Excuses excuses = new Excuses();
            //tes collègue te gonflent avec le sport ?
            List<Excuse> exc = excuses.getByCategory("Sport");
            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(1);
        }
    }
}
