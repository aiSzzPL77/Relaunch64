/*
 * Copyright (C) 2014 Luedeke
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.relaunch64.popelganda.Editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Luedeke
 */
public class SectionExtractor {
    public static LinkedHashMap getSections(String source, String compilerComment) {
        // prepare return values
        LinkedHashMap<Integer, String> sectionValues = new LinkedHashMap<>();
        // init vars
        int lineNumber = 0;
        String line;
        // go if not null
        if (source!=null) {
            // create buffered reader, needed for line number reader
            BufferedReader br = new BufferedReader(new StringReader(source));
            LineNumberReader lineReader = new LineNumberReader(br);
            // Section-pattern is a comment line with "@<section description>@"
            Pattern p = Pattern.compile("@(.*?)@");
            Matcher m = p.matcher("");
            // read line by line
            try {
                while ((line = lineReader.readLine())!=null) {
                    // increase line counter
                    lineNumber++;
                    //reset the input (matcher)
                    m.reset(line); 
                    line = line.trim();
                    // check if line is a comment line and contains section pattern
                    if (line.startsWith(compilerComment) && m.find()) {
                        // if yes, add to return value
                        sectionValues.put(lineNumber, m.group(1));
                    }
                }
            }
            catch (IOException ex) {
            }
        }
        return sectionValues;
    }
    public static ArrayList getSectionNames(String source, String compilerComment) {
        // init return value
        ArrayList<String> retval = new ArrayList<>();
        // retrieve sections
        LinkedHashMap<Integer, String> map = getSections(source, compilerComment);
        // check for valid value
        if (map!=null && !map.isEmpty()) {
            // retrieve only string values of sections
            Collection<String> c = map.values();
            // create iterator
            Iterator<String> i = c.iterator();
            // add all ssction names to return value
            while(i.hasNext()) retval.add(i.next());
            // return result
            return retval;
        }
        return null;
    }
    public static ArrayList getSectionLineNumbers(String source, String compilerComment) {
        // init return value
        ArrayList<Integer> retval = new ArrayList<>();
        // retrieve sections
        LinkedHashMap<Integer, String> map = getSections(source, compilerComment);
        // check for valid value
        if (map!=null && !map.isEmpty()) {
            // retrieve only string values of sections
            Set<Integer> ks = map.keySet();
            // create iterator
            Iterator<Integer> i = ks.iterator();
            // add all ssction names to return value
            while(i.hasNext()) retval.add(i.next());
            // return result
            return retval;
        }
        return null;
    }
    
}