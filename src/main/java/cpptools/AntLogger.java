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

package cpptools;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectComponent;

/**
 * Implements an event handler for logging to Ant.
 *
 * @author Mathieu Champlon
 */
public final class AntLogger extends AbstractLogger
{
    private final ProjectComponent project;

    /**
     * Create an event output.
     *
     * @param project the project instance
     */
    public AntLogger( final ProjectComponent project )
    {
        this.project = project;
    }

    /**
     * {@inheritDoc}
     */
    public void error( final String filename, final Throwable throwable, final String reason )
    {
        project.log( throwable.getMessage(), Project.MSG_VERBOSE );
        project.log( "Skipping " + filename + " : " + reason, Project.MSG_INFO );
    }

    /**
     * {@inheritDoc}
     */
    public void changed( final String filename )
    {
        project.log( "Parsing " + filename, Project.MSG_VERBOSE );
    }

    /**
     * {@inheritDoc}
     */
    protected void log( final String message )
    {
        project.log( message, Project.MSG_INFO );
    }
}
