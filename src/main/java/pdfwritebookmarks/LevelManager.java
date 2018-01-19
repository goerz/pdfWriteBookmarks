/*
 * LevelManager.java
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
import org.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;

public class LevelManager {
    
    /** Creates a new instance of LevelManager */
    public LevelManager() {
        levelstack.push(null);
    }
    
    private int currentlevel = 0;
    
    private java.util.Stack<PDOutlineItem> levelstack = new java.util.Stack<PDOutlineItem>();
    
    
    public PDOutlineItem register(PDOutlineItem item, int level){
        // adds a new bookmark to the manager at level, and returns the
        // parent of the new bookmark
        Object garbage = null; // used to pop to nirvana
        if (level <= currentlevel){
            //remove all items that are not an ancestor of the current item
            while (level < currentlevel + 1){
                garbage = levelstack.pop();
                currentlevel--;
            }
            currentlevel = level;
        } else {
            currentlevel++;
        }
        PDOutlineItem parent = levelstack.empty() ? null : levelstack.peek();
        levelstack.push(item);
        return parent;
    }
    
}
