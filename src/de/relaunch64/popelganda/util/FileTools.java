/*
 * Relaunch64 - A Java Crossassembler for C64 machine language coding.
 * Copyright (C) 2001-2013 by Daniel Lüdecke (http://www.danielluedecke.de)
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

package de.relaunch64.popelganda.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Daniel Luedecke
 */
public class FileTools {
    /**
     * This method creates and shows a file chooser, depending on the operating system. In case the os is Windows or Linux,
     * the standard Swing-JFileChooser will be opened. In case the os is Mac OS X, the old awt-dialog is used, which
     * looks more nativ.<br><br>
     * When the user chose a file, it will be returned, else {@code null} will be returned.
     *
     * @param parent the parent-frame of the file chooser
     * @param dlgmode<br>
     * - in case of Mac OS X: either {@code FileDialog.LOAD} or {@code FileDialog.SAVE}
     * - else: {@code JFileChooser.OPEN_DIALOG} or {@code JFileChooser.SAVE_DIALOG}
     * @param filemode<br>
     * - not important for Mac OS X.
     * - else: {@code JFileChooser.FILES_ONLY} or the other file-selection-mode-values
     * @param initdir the initial directory which can be set when the dialog is shown
     * @param initfile the initial file which can be selected when the dialog is shown
     * @param title the dialog's title
     * @param acceptedext the accepted file extensions that will be accepted, i.e. the files that are selectable
     * @param desc the description of which file types the extensions are
     * @return The chosen file, or {@code null} if dialog was cancelled
     */
    public static File chooseFile(java.awt.Frame parent, int dlgmode, int filemode, String initdir, String initfile, String title, final String[] acceptedext, final String desc) {
        // set current directory
        File curdir = (null==initdir)?null:new File(initdir);
        // get the title for the file dialog and use it as function parameter
        JFileChooser fc = createFileChooser(title,filemode,curdir,acceptedext,desc);
        // show the dialog
        int option = (JFileChooser.OPEN_DIALOG==dlgmode)?fc.showOpenDialog(parent):fc.showSaveDialog(parent);
        // if a file was chosen, set the filepath
        if (JFileChooser.APPROVE_OPTION == option) {
            // get the filepath...
            return fc.getSelectedFile();
        }
        return null;
    }
    /**
     * This merthod creates a FileChooser Dialog, initiates it (based on the passed parameters) and returns
     * a reference to the FileChooser Dialog.
     *
     * @param name the file dialog's title
     * @param filemode {@code JFileChooser.FILES_ONLY} or the other file-selection-mode-values (not important for Mac OS X)
     * @param curdir the initial directory which can be set when the dialog is shown
     * @param acceptedext the accepted file extensions that will be accepted, i.e. the files that are selectable
     * @param desc the description of which file types the extensions are
     * @return a reference to a new created file chooser
     */
    public static JFileChooser createFileChooser(String name, int filemode, File curdir, final String[] acceptedext, final String desc) {
        JFileChooser fc = new JFileChooser();
        // set dialog's title
        fc.setDialogTitle(name);
        // restrict all files as choosable
        fc.setAcceptAllFileFilterUsed(false);
        // init the filechoose with an existing filepath
        fc.setCurrentDirectory(curdir);
        // only directories should be selected
        fc.setFileSelectionMode(filemode);
        if (null==acceptedext || acceptedext.length<1) {
            fc.setAcceptAllFileFilterUsed(true);
        }
        else {
            // create a new file filter
            fc.setFileFilter(new FileFilter() {
                @Override public boolean accept(File f) {
                    // directories are always accepted
                    if (f.isDirectory()) {
                        return true;
                    }
                    // set file extensions in the file dialog according
                    // to user's choice of import type
                    // other accepted files are located in the resource map
                    boolean retval = false;
                    String fileext = "."+getFileExtension(f);
                    if (acceptedext.length>0) {
                        for (String ext : acceptedext) {
                            if (fileext.equals(ext.toLowerCase())) {
                                retval = true;
                            }
                        }
                    }
                    return retval;
                }

                @Override public String getDescription() {
                    // set file descripton in the file dialog according
                    // to user's choice of import type
                    return desc;
                }
            });
        }
        return fc;
    }
    /**
     * This method returns the file extension of a given file which is passed as parameter.
     * @param f the file which extension should be retrieved
     * @return the extension of the given file, <b>without</b> leading period (e.g. "jpg" is returned, not ".jpg")
     */
    public static String getFileExtension(File f) {
        // check for valid parameter
        if (null==f) {
            return "";
        }
        // init extension string
        String ext = null;
        // get filename
        String s = f.getName();
        // look for the position where the extension of the file starts
        int i = s.lastIndexOf(".");
        // retrieve extension
        if (i>0 &&  i<s.length()-1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
    /**
     * 
     * @param f
     * @return 
     */
    public static String getFilePath(File f) {
        if (f!=null && f.exists()) {
            String path = f.getAbsolutePath();
            return path.substring(0,path.lastIndexOf(File.separator));
        }
        return null;
    }
    /**
     * 
     * @param f
     * @param ext
     * @return 
     */
    public static File setFileExtension(File f, String ext) {
        // check for valid parameter
        if (null==f) {
            return f;
        }
        String s = f.toString();
        // look for the position where the extension of the file starts
        int i = s.lastIndexOf(".");
        // retrieve extension
        if (i>0 &&  i<s.length()-1) {
            return new File(s.substring(0, i+1)+ext);
        }
        return f;
    }
    /**
     * 
     * @param fp
     * @return 
     */
    public static boolean hasValidFileExtension(File fp) {
        // get file extension
        String ext = getFileExtension(fp);
        // check for valid value
        if (null==ext || ext.isEmpty()) {
            return false;
        }
        // add period, since the above method returns extension without period
        ext = "."+ext;
        // loop all valid extensions
        for (String extensions : ConstantsR64.FILE_EXTENSIONS) {
            // check each extension
            if (ext.equalsIgnoreCase(extensions)) {
                return true;
            }
        }
        return false;
    }
    public static boolean hasValidIncludeFileExtension(File fp) {
        // get file extension
        String ext = getFileExtension(fp);
        // check for valid value
        if (null==ext || ext.isEmpty()) {
            return false;
        }
        // add period, since the above method returns extension without period
        ext = "."+ext;
        // loop all valid extensions
        for (String extensions : ConstantsR64.FILE_EXTENSIONS_INCLUDES) {
            // check each extension
            if (ext.equalsIgnoreCase(extensions)) {
                return true;
            }
        }
        return false;
    }
    /**
     * This method returns the file name of a given file-path which is passed as parameter.
     * 
     * @param f the filepath of the file which file name should be retrieved
     * @return the name of the given file, excluding extension, or {@code null} if an error occured.
     */
    public static String getFileName(File f) {
        // check if we have any file
        if (f!=null) {
            // get filename
            String fname = f.getName();
            // find file-extension
            int extpos = fname.lastIndexOf(".");
            // set the filename as title
            if (extpos!=-1) {
                try {
                    // return file-name
                    return fname.substring(0,extpos);
                }
                catch (IndexOutOfBoundsException ex) {
                    return null;
                }
            }
        }
        return null;
    }
    /**
     * 
     * @param filename
     * @return 
     */
    public static File createFilePath(String filename) {
        File sFile;
        if (!new File(System.getProperty("user.home")+java.io.File.separatorChar+".Relaunch64").exists()) {
            try {
                // first of all, we want to check for a subdirectory ".Zettelkasten" in the user's home-directory
                if (new File(System.getProperty("user.home")+java.io.File.separatorChar+".Relaunch64").mkdirs()) {
                    sFile = new File(System.getProperty("user.home")+java.io.File.separatorChar+".Relaunch64"+java.io.File.separatorChar+filename);
                }
            else {
                sFile = new File(System.getProperty("user.home")+java.io.File.separatorChar+filename);
            }
            }
            catch (SecurityException ex) {
                sFile = null;
            }
        }
        else {
            sFile = new File(System.getProperty("user.home")+java.io.File.separatorChar+".Relaunch64"+java.io.File.separatorChar+filename);
        }
        // return result
        return sFile;
    }    
    /**
     * Returns the relative path from the source file {@code source} in relation to
     * the destination file {@code dest}.
     * 
     * @param source the source file from which the relative path should be computed.
     * @param dest the destination file.
     * @return the relative path from {@code dest} in relation to {@code source}.
     */
    public static String getRelativePath(File source, File dest) {
        try {
            return Paths.get(source.getAbsolutePath()).relativize(Paths.get(dest.getAbsolutePath())).toString();
        }
        catch (IllegalArgumentException ex) {
        }
        return null;
    }    
    /**
     * Reads a binary file and returns its content as byte array.
     * 
     * @param sourceFile FilePath to the binary file
     * @return a byte array with all byte values from the
     * file, or {@code null} if an error occured.
     */
    public static byte[] readBinaryFile(File sourceFile) {
        return readBinaryFile(sourceFile, 0, -1);
    }
    /**
     * Reads a binary file and returns its content as byte array.
     * 
     * @param sourceFile FilePath to the binary file
     * @param startOffset start offset for the first byte to read from file
     * @param endOffset end offset, the last byte that should be read from file
     * @return a byte array with all byte values from the
     * file, or {@code null} if an error occured.
     */
    public static byte[] readBinaryFile(File sourceFile, int startOffset, int endOffset) {
        // check for valid file
        if (null==sourceFile || !sourceFile.exists()) return null;
        FileInputStream in = null;
        byte[] retVal = null;
        byte[] buffer;
        try {
            in = new FileInputStream(sourceFile);
            // get file length
            long fileLength = sourceFile.length();
            // create byte buffer for content
            buffer = new byte[(int)fileLength];
            int counter = 0;
            // read bytes until EOF
            for(int i; (i=in.read())!=-1;) {
                buffer[counter++] = (byte)i;
            }
            // check if user wants only specific offset/part of the file
            if (endOffset!=-1 || startOffset>0) {
                // check if length is EOF
                if (-1==endOffset) endOffset = (int)fileLength;
                // create array with length of sub-data
                retVal = new byte[endOffset-startOffset];
                // copy bytes from complete buffer
                counter = 0;
                for (int i=startOffset; i<endOffset; i++) {
                    retVal[counter++] = buffer[i];
                }
            }
        }
        catch (IOException | SecurityException ex) {
            ConstantsR64.r64logger.log(Level.SEVERE,ex.getLocalizedMessage());
            return null;
        }
        finally {
            try {
                // close file input stream
                if (in!=null) in.close();
            }
            catch (IOException ex) {
                return null;
            }
        }
        // return offset buffer or buffer, depending on which one has data
        return (retVal!=null) ? retVal : buffer;
    }
}