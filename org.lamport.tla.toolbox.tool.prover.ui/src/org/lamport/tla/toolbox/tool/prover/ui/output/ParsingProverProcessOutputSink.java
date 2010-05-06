package org.lamport.tla.toolbox.tool.prover.ui.output;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.lamport.tla.toolbox.Activator;
import org.lamport.tla.toolbox.tool.prover.output.IProverProcessOutputSink;

/**
 * This class receives streaming output from the TLAPM
 * and sends it to a {@link TagBasedTLAPMOutputIncrementalParser2}
 * for parsing.
 * 
 * This class receives output by registering at the extension point
 * org.lamport.tla.toolbox.tool.prover.processOutputSink.
 * 
 * @author Daniel Ricketts
 *
 */
public class ParsingProverProcessOutputSink implements IProverProcessOutputSink
{

    private TagBasedTLAPMOutputIncrementalParser2 parser;
    private String name;

    /**
     * Sends text to the parser.
     */
    public void appendText(String text)
    {
        try
        {
            parser.addIncrement(text);

        } catch (BadLocationException e)
        {
            Activator.logError("Error parsing the TLAPM output stream for " + name, e);
        }
    }

    /**
     * Initializes a parser for the stream of text that will be sent
     * to this sink.
     * 
     * For now, we assume that the String modulePathString was generated by calling
     * {@link IPath#toPortableString()} and so the original {@link IPath} can
     * be retrieved by calling {@link Path#fromPortableString(String)}.
     */
    public void initializeSink(String modulePathString, int sinkType)
    {
        this.parser = new TagBasedTLAPMOutputIncrementalParser2(Path.fromPortableString(modulePathString));
        this.name = modulePathString;
    }

    /**
     * Notifies the parser that
     * no more text is to be sent.
     */
    public void processFinished()
    {
        parser.onDone();
    }

}