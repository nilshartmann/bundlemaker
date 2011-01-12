package org.bundlemaker.core.parser.jdt;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.ASTRequestor;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class JdtAstRequestor extends ASTRequestor {

	/** the compilation unit map */
	private Map<ICompilationUnit, CompilationUnit> _units;

	/** the progress monitor */
	private IProgressMonitor _progressMonitor;

	/**
	 * <p>
	 * Creates a new instance of type {@link JdtAstRequestor}.
	 * </p>
	 * 
	 * @param progressMonitor
	 */
	public JdtAstRequestor(IProgressMonitor progressMonitor) {

		_units = new HashMap<ICompilationUnit, CompilationUnit>();

		_progressMonitor = progressMonitor;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Map<ICompilationUnit, CompilationUnit> getCompilationUnits() {
		return _units;
	}

	/**
	 * @see org.eclipse.jdt.core.dom.ASTRequestor#acceptAST(org.eclipse.jdt.core.ICompilationUnit,
	 *      org.eclipse.jdt.core.dom.CompilationUnit)
	 */
	@Override
	public void acceptAST(ICompilationUnit source, CompilationUnit ast) {

		_units.put(source, ast);

		//
		if (_progressMonitor != null) {
			_progressMonitor.worked(1);
		}
	}
}
