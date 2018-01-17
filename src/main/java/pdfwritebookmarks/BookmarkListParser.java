/*
 * BookmarkList.java
 *
 * Created on March 21, 2006, 7:11 PM
 *
 * (c) 2006 Michael Goerz <goerz@physik.fu-berlin.de>
 * http://www.physik.fu-berlin.de/~goerz/programme_en.html
 * v1.0
 *
 */

/*    This file is part of pdfWriteBookmarks
 *
 *    pdfWriteBookmarks is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    pdfWriteBookmarks is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with pdfWriteBookmarks; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *    http://www.gnu.org/copyleft/gpl.html
 */

/*
 * pdfWriteBookmarks uses the PDFBox library
 * http://www.pdfbox.org/
 */


package pdfwritebookmarks;
import java.io.*;


public class BookmarkListParser {
    
    /** Parses a bookmark file */
    public static Iterable<BookmarkItem> parseBookmarks(String inputfile) {
        // Parses the Inputfile and creates a list of the bookmarks in it
        // The format is like that in the output of pdftk:
        // [...]
        // BookmarkTitle: text
        // BookmarkLevel: 2
        // BookmarkPageNumber: 20
        // [...]
        //
        // Alternatively, you can use the short form
        //      Bookmark Title :: Pagenumber
        // The indentation controls the level, exactly four spaces indentation
        // per level
        java.util.List<BookmarkItem> bookmarks = new java.util.ArrayList<BookmarkItem>();
        String line = "";
        String title = "";
        int page = 0;
        int level =0;
        try{
            //BufferedReader in = new BufferedReader(new FileReader(inputfile));
            FileInputStream fis = new FileInputStream(inputfile);
            
            BufferedReader in = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            
            while(line != null) {
               line = in.readLine();
               if (line != null){
                   
                   // TODO: translate the encoding pdftk uses for non-ascii symbols
                   
                    // First, check for the short bookmark format
                   if (line.matches( "(.*)::([ 0-9]*)$" )){
                       // Get the indentation
                       level = 1;
                       while (line.startsWith("    ")){
                           level++;
                           line = line.replaceFirst( "[ ]{4}", "" );
                       }
                       // Get Title and Pagenumber
                       line = line.trim();
                       title = line.replaceFirst("::([ 0-9]*)$", "");
                       try{
                            page = Integer.parseInt( line.substring(line.lastIndexOf(":")+1).trim() );
                       }
                       catch(NumberFormatException e){
                           System.out.println(e.getMessage());
                           System.out.println("ERROR: Could not parse bookmarks");
                           System.exit(1);
                       }
                       bookmarks.add(new BookmarkItem(title, page, level));
                       title = "";
                       level = 0;
                       page = 0;
                   }
                   
                   // Now we check for the long threeline format
                   line = line.trim();
                   if (line.indexOf("BookmarkTitle: ") == 0) {
                       title = line.replaceFirst("BookmarkTitle: ","");
                       title.trim();
                   }
                   if (line.indexOf("BookmarkLevel: ") == 0) {
                       try{
                            level = Integer.parseInt(line.replaceFirst("BookmarkLevel: ",""));
                       }
                       catch(NumberFormatException e){
                           System.out.println(e.getMessage());
                           System.out.println("ERROR: Could not parse bookmarks");
                           System.exit(1);
                       }
                   }
                   if (line.indexOf("BookmarkPageNumber: ") == 0) {
                       try{
                            page = Integer.parseInt(line.replaceFirst("BookmarkPageNumber: ",""));
                            if ((title != "") && (level != 0) && (page != 0)){
                                bookmarks.add(new BookmarkItem(title, page, level));
                                title = "";
                                level = 0;
                                page = 0;
                            }
                       }
                       catch(NumberFormatException e){
                           System.out.println(e.getMessage());
                           System.out.println("ERROR: Could not parse bookmarks");
                           System.exit(1);
                       }
                   }
                   
                }
            }
        }
        catch(IOException e){
            System.out.println("Could not read from file");
            System.exit(1);
        }
        return bookmarks;
    }
}
