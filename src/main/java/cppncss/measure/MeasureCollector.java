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
import java.util.TreeSet;
import cpptools.Options;

/**
 * Collects measures.
 * <p>
 * The results are sorted according to the value of the first measure.
 * <p>
 * The different measures for a given item must be recorded one after another.
 *
 * @author Mathieu Champlon
 */
public final class MeasureCollector implements Collector
{
    private final int threshold;
    private final TreeSet<Measure> result = new TreeSet<Measure>();
    private final List<String> labels = new ArrayList<String>();
    private final MeasureObserver observer;
    private String index;
    private String filename;

    /**
     * Create a measure collector.
     *
     * @param options the options
     * @param observer an observer to be notified of the results
     */
    public MeasureCollector( final Options options, final MeasureObserver observer )
    {
        if( observer == null )
            throw new IllegalArgumentException( "argument 'observer' is null" );
        this.threshold = getThreshold( options );
        this.observer = observer;
    }

    private int getThreshold( final Options options )
    {
        if( !options.hasOption( "n" ) )
            return -1;
        final List<String> thresholds = options.getOptionPropertyValues( "n" );
        if( thresholds.size() > 1 )
            throw new IllegalArgumentException( "invalid multiple -n arguments" );
        final int value = Integer.parseInt( thresholds.get( 0 ) );
        if( value < 0 )
            throw new IllegalArgumentException( "invalid negative -n argument" );
        return value;
    }

    /**
     * {@inheritDoc}
     */
    public void notify( final String label, final String item, final int line, final int count )
    {
        if( !labels.contains( label ) )
            labels.add( label );
        if( index == null )
            index = label;
        if( index.equals( label ) )
            insert( item, line, count );
        else
            update( item, line, count );
    }

    private void update( final String item, final int line, final int count )
    {
        for( Measure measure : result )
            measure.update( item, filename, line, count );
    }

    private void insert( final String item, final int line, final int count )
    {
        result.add( new Measure( item, filename, line, count ) );
        if( threshold >= 0 && result.size() > threshold )
            result.remove( result.last() );
    }

    /**
     * {@inheritDoc}
     */
    public void changed( final String filename )
    {
        this.filename = filename;
    }

    /**
     * {@inheritDoc}
     */
    public void flush()
    {
        observer.notify( labels );
        for( Measure measure : result )
            measure.accept( observer );
    }
}
