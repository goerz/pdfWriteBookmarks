/*
 * Main.java
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

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.pdmodel.PDPage;
import org.pdfbox.pdmodel.interactive.documentnavigation.destination.PDPageFitWidthDestination;
import org.pdfbox.pdmodel.interactive.documentnavigation.outline.PDDocumentOutline;
import org.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

import java.util.List;

public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    public static void main(String[] args) {
        // Parse the Commandline
        String bookmarkfile = "";
        String pdffile = "";
        String outfile = "";
        if (args.length >= 2){
            pdffile = args[0];
            bookmarkfile = args[1];
            if (args.length == 3){
                outfile = args[2];
            } else {
                outfile = pdffile;
            }
        } else {
            usage();
            System.exit(0);
        }
        // get the bookmarks from file
        BookmarkList bookmarklist = new BookmarkList(bookmarkfile);
        // open the pdf file
        PDDocument document = null;
        try{
            document = PDDocument.load( pdffile );
            if( document.isEncrypted() ){
                System.err.println("Cannot add bookmarks to encrypted document.");
                System.exit(1);
            }
            
            List pages = document.getDocumentCatalog().getAllPages();
            
            // attach new outline
            PDDocumentOutline outline =  new PDDocumentOutline();
            document.getDocumentCatalog().setDocumentOutline( outline );
            
            // use the LevelManager
            LevelManager manager = new LevelManager();
            
            //create all the bookmarks
            for( int i=0; i < bookmarklist.getNumberOfBookmarks(); i++ ){
                PDOutlineItem bookmark = new PDOutlineItem();
                bookmark.setTitle(bookmarklist.getTitle(i));
                PDPage page = (PDPage)pages.get(bookmarklist.getPageNumber(i)-1);// counting in list starts at zero!
                bookmark.setDestination(page);
                PDOutlineItem parent = manager.register(bookmark, bookmarklist.getLevel(i));
                if (parent == null){
                    outline.appendChild(bookmark);
                } else {
                    parent.appendChild(bookmark);
                }
            }
            
            // save the document
            document.save(outfile);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.exit(1);
            
        }
        finally{
            if( document != null )
            {
                try{
                    document.close();
                }
                catch(Exception e2){
                    System.out.println(e2.getMessage());
                    System.exit(1);
                }
            }
        }
    }
    
    

    private static void usage(){
        System.out.println("pdfWriteBookmarks v1.0");
        System.out.println("(c) 2006 Michael Goerz\n");
        System.out.println("This program is licensed under the GPL.");
        System.out.println("See http://www.gnu.org/copyleft/gpl.html for details\n");
        System.out.println("usage:");
        System.out.println("java -jar pdfWriteBookmarks.jar inputfile.pdf bookmarks.txt [output.pdf]\n");
        System.out.println("The format of bookmarks.txt must be like the output of pdftk:");
        System.out.println("pdftk file.pdf dump_data\n");
        System.out.println("[...]");
        System.out.println("BookmarkTitle: text");
        System.out.println("BookmarkLevel: 2");
        System.out.println("BookmarkPageNumber: 20");
        System.out.println("[...]\n");
        System.out.println("Alternatively, you can use a shorter one-line format:");
        System.out.println("    Bookmark Title :: pagenumber");
        System.out.println("The BookmarkLevel is expressed by the indentation. There have to be");
        System.out.println("exactly four spaces indentation per level");
    }
    
    
}
