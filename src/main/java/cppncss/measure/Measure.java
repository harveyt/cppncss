/**
 * Redistribution  and use  in source  and binary  forms, with  or without
 * modification, are permitted provided  that the following conditions are
 * met :
 *
 * . Redistributions  of  source  code  must  retain  the  above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * . Redistributions in  binary form  must reproduce  the above  copyright
 *   notice, this list of conditions  and the following disclaimer in  the
 *   documentation and/or other materials provided with the distribution.
 *
 * . The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS  PROVIDED BY THE  AUTHOR ``AS IS''  AND ANY EXPRESS  OR
 * IMPLIED  WARRANTIES,  INCLUDING,  BUT   NOT  LIMITED  TO,  THE   IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND  FITNESS FOR A PARTICULAR  PURPOSE ARE
 * DISCLAIMED.  IN NO  EVENT SHALL  THE AUTHOR  BE LIABLE  FOR ANY  DIRECT,
 * INDIRECT,  INCIDENTAL,  SPECIAL,  EXEMPLARY,  OR  CONSEQUENTIAL  DAMAGES
 * (INCLUDING,  BUT  NOT LIMITED  TO,  PROCUREMENT OF  SUBSTITUTE  GOODS OR
 * SERVICES;  LOSS  OF USE,  DATA,  OR PROFITS;  OR  BUSINESS INTERRUPTION)
 * HOWEVER CAUSED  AND ON  ANY THEORY  OF LIABILITY,  WHETHER IN  CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY  WAY  OUT OF  THE  USE OF  THIS  SOFTWARE, EVEN  IF  ADVISED OF  THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package cppncss.measure;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores a result measure.
 *
 * @author Mathieu Champlon
 */
public final class Measure implements Comparable<Measure>
{
    private final List<Integer> counts = new ArrayList<Integer>();
    private final String item;
    private final String filename;
    private final int line;

    /**
     * Create a measure.
     *
     * @param item the name of the measured item
     * @param filename the name of the file containing the item
     * @param line the location of the item within the file
     * @param count the value of the measure
     */
    public Measure( final String item, final String filename, final int line, final int count )
    {
        if( item == null )
            throw new IllegalArgumentException( "argument 'item' is null" );
        if( filename == null )
            throw new IllegalArgumentException( "argument 'filename' is null" );
        if( line < 0 )
            throw new IllegalArgumentException( "argument 'line' is < 0" );
        counts.add( count );
        this.item = item;
        this.filename = filename;
        this.line = line;
    }

    /**
     * Add a measure value to the recorded values.
     * <p>
     * If the item name does not match the measure the value is not recorded.
     *
     * @param item the item name
     * @param filename the file name of the item
     * @param line the location of the item
     * @param count the measure to record
     */
    public void update( final String item, final String filename, final int line, final int count )
    {
        if( matches( item, filename, line ) )
            counts.add( count );
    }

    /**
     * Accept a visitor.
     *
     * @param observer a measure observer
     */
    public void accept( final MeasureObserver observer )
    {
        for( Integer count : counts )
            observer.notify( toString(), count );
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        if( filename.equals( item ) )
            return item;
        return item + " at " + filename + ":" + line;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( final Measure measure )
    {
        if( matches( measure.item, measure.filename, measure.line ) )
            return 0;
        final int delta = measure.counts.get( 0 ) - counts.get( 0 );
        if( delta == 0 )
            return 1;
        return delta;
    }

    private boolean matches( final String item, final String filename, final int line )
    {
        return this.item.equals( item ) && this.filename.equals( filename ) && this.line == line;
    }
}
