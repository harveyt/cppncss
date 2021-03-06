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

package cppstyle;

import java.io.PrintStream;

/**
 * Implements a text logger.
 *
 * @author Mathieu Champlon
 */
public final class AsciiResultOutput implements ResultOutput
{
    private final PrintStream stream;
    private String filename;

    /**
     * Create an AsciiCheckListener.
     *
     * @param stream the output stream
     */
    public AsciiResultOutput( final PrintStream stream )
    {
        this.stream = stream;
    }

    /**
     * {@inheritDoc}
     */
    public void fail( final String reason, final int start, final int end )
    {
        if( start == end )
            fail( reason, start );
        else
            stream.println( filename + "(" + start + "-" + end + "): " + reason );
    }

    /**
     * {@inheritDoc}
     */
    public void fail( final String reason, final int line )
    {
        stream.println( filename + "(" + line + "): " + reason );
    }

    /**
     * {@inheritDoc}
     */
    public void fail( final String reason )
    {
        stream.println( filename + ": " + reason );
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
    }
}
