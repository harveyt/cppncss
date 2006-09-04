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

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

/**
 * Provides code measurement for C++.
 * 
 * @author Mathieu Champlon
 */
public class CppNcss implements CcnObserver
{
    private final Vector<CcnFunction> ccnResult;
    private final Comparator<CcnFunction> comparator;
    private final int THRESHOLD = 30;

    private CppNcss()
    {
        ccnResult = new Vector<CcnFunction>();
        comparator = new Comparator<CcnFunction>()
        {
            public int compare( CcnFunction o1, CcnFunction o2 )
            {
                return o1.compare( o2 );
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    public void notify( final String function, final int count )
    {
        final CcnFunction ccnFunction = new CcnFunction( function, count );
        ccnResult.add( ccnFunction );
        Collections.sort( ccnResult, comparator );
        if( ccnResult.size() > THRESHOLD )
            ccnResult.remove( ccnResult.size() - 1 );
    }

    private void display()
    {
        System.out.println( "CCN" );
        final Iterator<CcnFunction> iterator = ccnResult.iterator();
        while( iterator.hasNext() )
            System.out.println( iterator.next().toString() );
    }

    public static void main( final String[] args ) throws IOException
    {
        final CppNcss cppNcss = new CppNcss();
        new Analyzer( args ).parse( new CcnVisitor( cppNcss ) );
        cppNcss.display();
    }
}
