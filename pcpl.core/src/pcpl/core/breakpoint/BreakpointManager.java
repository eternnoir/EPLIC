package pcpl.core.breakpoint;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.debug.core.model.ILineBreakpoint;

public class BreakpointManager {
	private static BreakpointManager instance = null;
	private  ArrayList<ILineBreakpoint> _result = null;
	public static BreakpointManager getInstance() {
		if (instance == null) {
			instance = new BreakpointManager();
		}
		return instance;
	}
	
	public BreakpointManager(){
		_result = null;
	}
	
	public ArrayList<ILineBreakpoint> diffResult(ArrayList<ILineBreakpoint> nor,ArrayList<ILineBreakpoint> rec){
		for(ILineBreakpoint nm : nor){
			while(rec.indexOf(nm)!= -1){
				rec.remove(nm);
			}
		}
		_result = new ArrayList<ILineBreakpoint>(rec.size());
		for(ILineBreakpoint b : rec){
			_result.add(b);
		}
		return _result;
	}
	
	public ArrayList<ILineBreakpoint> getResult(){
		assert(_result != null);
		return _result;
	}
	
	public void disableAllBreakpoint(){
		IBreakpoint[] b = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		for(IBreakpoint _b : b){
			try {
				_b.setEnabled(false);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void removeAllBreakpoint(){
		IBreakpoint[] b = DebugPlugin.getDefault().getBreakpointManager().getBreakpoints();
		try {
			DebugPlugin.getDefault().getBreakpointManager().removeBreakpoints(b, true);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setResult(){
		for(ILineBreakpoint b : _result){
			try {
				b.setEnabled(true);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setAllBreakpoint(){
		ArrayList<IResource> resourceList =  FileParaviserUtils.getAllFilesInProject("java");
		for(IResource r : resourceList){
			setBreakpointByResource(r);
		}
	}
	
	public void setBreakpointByResource(IResource r){
		IFile f = ResourcesPlugin.getWorkspace().getRoot().getFileForLocation(r.getLocation());
	    String strings;
	    String[] lines = null;
	    // get single line,to check need to set breakpoint
	    try {
			InputStream s = f.getContents();
			strings = IOUtils.toString(s,"UTF-8");
			lines = strings.split(System.getProperty("line.separator"));
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	    ArrayList<Integer> lineNumbers = checkFunctionNameLineNumber(lines);
	    for(int i : lineNumbers){
	    	BreakpointSetter.getInstance().setBreakpoint(r, i);
	    }
	    
	}
	
	private ArrayList<Integer> checkFunctionNameLineNumber(String[] line){
		ArrayList<Integer> ret = new ArrayList<Integer>();
		for(int i = 0;i < line.length;i++){
			if(checkFunction(line[i])){	//判斷function
				ret.add(i+1);
			}
		}
		
		return ret;
	}
	
	private boolean checkFunction(String s){
		if((s.indexOf("public") + s.indexOf("private"))>-1){	//判斷function
			if((s.indexOf("(")>-1) && (s.indexOf("abstract")<0)){
				return true;
			}
		}
		return false;
	}	

}