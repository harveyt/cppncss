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
 *
 * $Id: $
 */

package cppncss;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Vector;
import tools.EasyMockTestCase;

/**
 * @author Mathieu Champlon
 */
public class AsciiResultOutputTest extends EasyMockTestCase
{
    private static final String LINE_SEPARATOR = System.getProperty( "line.separator" );
    private AsciiResultOutput output;
    private ByteArrayOutputStream stream;

    protected void setUp() throws Exception
    {
        stream = new ByteArrayOutputStream();
        output = new AsciiResultOutput( new PrintStream( stream ), "item" );
    }

    private Vector<String> makeLabels()
    {
        final Vector<String> labels = new Vector<String>();
        labels.add( "label 1" );
        labels.add( "label 2" );
        return labels;
    }

    public void testNotifyLabelsOutputsFormattedHeader()
    {
        output.notify( makeLabels() );
        assertEquals( LINE_SEPARATOR + "Nr. label 1 label 2 item" + LINE_SEPARATOR, stream.toString() );
    }

    public void testNotifyAllMeasurementsOutputsCountsAndItem()
    {
        output.notify( makeLabels() );
        stream.reset();
        output.notify( "item", 12 );
        output.notify( "item", 42 );
        assertEquals( "  1      12      42 item" + LINE_SEPARATOR, stream.toString() );
        stream.reset();
        output.notify( "another item", 7 );
        output.notify( "another item", 51 );
        assertEquals( "  2       7      51 another item" + LINE_SEPARATOR, stream.toString() );
    }

    public void testNotifySumOutputsCountsAndLabel()
    {
        output.notify( makeLabels() );
        stream.reset();
        output.notify( "label", 12l );
        assertEquals( "item label: 12" + LINE_SEPARATOR, stream.toString() );
    }

    public void testNotifyAverageOutputsCountsAndLabel()
    {
        output.notify( makeLabels() );
        stream.reset();
        output.notify( "label", 12f );
        assertEquals( "Average item label: 12.00" + LINE_SEPARATOR, stream.toString() );
    }
}