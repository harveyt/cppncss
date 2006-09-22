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

package cppncss;

import java.util.Vector;

/**
 * Stores a result measurement.
 *
 * @author Mathieu Champlon
 */
public final class Measurement implements Comparable
{
    private final String item;
    private final int line;
    private final int count;
    private final Vector<Integer> counts;
    private final String filename;

    /**
     * Create a measurement.
     *
     * @param item the name of the measured item
     * @param filename the name of the file containing the item
     * @param line the location of the item within the file
     * @param count the value of the measurement
     */
    public Measurement( final String item, final String filename, final int line, final int count )
    {
        if( item == null )
            throw new IllegalArgumentException( "argument 'item' is null" );
        if( line < 0 )
            throw new IllegalArgumentException( "argument 'line' is < 0" );
        if( count < 0 )
            throw new IllegalArgumentException( "argument 'count' is < 0" );
        this.item = item;
        this.line = line;
        this.filename = filename;
        this.count = count;
        counts = new Vector<Integer>();
    }

    /**
     * Add a measurement value to the recorded values.
     * <p>
     * If the item name does not match the measurement the value is not recorded.
     *
     * @param item the item name
     * @param filename the file name of the item
     * @param line the location of the item
     * @param count the measurement to record
     * @return whether the measurement has been added or not
     */
    public boolean update( final String item, final String filename, final int line, final int count )
    {
        if( !matches( item, filename, line ) )
            return false;
        counts.add( count );
        return true;
    }

    /**
     * Accept a visitor.
     *
     * @param visitor the visitor
     */
    public void accept( final MeasurementVisitor visitor )
    {
        visitor.visit( count, toString() );
        for( int index = 0; index < counts.size(); ++index )
            visitor.visit( counts.get( index ), toString() );
    }

    /**
     * {@inheritDoc}
     */
    public String toString()
    {
        if( filename == null )
            return item;
        return item + " at " + filename + ":" + line;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( final Object object )
    {
        final Measurement measurement = (Measurement)object;
        if( matches( measurement.item, measurement.filename, measurement.line ) )
            return 0;
        final int delta = (measurement).count - count;
        if( delta == 0 )
            return 1;
        return delta;
    }

    private boolean matches( final String item, final String filename, final int line )
    {
        if( !this.item.equals( item ) || this.line != line )
            return false;
        if( this.filename == null )
            return filename == null;
        return this.filename.equals( filename );
    }
}
