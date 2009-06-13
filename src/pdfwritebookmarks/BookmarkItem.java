/*
 * BookmarkItem.java
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


public class BookmarkItem {
    
    /** Creates a new instance of BookmarkItem */
    public BookmarkItem(String title, int pagenumber, int level) {
        this.setTitle(title);
        this.setPageNumber(pagenumber);
        this.setLevel(level);
    }

    
    private String title;
    private int pagenumber;
    private int level;
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String t){
        if (t.length()<=255){
            title = t;
        } else {
            System.out.println("Bookmark Title too long");
            System.exit( 1 );
            title = "";
        }
    }
    
    public int getPageNumber(){
        return pagenumber;
    }
    
    public void setPageNumber(int p){
        if (p >= 0) {
            pagenumber = p;
        } else {
            System.out.println("Pagenumber must be greater than zero");
            System.exit( 1 );
            pagenumber = 0;
        }
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setLevel(int l){
        if (l >= 0) {
            level = l;
        } else {
            System.out.println("Level must be greater than zero");
            System.exit( 1 );
            level = 0;
        }
    }
}
