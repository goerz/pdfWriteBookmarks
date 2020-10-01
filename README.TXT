/*    pdfWriteBookmarks
 *    (c) 2006 Michael Goerz <goerz@physik.fu-berlin.de>
 *    http://www.physik.fu-berlin.de/~goerz/
 *
 *    pdfWriteBookmarks is free software; you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation; either version 2 of the License, or
 *    (at your option) any later version.
 *
 *    PdfWriteBookmarks is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with PdfWriteBookmarks; if not, write to the Free Software
 *    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *    http://www.gnu.org/copyleft/gpl.html
 */

/*
 * PdfWriteBookmarks uses the PDFBox library
 * http://www.pdfbox.org/
 */


PdfWriteBookmarks is build with [Maven](http://maven.apache.org/). You can
build it with the command

    ./mvnw package

You will find the actual source code files in the subfolder src/pdfwritebookmarks/

The executable jar-file is in the subfolder dist/. Read /dist/README.txt

You will probably want to put this folder in a convenient location and write a simple 
batch file to wrap around the jar-file.
