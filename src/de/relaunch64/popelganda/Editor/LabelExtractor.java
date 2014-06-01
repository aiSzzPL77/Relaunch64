/*
 * Relaunch64 - A Java cross-development IDE for C64 machine language coding.
 * Copyright (C) 2001-2014 by Daniel Lüdecke (http://www.danielluedecke.de)
 * 
 * Homepage: http://www.popelganda.de
 * 
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation; either version 3 of 
 * the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program;
 * if not, see <http://www.gnu.org/licenses/>.
 * 
 * 
 * Dieses Programm ist freie Software. Sie können es unter den Bedingungen der GNU
 * General Public License, wie von der Free Software Foundation veröffentlicht, weitergeben
 * und/oder modifizieren, entweder gemäß Version 3 der Lizenz oder (wenn Sie möchten)
 * jeder späteren Version.
 * 
 * Die Veröffentlichung dieses Programms erfolgt in der Hoffnung, daß es Ihnen von Nutzen sein 
 * wird, aber OHNE IRGENDEINE GARANTIE, sogar ohne die implizite Garantie der MARKTREIFE oder 
 * der VERWENDBARKEIT FÜR EINEN BESTIMMTEN ZWECK. Details finden Sie in der 
 * GNU General Public License.
 * 
 * Sie sollten ein Exemplar der GNU General Public License zusammen mit diesem Programm 
 * erhalten haben. Falls nicht, siehe <http://www.gnu.org/licenses/>.
 */

package de.relaunch64.popelganda.Editor;

import de.relaunch64.popelganda.util.ConstantsR64;
import de.relaunch64.popelganda.assemblers.Assembler;
import java.io.BufferedReader;
import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Daniel Lüdecke
 */
public class LabelExtractor {
    /**
     * Retrieves a list of all labels from the current activated source code
     * (see {@link #getActiveSourceCode()}) that start with the currently
     * typed characters at the caret position (usually passed as parameter
     * {@code subWord}.
     * 
     * @param subWord A string which filters the list of labels. Only labels that start with
     * {@code subWord} will
     * @param source
     * @param assembler
     * @param lineNumber
     * 
     * @return An object array of sorted labels, where only those labels are returned that start with {@code subWord}.
     */
    public static ArrayList<String> getSubNames(String subWord, ArrayList<String> labels) {
        // check for valid values
        if (null==labels || labels.isEmpty()) return new ArrayList<String>();
        // remove all labels that do not start with already typed chars
        if (!subWord.isEmpty()) {
            for (int i=labels.size()-1; i>=0; i--) {
                if (!labels.get(i).startsWith(subWord)) labels.remove(i);
            }
        }
        // return as object array
        return labels;
    }
    /**
     * Retrieves a list of all labels from the current activated source code
     * (see {@link #getActiveSourceCode()}).
     * 
     * @param sortList If {@code true}, labels are sorted in alphabetical order.
     * @param source
     * @param assembler
     * @param lineNumber
     * @return An array list of all label names from the source code.
     */
    public static ArrayList<String> getNames(LinkedHashMap<String, Integer> map) {
        return new ArrayList<String>(map.keySet());
    }
    /**
     * This method retrieves all labels from the current activated source code
     * (see {@link #getActiveSourceCode()}) and returns both line number of label
     * and label name as linked HashMap.
     * 
     * @param source
     * @param assembler
     * @param lineNumber
     * @return All labels with their line numbers, or {@code null} if there are no labels
     * in the source code.
     */
    public static LinkedHashMap getLabels(String source, Assembler assembler, int lineNumber) {
        StringReader sr = new StringReader(source);
        BufferedReader br = new BufferedReader(sr);
        LineNumberReader lineReader = new LineNumberReader(br);
        return assembler.getLabels(lineReader, lineNumber).labels;
    }

    public static LinkedHashMap getFunctions(String source, Assembler assembler, int lineNumber) {
        StringReader sr = new StringReader(source);
        BufferedReader br = new BufferedReader(sr);
        LineNumberReader lineReader = new LineNumberReader(br);
        return assembler.getLabels(lineReader, lineNumber).functions;
    }

    public static LinkedHashMap getMacros(String source, Assembler assembler, int lineNumber) {
        StringReader sr = new StringReader(source);
        BufferedReader br = new BufferedReader(sr);
        LineNumberReader lineReader = new LineNumberReader(br);
        return assembler.getLabels(lineReader, lineNumber).macros;
    }

    public static ArrayList<Integer> getLineNumbers(LinkedHashMap<String, Integer> map) {
        return new ArrayList<Integer>(map.values());
    }
}
