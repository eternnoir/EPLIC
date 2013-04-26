package pcpl.core.handlers;

import java.util.ArrayList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.debug.core.model.LineBreakpoint;
import org.eclipse.jdt.internal.debug.core.breakpoints.JavaLineBreakpoint;

import pcpl.core.breakpoint.BreakpointSetter;
import pcpl.core.breakpoint.FileParaviserUtils;
import pcpl.core.eventHandler.EventCenter;
import pcpl.core.mode.TraceMode;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SampleHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public SampleHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		/*IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		MessageDialog.openInformation(
				window.getShell(),
				"Core",
				"Hello, Eclipse world");*/
		//ArrayList<IFile> f = FileParaviserUtils.getAllFile("java");
		ArrayList<IResource> r =  FileParaviserUtils.getAllFilesInProject("java");
		
		BreakpointSetter.getInstance().setBreakpoint(r.get(0), 3);
		/*
		if(EventCenter.getInstance().getModeType() == 1){		//normal mode
			EventCenter.getInstance().setModeType(2);
			System.out.print("change Mode Type 2\n");
		}
		else if(EventCenter.getInstance().getModeType() == 2){//record mode
			EventCenter.getInstance().removeBreakPointListener(EventCenter.getInstance().getRecMode());
			//EventCenter.getInstance().setMode(EventCenter.getInstance().getNorMode());
			TraceMode t = new TraceMode();
			EventCenter.getInstance().setTraMode(t);
			EventCenter.getInstance().setModeType(3);
			System.out.print("change Mode Type 3\n");
		}
		else if(EventCenter.getInstance().getModeType() == 3){		//normal mode
			EventCenter.getInstance().setModeType(1);
			System.out.print("change Mode Type 1\n");
		}
		else{	
			System.err.print("mode error");
		}*/

		return null;
	}
	

}
